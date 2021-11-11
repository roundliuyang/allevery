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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/*
    批量发送消息
    在一些业务场景下，我们希望使用 Producer 批量发送消息，提高发送性能。在 RocketMQTemplate 中，提供了一个方法方法批量发送消息的方法。代码如下：
    // RocketMQTemplate.java

    public <T extends Message> SendResult syncSend(String destination, Collection<T> messages, long timeout) {
        // ... 省略具体代码实现
    }
    通过方法参数 destination 可知，必须发送相同 Topic 的消息。
    要注意方法参数 messages ，每个集合的元素必须是 Spring Messaging 定义的 Message 消息。😈 RocketMQTemplate 重载了非常多的 #syncSend(...) 方法，一定要小心哟。
    通过方法名可知，这个是同步批量发送消息。
    有一点要注意，虽然是批量发送多条消息，但是是以所有消息加起来的大小，不能超过消息的最大大小的限制，而不是按照单条计算。😈 所以，一次性发送的消息特别多，还是需要分批的进行批量发送。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo02ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo02Producer producer;

    @Test
    public void testSendBatch() throws InterruptedException {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        SendResult result = producer.sendBatch(ids);
        logger.info("[testSendBatch][发送编号：[{}] 发送结果：[{}]]", ids, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
