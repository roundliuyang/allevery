package com.yly.task.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在类上，添加 @Component 注解，创建 DemoJob Bean 对象。
 * 创建 #execute() 方法，实现打印日志。同时，在该方法上，添加 @Scheduled 注解，设置每 2 秒执行该方法。
 * 虽然说，@Scheduled 注解，可以添加在一个类上的多个方法上，但是艿艿的个人习惯上，还是一个 Job 类，一个定时任务。
 */
@Component
public class DemoJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicInteger counts = new AtomicInteger();

    /*
        @Scheduled 注解，设置定时任务的执行计划。
        常用属性如下：
        cron 属性：Spring Cron 表达式。例如说，"0 0 12 * * ?" 表示每天中午执行一次，"11 11 11 11 11 ?" 表示 11 月 11 号 11 点 11 分 11 秒执行一次（哈哈哈）。更多示例和讲解，可以看看 《Spring Cron 表达式》 文章。注意，以调用完成时刻为开始计时时间。
        fixedDelay 属性：固定执行间隔，单位：毫秒。注意，以调用完成时刻为开始计时时间。
        fixedRate 属性：固定执行间隔，单位：毫秒。注意，以调用开始时刻为开始计时时间。
        这三个属性，有点雷同，可以看看 《@Scheduled 定时任务的fixedRate、fixedDelay、cron 的区别》 ，一定要分清楚差异。
        不常用属性如下：

        initialDelay 属性：初始化的定时任务执行延迟，单位：毫秒。
        zone 属性：解析 Spring Cron 表达式的所属的时区。默认情况下，使用服务器的本地时区。
        initialDelayString 属性：initialDelay 的字符串形式。
        fixedDelayString 属性：fixedDelay 的字符串形式。
        fixedRateString 属性：fixedRate 的字符串形式。
     */
    @Scheduled(fixedRate = 2000)
    public void execute() {
        logger.info("[execute][定时第 ({}) 次执行]", counts.incrementAndGet());
//        try {
//            Thread.sleep(10000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    /*
        通过日志，我们可以看到，初始化一个 ThreadPoolTaskScheduler 任务调度器。之后，每 2 秒，执行一次 DemoJob 的任务。
        至此，我们已经完成了 Spring Task 调度任务功能的入门。实际上，Spring Task 还提供了异步任务 。
     */

}
