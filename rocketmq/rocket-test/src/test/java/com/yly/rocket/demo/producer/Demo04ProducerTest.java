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
    RocketMQ 提供消费重试的机制。在消息消费失败的时候，RocketMQ 会通过消费重试机制，重新投递该消息给 Consumer ，让 Consumer 有机会重新消费消息，实现消费成功。
    当然，RocketMQ 并不会无限重新投递消息给 Consumer 重新消费，而是在默认情况下，达到 16 次重试次数时，Consumer 还是消费失败时，该消息就会进入到死信队列。
    死信队列用于处理无法被正常消费的消息。当一条消息初次消费失败，消息队列会自动进行消息重试；达到最大重试次数后，若消费依然失败，则表明消费者在正常情况下无法正确地消费该消息，此时，消息队列不会立刻将消息丢弃，而是将其发送到该消费者对应的特殊队列中。
    RocketMQ 将这种正常情况下无法被消费的消息称为死信消息（Dead-Letter Message），将存储死信消息的特殊队列称为死信队列（Dead-Letter Queue）。在 RocketMQ 中，可以通过使用 console 控制台对死信队列中的消息进行重发来使得消费者实例再次进行消费。
    每条消息的失败重试，是有一定的间隔时间。实际上，消费重试是基于「5. 定时消息」 来实现，第一次重试消费按照延迟级别为 3 开始。😈 所以，默认为 16 次重试消费，也非常好理解，毕竟延迟级别最高为 18 呀。
    不过要注意，只有集群消费模式下，才有消息重试。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo04ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo04Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
    // 从日志中，我们可以看到，消息因为消费失败后，又重试消费了多次。
}
