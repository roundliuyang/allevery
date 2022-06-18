package com.yly.rabbitmq.rpc.producer;


import com.yly.rabbitmq.rpc.message.Demo14Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <1> 处，创建 CorrelationData 对象，使用 UUID 作为唯一标识。
 * <2> 处，调用 RabbitTemplate#convertSendAndReceive(exchange, routingKey, message, correlationData) 方法，Producer 发送消息，
 * 并等待结果。该结果，是 Consumer 消费消息，返回的结果。
 */
@Component
public class Demo14Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String syncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo14Message message = new Demo14Message();
        message.setId(id);
        // 创建 CorrelationData 对象
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 同步发送消息，并接收结果
        return (String) rabbitTemplate.convertSendAndReceive(Demo14Message.EXCHANGE, Demo14Message.ROUTING_KEY, message,
                correlationData);
    }

}
