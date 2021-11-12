package com.yly.rabbitmq.demo.producer;


import com.yly.rabbitmq.demo.message.Demo01Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class Demo01Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 指定 Exchange + RoutingKey ，从而路由到一个 Queue 中
    public void syncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo01Message.EXCHANGE, Demo01Message.ROUTING_KEY, message);
    }

    // 是不是觉得有点奇怪，这里我们传入的 RoutingKey 为队列名？！因为 RabbitMQ 有一条默认的 Exchange: (AMQP default) 规则：
    // 翻译过来的意思：默认交换器，隐式地绑定到每个队列，路由键等于队列名称。
    // 所以，此处即使我们传入的 RoutingKey 为队列名，一样可以发到对应队列。
    public void syncSendDefault(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo01Message.QUEUE, message);
    }

    // 通过 @Async 注解，声明异步调用该方法，从而实现异步消息到 RabbitMQ 中。因为 RabbitTemplate 并未像 KafkaTemplate 或 RocketMQTemplate 直接提供了异步发送消息的方法，
    // 所以我们需要结合 Spring 异步调用来实现。
    @Async
    public ListenableFuture<Void> asyncSend(Integer id) {
        try {
            // 发送消息
            this.syncSend(id);
            // 返回成功的 Future
            return AsyncResult.forValue(null);
        } catch (Throwable ex) {
            // 返回异常的 Future
            return AsyncResult.forExecutionException(ex);
        }
    }

}
