package com.yly.rabbitmq.demo.producer;


import com.yly.rabbitmq.demo.message.Demo02Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 和 Demo01Producer 的 #syncSend(Integer id) 方法大体相似，差异点在于新增了方法参数 routingKey ，方便我们传入不同的路由键。
 */
@Component
public class Demo02Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id, String routingKey) {
        // 创建 Demo02Message 消息
        Demo02Message message = new Demo02Message();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo02Message.EXCHANGE, routingKey, message);
    }

}
