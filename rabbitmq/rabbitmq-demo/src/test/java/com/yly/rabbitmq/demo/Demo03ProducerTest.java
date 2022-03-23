package com.yly.rabbitmq.demo;


import com.yly.rabbitmq.demo.producer.Demo03Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * Fanout Exchange 路由规则非常简单，它会把所有发送到该 Exchange 的消息路由到所有与它绑定的 Queue 中。如下图：
 * 生产者（P）发送到 Exchange（X）的所有消息都会路由到图中的两个 Queue，并最终被两个消费者（C1 与 C2）消费。
 * 总结来说，指定 Exchange ，会路由到多个绑定的 Queue 中。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo03ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo03Producer producer;

    //  符合预期
    //  发送的消息，成功投递到了两个队列中，所以被两个消费者都消费到了。
    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
