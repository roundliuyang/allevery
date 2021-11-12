package com.yly.rabbitmq.demo.consumer;


import com.yly.rabbitmq.demo.message.Demo03Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 两个消费者，分别消费 "QUEUE_DEMO_03_A"、"QUEUE_DEMO_03_B" 队列。
@Component
@RabbitListener(queues = Demo03Message.QUEUE_A)
public class Demo03ConsumerA {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo03Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
