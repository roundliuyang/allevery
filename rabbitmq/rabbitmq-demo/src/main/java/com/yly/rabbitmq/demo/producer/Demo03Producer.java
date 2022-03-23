package com.yly.rabbitmq.demo.producer;


import com.yly.rabbitmq.demo.message.Demo03Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 和 Demo01Producer 的 #syncSend(Integer id) 方法大体相似，差异点在于传入 routingKey = null ，因为不需要。
 */
@Component
public class Demo03Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo03Message 消息
        Demo03Message message = new Demo03Message();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo03Message.EXCHANGE, null, message);
    }

}
