package com.yly.rabbitmq.ack.consumer;


import com.rabbitmq.client.Channel;
import com.yly.rabbitmq.ack.message.Demo12Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
    在消费方法上，我们增加类型为 Channel 的方法参数，和 deliveryTag 。通过调用其 Channel#basicAck(deliveryTag, multiple) 方法，可以进行消息的确认。
    这里，艿艿添加了比较详细的注释说明，胖友可以自己瞅瞅噢。
    在 @RabbitListener 注解的 ackMode 属性，我们可以设置自定义的 AcknowledgeMode 模式。
    在消费逻辑中，我们故意只提交消费的消息的 Demo12Message.id 为奇数的消息。😈 这样，我们只需要发送一条 id=1 ，一条 id=2 的消息，如果第二条的消费进度没有被提交，就可以说明手动提交消费进度成功。
 */
@Component
@RabbitListener(queues = Demo12Message.QUEUE)
public class Demo12Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo12Message message, Channel channel,
                          @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        // 提交消费进度
        if (message.getId() % 2 == 1) {
            // ack 确认消息
            // 第二个参数 multiple ，用于批量确认消息，为了减少网络流量，手动确认可以被批处。
            // 1. 当 multiple 为 true 时，则可以一次性确认 deliveryTag 小于等于传入值的所有消息
            // 2. 当 multiple 为 false 时，则只确认当前 deliveryTag 对应的消息
            channel.basicAck(deliveryTag, false);
        }
    }

}
