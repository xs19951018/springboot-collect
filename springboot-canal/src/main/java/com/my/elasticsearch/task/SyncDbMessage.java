package com.my.elasticsearch.task;

import com.my.elasticsearch.util.BeanUtils;
import com.my.elasticsearch.util.CanalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SyncDbMessage implements Runnable {

    private CanalUtils canalUtils = BeanUtils.getBean(CanalUtils.class);

    @Override
    public void run() {
        log.info("同步数据库中数据,start------");
        canalUtils.handleMessage();
        log.info("同步数据库中数据,end------");
    }
}
