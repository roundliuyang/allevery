package com.yly.rabbitmq.ordely.consumer;


import com.yly.rabbitmq.ordely.message.Demo10Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 严格消费顺序消息
 * 为了实现每个子 Queue 能够被每个 Consumer 串行消费，从而实现基于子 Queue 的并行的严格消费顺序消息，我们只好在类上添了四个 @RabbitListener 注解，每个对应一个子 Queue 。
 * 如果胖友使用一个 @RabbitListener 注解，并添加四个子 Queue ，然后设置 concurrency = 4 时，实际是并发四个线程，消费四个子 Queue 的消息，无法保证严格消费顺序消息。
 */
@Component
@RabbitListener(queues = Demo10Message.QUEUE_0)
@RabbitListener(queues = Demo10Message.QUEUE_1)
@RabbitListener(queues = Demo10Message.QUEUE_2)
@RabbitListener(queues = Demo10Message.QUEUE_3)
public class Demo10Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler(isDefault = true)
    public void onMessage(Message<Demo10Message> message) {
        logger.info("[onMessage][线程编号:{} Queue:{} 消息编号：{}]", Thread.currentThread().getId(), getQueue(message),
                message.getPayload().getId());
    }

    private static String getQueue(Message<Demo10Message> message) {
        return message.getHeaders().get("amqp_consumerQueue", String.class);
    }

}
