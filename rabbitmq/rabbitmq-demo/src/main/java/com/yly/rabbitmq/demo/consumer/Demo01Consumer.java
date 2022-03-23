package com.yly.rabbitmq.demo.consumer;


import com.yly.rabbitmq.demo.message.Demo01Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 在类上，添加了 @RabbitListener 注解，声明了消费的队列是 "QUEUE_DEMO_01"
 * 在方法上，添加了 @RabbitHandler 注解，申明了处理消息的方法。同时，方法入参为消息的类型。这里，我们设置了 Demo01Message
 * 如果我们想要获得消费消息的更多信息，例如说，RoutingKey、创建时间等等信息，则可以考虑使用艿艿注释掉的那段代码，通过方法入参为 org.springframework.amqp.core.Message 类型。
 * 不过绝大多数情况下，我们并不需要这么做。
 */
@Component
@RabbitListener(queues = Demo01Message.QUEUE)
public class Demo01Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo01Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

//    @RabbitHandler(isDefault = true)
//    public void onMessage(org.springframework.amqp.core.Message message) {
//        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
//    }

}
