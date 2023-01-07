package com.yly.rabbitmq.transaction.producer;


import com.yly.rabbitmq.transaction.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo11ProducerTest {

    @Autowired
    private Demo11Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
