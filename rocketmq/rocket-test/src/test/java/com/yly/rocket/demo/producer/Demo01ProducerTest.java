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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo01ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo01Producer producer;

/*
    通过日志我们可以看到，我们发送的消息，分别被 Demo01AConsumer 和 Demo01Consumer 两个消费者（消费者分组）都消费了一次。
    同时，两个消费者在不同的线程池中，消费了这条消息。虽然说，我们看到两条日志里，我们都看到了线程名为 "MessageThread_1" ，但是线程编号分别是 45 和 51 。😈 因为，
    每个 RocketMQ Consumer 的消费线程池创建的线程都是以 "MessageThread_" 开头，同时这里相同的线程名结果不同的线程编号，很容易判断出时候用了两个不同的消费线程池。
 */
    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

/*
    我们来执行 #testASyncSend() 方法，测试异步发送消息。控制台输出如下：
    友情提示：注意，不要关闭 #testSyncSend() 单元测试方法，因为我们要模拟每个消费者集群，都有多个 Consumer 节点。
    和 #testSyncSend() 方法执行的结果，是一致的。此时，我们打开 #testSyncSend() 方法所在的控制台，不会看到有消息消费的日志。说明，符合集群消费的机制：集群消费模式下，相同 Consumer Group 的每个 Consumer 实例平均分摊消息。。
    😈 不过如上的日志，也可能出现在 #testSyncSend() 方法所在的控制台，而不在 #testASyncSend() 方法所在的控制台。
 */
    @Test
    public void testASyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id, new SendCallback() {

            @Override
            public void onSuccess(SendResult result) {
                logger.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
            }

            @Override
            public void onException(Throwable e) {
                logger.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }

        });

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

/*
    testOnewaySend() 方法，胖友自己执行，比较简单。
 */
    @Test
    public void testOnewaySend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.onewaySend(id);
        logger.info("[testOnewaySend][发送编号：[{}] 发送完成]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

/*
    @ExtRocketMQTemplateConfiguration
    RocketMQ-Spring 考虑到开发者可能需要连接多个不同的 RocketMQ 集群，所以提供了 @ExtRocketMQTemplateConfiguration 注解，实现配置连接不同 RocketMQ 集群的 Producer 的 RocketMQTemplate Bean 对象。
    @ExtRocketMQTemplateConfiguration 注解的具体属性，和我们在 「3.2 应用配置文件」 的 rocketmq.producer 配置项是一致的，就不重复赘述啦。
    @ExtRocketMQTemplateConfiguration 注解的简单使用示例，代码如下：

    @ExtRocketMQTemplateConfiguration(nameServer = "${demo.rocketmq.extNameServer:demo.rocketmq.name-server}")
        public class ExtRocketMQTemplate extends RocketMQTemplate {
    }
    在类上，添加 @ExtRocketMQTemplateConfiguration 注解，并设置连接的 RocketMQ Namesrv 地址。
    同时，需要继承 RocketMQTemplate 类，从而使我们可以直接使用 @Autowire 或 @Resource 注解，注入 RocketMQTemplate Bean 属性。
 */

}
