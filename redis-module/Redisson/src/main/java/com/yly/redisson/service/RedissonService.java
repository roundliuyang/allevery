package com.yly.redisson.service;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedissonService {

    @Resource
    private RedissonClient redissonClient;

    //锁过期时间
    private static final Long LOCK_KEY_TIME = 120L;

    public void doJobTask() {

        //定时任务执行周期较短，为防止数据重复修改，加入锁
        RLock lock = redissonClient.getLock("your_task_name");
        // 尝试获取锁并设定锁的过期时间
        boolean acquired = false;
        try {
            //获取锁
            acquired = lock.tryLock(0, LOCK_KEY_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("取锁失败");
        }
        if (acquired) {
            try {

                // 执行业务逻辑
            } catch (Exception e) {

                log.error("处理失败");
                //业务异常处理逻辑

            } finally {
                // 释放锁
                lock.unlock();
            }
        } else {
            // 获取锁失败，说明有其他线程或进程正在处理数据
            // 可以进行重试或触发告警机制
        }
    }

}