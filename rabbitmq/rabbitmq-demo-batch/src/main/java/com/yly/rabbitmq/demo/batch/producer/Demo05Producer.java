package com.yly.rabbitmq.demo.batch.producer;


import com.yly.rabbitmq.demo.batch.message.Demo05Message;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 看起来和我们在「3.1.6 Demo01Producer」提供的发送消息的方法，除了换成了 BatchingRabbitTemplate 来发送消息，其它都是一致的。😈 对的，这也是为什么艿艿在上文说到，Spring-AMQP 是“偷偷”收集来实现批量发送，对于我们使用发送消息的方法，还是一致的。
 * BatchingRabbitTemplate 通过重写 #send(String exchange, String routingKey, Message message, CorrelationData correlationData) 核心方法，实现批量发送的功能。感兴趣的胖友，可以自己去研究下源码，不复杂哈~
 */
@Component
public class Demo05Producer {

    @Autowired
    private BatchingRabbitTemplate batchingRabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo05Message 消息
        Demo05Message message = new Demo05Message();
        message.setId(id);
        // 同步发送消息
        batchingRabbitTemplate.convertAndSend(Demo05Message.EXCHANGE, Demo05Message.ROUTING_KEY, message);
    }

}
