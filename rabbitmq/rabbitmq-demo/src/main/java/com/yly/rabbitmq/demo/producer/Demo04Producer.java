package com.yly.rabbitmq.demo.producer;


import com.yly.rabbitmq.demo.message.Demo04Message;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 和 Demo01Producer 的 #syncSend(Integer id) 方法大体相似，差异点在于新增了方法参数 headerValue ，方便我们传入不同的 Headers 值。
 * 因为 RabbitTemplate 会提供很方便的传递 Headers 的 API 方法，所以我们只好自己构建，当然也比较简单哈。
 */
@Component
public class Demo04Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id, String headerValue) {
        // 创建 MessageProperties 属性
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(Demo04Message.HEADER_KEY, headerValue); // 设置 header
        // 创建 Message 消息
        Message message = rabbitTemplate.getMessageConverter().toMessage(
                new Demo04Message().setId(id), messageProperties);
        // 同步发送消息
        rabbitTemplate.send(Demo04Message.EXCHANGE, null, message);
    }

}
