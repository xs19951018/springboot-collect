package com.my.elasticsearch.common.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.my.elasticsearch.api.ElasticSearchApi;
import com.my.elasticsearch.common.annotation.CanalClientListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * elastic 监听数据库表user变动
 */
@Component
@Slf4j
@CanalClientListener(tables = "user")
public class ElasticCanalListener implements CanalListener {

    @Autowired
    private ElasticSearchApi elasticSearchApi;

    @Override
    public void handleEvent(String table, CanalEntry.EventType eventType, String id, String data) {
        try {
            String indexName = table;
            if (CanalEntry.EventType.DELETE == eventType) {
                elasticSearchApi.deleteDocument(indexName, id);
            } else if (CanalEntry.EventType.INSERT == eventType) {
                elasticSearchApi.addDocument(indexName, id, data);
            } else {
                elasticSearchApi.updateDocument(indexName, id, data);
            }
            log.info("同步mysql数据变动到elastic成功:{}", data);
        } catch (Exception e) {
            log.info("同步mysql数据变动失败:{}", e);
        }
    }
}
