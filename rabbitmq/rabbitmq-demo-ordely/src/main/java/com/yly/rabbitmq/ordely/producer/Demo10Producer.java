package com.yly.rabbitmq.ordely.producer;


import com.yly.rabbitmq.ordely.message.Demo10Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo10Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo10Message 消息
        Demo10Message message = new Demo10Message();
        message.setId(id);
        // 同步发送消息
        // 发消息时，我们对 Demo10Message.id % 队列编号进行取余，获得队列编号作为 RoutingKey,从而路由消息对应的子Queue 中。
        rabbitTemplate.convertAndSend(Demo10Message.EXCHANGE, this.getRoutingKey(id), message);
    }

    private String getRoutingKey(Integer id) {
        return String.valueOf(id % Demo10Message.QUEUE_COUNT);
    }

}
