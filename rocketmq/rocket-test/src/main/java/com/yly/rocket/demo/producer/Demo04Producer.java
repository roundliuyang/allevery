package com.yly.rocket.demo.producer;


import com.yly.rocket.demo.message.Demo04Message;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
代码上，并没有什么特别，和 Demo01Producer 的同步发送消息的代码是一致的，除了消息换成了 Demo04Message 。
 */
@Component
public class Demo04Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSend(Integer id) {
        // 创建 Demo04Message 消息
        Demo04Message message = new Demo04Message();
        message.setId(id);
        // 同步发送消息
        return rocketMQTemplate.syncSend(Demo04Message.TOPIC, message);
    }

}
