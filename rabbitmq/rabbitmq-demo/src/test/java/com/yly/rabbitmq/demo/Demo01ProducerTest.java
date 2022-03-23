package com.yly.rabbitmq.demo;


import com.yly.rabbitmq.demo.producer.Demo01Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;

/**
 * Direct 类型的 Exchange 路由规则比较简单，它会把消息路由到那些 binding key 与 routing key 完全匹配的 Queue 中。以下图的配置为例：
 * 我们以 routingKey="error" 发送消息到 Exchange ，则消息会路由到 Queue1(amqp.gen-S9b…) 。
 * 我们以 routingKey="info" 或 routingKey="warning" 来发送消息，则消息只会路由到 Queue2(amqp.gen-Agl…) 。
 * 如果我们以其它 routingKey 发送消息，则消息不会路由到这两个 Queue 中。
 * 总结来说，指定 Exchange + routing key ，有且仅会路由到至多一个 Queue 中。😈 极端情况下，如果没有匹配，消息就发送到“空气”中，不会进入任何 Queue 中。
 * 注：Queue 名字 amqp.gen-S9b… 和 amqp.gen-Agl… 自动生成的。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo01ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo01Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void tesSyncSendDefault() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSendDefault(id);
        logger.info("[tesSyncSendDefault][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id).addCallback(new ListenableFutureCallback<Void>() {

            @Override
            public void onFailure(Throwable e) {
                logger.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }

            @Override
            public void onSuccess(Void aVoid) {
                logger.info("[testASyncSend][发送编号：[{}] 发送成功，发送成功]", id);
            }

        });
        logger.info("[testASyncSend][发送编号：[{}] 调用完成]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void nothing() throws InterruptedException {
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void nothing02() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            producer.syncSend(id);
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
            Thread.sleep(5000L);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
