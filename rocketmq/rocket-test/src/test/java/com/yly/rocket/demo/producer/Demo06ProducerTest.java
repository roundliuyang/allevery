package com.yly.rocket.demo.producer;


import com.yly.rocket.demo.Application;
import org.apache.rocketmq.client.producer.SendCallback;
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
    RocketMQ 提供了两种顺序级别：

    普通顺序消息 ：Producer 将相关联的消息发送到相同的消息队列。
    完全严格顺序 ：在【普通顺序消息】的基础上，Consumer 严格顺序消费。
    目前已知的应用只有数据库 binlog 同步强依赖严格顺序消息，其他应用绝大部分都可以容忍短暂乱序，推荐使用普通的顺序消息。
    如下是 RocketMQ 官方文档对这两种顺序级别的定义：

    普通顺序消费模式下，消费者通过同一个消费队列收到的消息是有顺序的，不同消息队列收到的消息则可能是无顺序的。
    严格顺序消息模式下，消费者收到的所有消息均是有顺序的。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo06ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo06Producer producer;

    //  Producer 发送顺序消息时，因为我们使用 id = 1 作为 hashKey ，所以都发送到了 Topic 为 "DEMO_06" ，队列编号为 1 的消息队列。
    //  Consumer 顺序消费消息时，是在单线程中，顺序消费每条消息。

    @Test
    public void testSyncSendOrderly() throws InterruptedException {
        // 发送多条消息
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            SendResult result = producer.syncSendOrderly(id);
            logger.info("[testSyncSendOrderly][发送编号：[{}] 发送结果：[{}]]", id, result);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testASyncSendOrderly() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            producer.asyncSendOrderly(id, new SendCallback() {

                @Override
                public void onSuccess(SendResult result) {
                    logger.info("[testASyncSendOrderly][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
                }

                @Override
                public void onException(Throwable e) {
                    logger.info("[testASyncSendOrderly][发送编号：[{}] 发送异常]]", id, e);
                }

            });
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testOnewaySendOrderly() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            producer.onewaySendOrderly(id);
            logger.info("[testOnewaySendOrderly][发送编号：[{}] 发送完成]", id);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
