package com.yly.rocket.demo.consumer;


import com.yly.rocket.demo.message.Demo02Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
    虽然说，Demo02Message 消息是批量发送的，但是我们还是可以和 「3.8 Demo1Consumer」 一样，逐条消费消息。
 */
@Component
@RocketMQMessageListener(
        topic = Demo02Message.TOPIC,
        consumerGroup = "demo02-consumer-group-" + Demo02Message.TOPIC
)
public class Demo02Consumer implements RocketMQListener<Demo02Message> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(Demo02Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
