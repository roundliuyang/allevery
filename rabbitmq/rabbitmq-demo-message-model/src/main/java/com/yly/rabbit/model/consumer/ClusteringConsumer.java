package com.yly.rabbit.model.consumer;


import com.yly.rabbit.model.message.ClusteringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/*
    相比其它 Consumer 示例来说，这里添加的 @RabbitListener 注解复杂很多。
    在 bindings 属性，我们添加了 @QueueBinding 注解，来定义了一个 Binding 。通过 key 属性，设置使用的 RoutingKey 为 # ，匹配所有。这就是为什么我们在「9.1.3 ClusteringMessage」未定义 RoutingKey ，以及在「9.1.5 ClusteringProducer」中使用 routingKey = null 的原因。
    在 exchange 属性，我们添加了 @Exchange 注解，设置了对应 EXCHANGE_CLUSTERING 这个 Exchange 。
    type 属性，设置是 TopicExchange 。
    declare 属性，因为「9.1.4 RabbitConfig」中，已经配置创建这个 Exchange 了。
    在 value 属性，我们添加了 @Queue 注解，设置消费 QUEUE_CLUSTERING-GROUP-01 这个 Queue 的消息。
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        name = ClusteringMessage.QUEUE + "-" + "GROUP-01"
                ),
                exchange = @Exchange(
                        name = ClusteringMessage.EXCHANGE,
                        type = ExchangeTypes.TOPIC,
                        declare = "false"
                ),
                key = "#"
        )
)
public class ClusteringConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(ClusteringMessage message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
