package com.yly.rabbitmq.demo.batch.consumer2.producer;


import com.yly.rabbitmq.demo.batch.consumer2.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/*
    #testSyncSend01() 方法，发送 3 条消息，测试 Demo06Consumer 获取数量为 batchSize = 10 消息，超时情况下的批量消费。
    #testSyncSend02() 方法，发送 10 条消息，测试 Demo06Consumer 获取数量为 batchSize = 10 消息，未超时情况下的批量消费。
    我们来执行 #testSyncSend01() 方法，超时情况下的批量消费。控制台输出如下：

    // Producer 成功同步发送了 3 条消息
    2019-12-15 00:01:18.097  INFO 78389 --- [           main] c.i.s.l.r.producer.Demo06ProducerTest    : [testSyncSendMore][发送编号：[1575993678] 发送成功]
    2019-12-15 00:01:18.099  INFO 78389 --- [           main] c.i.s.l.r.producer.Demo06ProducerTest    : [testSyncSendMore][发送编号：[1575993678] 发送成功]
    2019-12-15 00:01:18.099  INFO 78389 --- [           main] c.i.s.l.r.producer.Demo06ProducerTest    : [testSyncSendMore][发送编号：[1575993678] 发送成功]

    // Consumer 30 秒超时等待后，批量消费到 3 条消息
    2019-12-15 00:01:48.116  INFO 78389 --- [ntContainer#0-1] c.i.s.l.r.consumer.Demo06Consumer        : [onMessage][线程编号:17 消息数量：3]
    符合预期。具体胖友看下日志上的注释说明。
    我们来执行 #testSyncSend02() 方法，未超时情况下的批量消费。控制台输出如下：

    // Producer 成功同步发送了 10 条消息
    2019-12-15 00:03:50.406  INFO 78997 --- [           main] c.i.s.l.r.producer.Demo06ProducerTest    : [testSyncSendMore][发送编号：[1575993830] 发送成功]
    // ... 省略 8 条消息
    2019-12-15 00:03:50.410  INFO 78997 --- [           main] c.i.s.l.r.producer.Demo06ProducerTest    : [testSyncSendMore][发送编号：[1575993830] 发送成功]

    // Consumer 拉取到 10 条消息后，立即批量消费到 10 条消息
    2019-12-15 00:03:50.429  INFO 78997 --- [ntContainer#0-1] c.i.s.l.r.consumer.Demo06Consumer        : [onMessage][线程编号:17 消息数量：10
    符合预期。具体胖友看下日志上的注释说明。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo06ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo06Producer producer;

    @Test
    public void testSyncSend01() throws InterruptedException {
        // 发送 3 条消息
        this.testSyncSend(3);
    }

    @Test
    public void testSyncSen02() throws InterruptedException {
        // 发送 10 条消息
        this.testSyncSend(10);
    }

    private void testSyncSend(int n) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // 同步发送消息
            int id = (int) (System.currentTimeMillis() / 1000);
            producer.syncSend(id);
            logger.info("[testSyncSendMore][发送编号：[{}] 发送成功]", id);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
