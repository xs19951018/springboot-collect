package com.my.elasticsearch.common.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;

public interface CanalListener {

    /**
     * 处理数据变动
     * @param table
     * @param eventType
     * @param id
     * @param data
     */
     void handleEvent(String table, CanalEntry.EventType eventType, String id, String data);

}
