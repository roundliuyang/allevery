package com.yly.rabbitmq.demo.batch.consumer;


import com.yly.rabbitmq.demo.batch.message.Demo05Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 和 Demo01Consumer 基本一致，差别在于消费的队列是 "QUEUE_DEMO_02"
 */
@Component
@RabbitListener(queues = Demo05Message.QUEUE)
public class Demo05Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo05Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
