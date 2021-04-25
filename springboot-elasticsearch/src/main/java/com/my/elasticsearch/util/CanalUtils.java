package com.my.elasticsearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.Message;
import com.my.common.util.StringUtils;
import com.my.elasticsearch.common.annotation.CanalClientListener;
import com.my.elasticsearch.common.listener.CanalListener;
import com.my.elasticsearch.task.ElasticExecutor;
import com.my.elasticsearch.task.SyncDbMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * canal：mysql数据同步
 */
@Slf4j
@Component
public class CanalUtils {

    @Value("${elastic.address}")
    private String address;
    @Value("${elastic.tables}")
    private String tables;
    private CanalConnector canalConnector;
    @Autowired
    private ElasticExecutor elasticExecutor;
    @Autowired
    private BeanUtils beanUtils;
    /**
     * canal监听器
     */
    private Map<String, List<CanalListener>> canalListenerMap = new HashMap<>();

    /**
     * 初始化canal连接
     */
    @PostConstruct
    public void init() {
        try {
            canalConnector = CanalConnectors.newSingleConnector(
                    new InetSocketAddress(address, 11111), "example", "", "");
            canalConnector.connect();
            log.info("canal连接成功...");
            canalConnector.subscribe(tables);
            // 回滚到上一次日志的位置
            canalConnector.rollback();

            // 注册canal监听器
            Boolean b = registerCanalListener();
            if (b) {
                // 注册canal定时任务
                elasticExecutor.addSchduler(new SyncDbMessage(), 0, 11, TimeUnit.SECONDS, 1);
            }
        } catch (Exception e) {
            log.info("canal初始化连接失败:{}", e);
            closeCanal();
        }
    }

    /**
     * 注册canal监听器
     */
    public Boolean registerCanalListener() {
        List<CanalListener> canalListenerList = beanUtils.getBeansOfType(CanalListener.class);
        if (CollectionUtils.isEmpty(canalListenerList)) {
            log.info("未找到canal监听器,正在关闭canal连接...");
            closeCanal();
            return Boolean.FALSE;
        }
        canalListenerList.forEach(canalListener -> {
            CanalClientListener canalClient = canalListener.getClass().getAnnotation(CanalClientListener.class);
            String[] tables = canalClient.tables();
            for (String table : tables) {
                List<CanalListener> tableListeners = canalListenerMap.get(table);
                if (CollectionUtils.isEmpty(tableListeners)) {
                    tableListeners = new ArrayList<>();
                }
                tableListeners.add(canalListener);
                canalListenerMap.put(table, tableListeners);
            }
        });
        log.info("canal监听器注册成功:{}", canalListenerMap);
        return Boolean.TRUE;
    }

    /**
     * 处理消息
     */
    public void handleMessage() {
        int batchSize = 100;
        Long batchId = null;
        try {
            Message message = canalConnector.getWithoutAck(batchSize);
            batchId = message.getId();
            if (!(batchId == -1 || message.getEntries().size() == 0)) {
                handleEvent(message.getEntries());
            }
            canalConnector.ack(batchId);
        } catch (Exception e) {
            log.error("canal数据同步失败:{}", e);
            canalConnector.rollback(batchId);
        }
    }

    public void handleEvent(List<CanalEntry.Entry> entryList) {
        for (Entry entry : entryList) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }
            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            EventType eventType = rowChage.getEventType();
            String table = entry.getHeader().getTableName();

            JSONObject jsonObject = null;
            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    jsonObject = convertJsonStr(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    jsonObject = convertJsonStr(rowData.getAfterColumnsList());
                } else {
//                    printColumn(rowData.getBeforeColumnsList());
                    jsonObject = convertJsonStr(rowData.getAfterColumnsList());
                }
            }
            log.info("数据库table[{}]change[{}]:{}", table, eventType, jsonObject);

            // 调用监听器处理数据
            List<CanalListener> canalListeners = canalListenerMap.get(table);
            for (CanalListener listener : canalListeners) {
                listener.handleEvent(table, eventType, jsonObject.get("id").toString(), jsonObject.toJSONString());
            }
        }
    }

    /**
     * binlog数据转化json对象字符串
     * @param columns
     * @return
     */
    private JSONObject convertJsonStr(List<Column> columns) {
        JSONObject jsonObject = new JSONObject();
        columns.forEach(column -> jsonObject.put(StringUtils.lineToHump(column.getName()), column.getValue()));
        return jsonObject;
    }

    private void closeCanal() {
        if (canalConnector != null) {
            canalConnector.disconnect();
            log.info("canal连接已关闭...");
        }
    }
}
