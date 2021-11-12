package com.yly.rabbitmq.demo.batch.producer;


import com.yly.rabbitmq.demo.batch.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;


/*
在一些业务场景下，我们希望使用 Producer 批量发送消息，提高发送性能。不同于我们在《芋道 Spring Boot 消息队列 RocketMQ 入门》 的「4. 批量发送消息」 功能，RocketMQ 是提供了一个可以批量发送多条消息的 API 。
而 Spring-AMQP 提供的批量发送消息，它提供了一个 MessageBatch 消息收集器，将发送给相同 Exchange + RoutingKey 的消息们，“偷偷”收集在一起，当满足条件时候，一次性批量发送提交给 RabbitMQ Broker 。

Spring-AMQP 通过 BatchingRabbitTemplate 提供批量发送消息的功能。如下是三个条件，满足任一即会批量发送：

【数量】batchSize ：超过收集的消息数量的最大条数。
【空间】bufferLimit ：超过收集的消息占用的最大内存。
【时间】timeout ：超过收集的时间的最大等待时长，单位：毫秒。😈 不过要注意，这里的超时开始计时的时间，是以最后一次发送时间为起点。也就说，每调用一次发送消息，都以当前时刻开始计时，重新到达 timeout 毫秒才算超时。
另外，BatchingRabbitTemplate 提供的批量发送消息的能力比较弱。对于同一个 BatchingRabbitTemplate 对象来说，同一时刻只能有一个批次(保证 Exchange + RoutingKey 相同)，否则会报错。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo05ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo05Producer producer;

    /*
        步发送三条消息，每次发送消息之间，都故意 sleep 10 秒。😈 目的是，恰好满足我们配置的 timeout 最大等待时长。
        因为使用 BatchingRabbitTemplate 批量发送消息，所以在 Producer 成功发送完第一条消息后，Consumer 并未消费到这条消息。
        又因为 BatchingRabbitTemplate 是按照每次发送后，都重新计时，所以在最后一条消息成功发送后的 30 秒，Consumer 才消费到批量发送的 3 条消息。
     */
    @Test
    public void testSyncSend() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            // 同步发送消息
            int id = (int) (System.currentTimeMillis() / 1000);
            producer.syncSend(id);

            // 故意每条消息之间，隔离 10 秒
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
            Thread.sleep(10 * 1000L);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
