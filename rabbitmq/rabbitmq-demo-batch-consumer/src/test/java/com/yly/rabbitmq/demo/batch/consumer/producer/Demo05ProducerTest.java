package com.yly.rabbitmq.demo.batch.consumer.producer;


import com.yly.rabbitmq.demo.batch.consumer.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/*
    在「4. 批量发送消息」小节，我们已经实现批量发送消息到 RabbitMQ Broker 中。那么，我们来思考一个问题，这批消息在 RabbitMQ Broker 到底是存储一条消息，还是多条消息？

    如果胖友使用过 Kafka、RocketMQ 这两个消息队列，那么判断肯定会是多条消息。
    从「4.6 Demo05Consumer」中，我们可以看到逐条消息的消费，也会认为是多条消息。
    😭 实际上，RabbitMQ Broker 存储的是一条消息。又或者说，RabbitMQ 并没有提供批量接收消息的 API 接口。

    那么，为什么我们在「4. 批量发送消息」能够实现呢？答案是批量发送消息是 Spring-AMQP 的 SimpleBatchingStrategy 所封装提供：

    在 Producer 最终批量发送消息时，SimpleBatchingStrategy 会通过 #assembleMessage() 方法，将批量发送的多条消息组装成一条“批量”消息，然后进行发送。
    在 Consumer 拉取到消息时，会根据#canDebatch(MessageProperties properties) 方法，判断该消息是否为一条“批量”消息？如果是，则调用# deBatch(Message message, Consumer<Message> fragmentConsumer) 方法，将一条“批量”消息拆开，变成多条消息。
    这个操作，是不是略微有点骚气？！艿艿在这里卡了很久！！！莫名其妙的~一直以为，RabbitMQ 提供了批量发送消息的 API 接口啊。

    OK ，虽然很悲伤，但是我们还是回到这个小节的主题。

    在一些业务场景下，我们希望使用 Consumer 批量消费消息，提高消费速度。在 Spring-AMQP 中，提供了两种批量消费消息的方式。本小节，我们先来看第一种，它需要基于「4. 批量发送消息」之上实现。
    在 SimpleBatchingStrategy 将一条“批量”消息拆开，变成多条消息后，直接批量交给 Consumer 进行消费处理。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo05ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo06Producer producer;

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
    /*
        我们来执行 #testASyncSend() 方法，测试批量消费消息。控制台输出如下：

        // Producer 成功同步发送了 3 条消息，每条间隔 10 秒。
        2019-12-15 22:42:08.755  INFO 60216 --- [           main] c.i.s.l.r.producer.Demo05ProducerTest    : [testSyncSend][发送编号：[1575988928] 发送成功]
        2019-12-15 22:42:18.757  INFO 60216 --- [           main] c.i.s.l.r.producer.Demo05ProducerTest    : [testSyncSend][发送编号：[1575988938] 发送成功]
        2019-12-15 22:42:28.758  INFO 60216 --- [           main] c.i.s.l.r.producer.Demo05ProducerTest    : [testSyncSend][发送编号：[1575988948] 发送成功]

        // Demo05Consumer 在最后一条消息发送成功后果的 30 秒，一次性批量消费了这 3 条消息。
        2019-12-15 22:42:58.775  INFO 60216 --- [ntContainer#0-1] c.i.s.l.r.consumer.Demo05Consumer        : [onMessage][线程编号:17 消息数量：3]
        符合预期，Demo05Consumer 批量消费了 3 条消息。
     */

}
