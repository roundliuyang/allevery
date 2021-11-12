package com.yly.rabbitmq.demo;


import com.yly.rabbitmq.demo.producer.Demo02Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo02ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo02Producer producer;

    // #testSyncSendSuccess() 方法，发送消息的 RoutingKey 是 "da.yu.nai" ，可以匹配到 "DEMO_QUEUE_02" 。
    @Test
    public void testSyncSendSuccess() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id, "da.yu.nai");
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    // #testSyncSendFailure() 方法，发送消息的 RoutingKey 是 "yu.nai.shuai" ，无法匹配到 "DEMO_QUEUE_02",因为 无法匹配到 "DEMO_QUEUE_02" ，自然 Demo02Consumer 无法进行消费。
    @Test
    public void testSyncSendFailure() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id, "yu.nai.shuai");
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
