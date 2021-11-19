package com.yly.rabbitmq.demo.batch.consumer.consumer;


import com.yly.rabbitmq.demo.batch.consumer.message.Demo05Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/*
    在类上的 @@RabbitListener 注解的 containerFactory 属性，设置了我们在「5.1 RabbitConfig」创建的 SimpleRabbitListenerContainerFactory Bean ，表示它要批量消费消息。
    在 #onMessage(...) 消费方法上，修改方法入参的类型为 List 数组。
 */
@Component
@RabbitListener(queues = Demo05Message.QUEUE,
    containerFactory = "consumerBatchContainerFactory")
public class Demo05Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(List<Demo05Message> messages) {
        logger.info("[onMessage][线程编号:{} 消息数量：{}]", Thread.currentThread().getId(), messages.size());
    }

//    @RabbitHandler(isDefault = true)
//    public void onMessageX(List<Message> messages) {
//        logger.info("[onMessage][线程编号:{} 消息数量：{}]", Thread.currentThread().getId(), messages.size());
//    }

}
