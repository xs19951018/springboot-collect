package com.my.elasticsearch.task;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ElasticExecutor {

    private ScheduledThreadPoolExecutor elasticScheduler = new ScheduledThreadPoolExecutor(1);

    private static Map<Integer, ScheduledFuture> elasticSchdulerM = new HashMap<>();

    /**
     * 新增商品任务
     * @param task 任务
     * @param initialDelay 首次执行的延迟时间
     * @param delay 执行间隔
     * @param unit 时间单位
     * @param type 任务类型
     */
    public void addSchduler(Runnable task, long initialDelay, long delay, TimeUnit unit, Integer type) {
        ScheduledFuture<?> scheduledFuture = elasticScheduler.scheduleWithFixedDelay(task, initialDelay, delay, unit);
        elasticSchdulerM.put(type, scheduledFuture);
    }

    public void removeSchduler(Integer type) {
        ScheduledFuture schedule = elasticSchdulerM.get(type);
        if (schedule != null) {
            schedule.cancel(true);
            elasticSchdulerM.remove(type);
        }
    }
}
