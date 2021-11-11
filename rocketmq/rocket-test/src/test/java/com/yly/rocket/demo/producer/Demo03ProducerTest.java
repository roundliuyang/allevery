package com.yly.rocket.demo.producer;


import com.yly.rocket.demo.Application;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/*
在 RocketMQ 中，提供定时消息的功能。
定时消息，是指消息发到 Broker 后，不能立刻被 Consumer 消费，要到特定的时间点或者等待特定的时间后才能被消费。
不过，RocketMQ 暂时不支持任意的时间精度的延迟，而是固化了 18 个延迟级别。如下表格：
延迟级别	时间	延迟级别	时间	延迟级别	时间
1	1s	7	3m	13	9m
2	5s	8	4m	14	10m
3	10s	9	5m	15	20m
4	30s	10	6m	16	30m
5	1m	11	7m	17	1h
6	2m	12	8m	18	2h
如果胖友想要任一时刻的定时消息，可以考虑借助 MySQL + Job 来实现。又或者考虑使用 DDMQ(滴滴打车基于 RocketMQ 和 Kafka 改造的开源消息队列) 。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo03ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo03Producer producer;

    @Test
    public void testSyncSendDelay() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSendDelay(id, 3); // 延迟级别 3 ，即 10 秒后消费
        logger.info("[testSyncSendDelay][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
    // 发送的消息，延迟 10 秒被 Demo03Consumer 消费。

}
