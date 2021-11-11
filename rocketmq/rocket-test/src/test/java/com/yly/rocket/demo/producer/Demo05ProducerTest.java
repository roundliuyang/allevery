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
    在上述的示例中，我们看到的都是使用集群消费。而在一些场景下，我们需要使用广播消费。
    广播消费模式下，相同 Consumer Group 的每个 Consumer 实例都接收全量的消息。
    例如说，在应用中，缓存了数据字典等配置表在内存中，可以通过 RocketMQ 广播消费，实现每个应用节点都消费消息，刷新本地内存的缓存。
    又例如说，我们基于 WebSocket 实现了 IM 聊天，在我们给用户主动发送消息时，因为我们不知道用户连接的是哪个提供 WebSocket 的应用，
    所以可以通过 RocketMQ 广播消费，每个应用判断当前用户是否是和自己提供的 WebSocket 服务连接，如果是，则推送消息给用户。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo05ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo05Producer producer;
/*
    首先，执行 #test() 测试方法，先启动一个消费者分组 "demo05-consumer-group-DEMO_05" 的 Consumer 节点。
    然后，执行 #testSyncSend() 测试方法，先启动一个消费者分组 "demo05-consumer-group-DEMO_05" 的 Consumer 节点。
    同时，该测试方法，调用 Demo05ProducerTest#syncSend(id) 方法，同步发送了一条消息。控制台输出如下：
    消费者分组 "demo05-consumer-group-DEMO_05" 的两个 Consumer 节点，都消费了这条发送的消息。符合广播消费的预期~
 */
    @Test
    public void test() throws InterruptedException {
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
