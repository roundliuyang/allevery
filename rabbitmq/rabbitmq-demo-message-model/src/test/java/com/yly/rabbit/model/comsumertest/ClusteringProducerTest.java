package com.yly.rabbit.model.comsumertest;


import com.yly.rabbit.model.Application;
import com.yly.rabbit.model.producer.ClusteringProducer;
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
public class ClusteringProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClusteringProducer producer;

    /**
     * 首先，执行 #mock() 测试方法，先启动一个消费 "QUEUE_CLUSTERING-GROUP-01" 这个 Queue 的 Consumer 节点。
     */
    @Test
    public void mock() throws InterruptedException {
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    /**
     * 然后，执行 #testSyncSend() 测试方法，再启动一个消费 "QUEUE_CLUSTERING-GROUP-01" 这个 Queue 的 Consumer 节点。同时，该测试方法，调用 ClusteringProducer#syncSend(id) 方法，同步发送了 3 条消息。
     */
    @Test
    public void testSyncSend() throws InterruptedException {
        // 发送 3 条消息
        for (int i = 0; i < 3; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            producer.syncSend(id);
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
