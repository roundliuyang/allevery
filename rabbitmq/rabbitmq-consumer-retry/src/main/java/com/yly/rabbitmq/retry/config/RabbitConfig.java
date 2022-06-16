package com.yly.rabbitmq.retry.config;


import com.yly.rabbitmq.retry.message.Demo07Message;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 相比「3.1.5 RabbitConfig」来说，主要有两个差异点。
 * 第一点，创建的正常 Queue 额外设置了，当消息成为死信时，RabbitMQ 自动转发到 Exchange 为 Demo07Message.EXCHANGE，RoutingKey 为 Demo07Message.DEAD_ROUTING_KEY 的死信队列中。
 * 第二点，通过 #demo07DeadQueue() 方法来创建死信队列的 Queue ，通过 #demo07DeadBinding() 方法来创建死信队列的 Binding 。😈 因为我们重用了 Exchange 为 Demo07Message.EXCHANGE ，所以无需创建。当然，胖友也可以根据自己的需要，创建死信队列的 Exchange 。
 */
@Configuration
public class RabbitConfig {

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo07Queue() {
            return QueueBuilder.durable(Demo07Message.QUEUE) // durable: 是否持久化
                    .exclusive() // exclusive: 是否排它
                    .autoDelete() // autoDelete: 是否自动删除
                    .deadLetterExchange(Demo07Message.EXCHANGE)
                    .deadLetterRoutingKey(Demo07Message.DEAD_ROUTING_KEY)
                    .build();
        }

        // 创建 Dead Queue
        @Bean
        public Queue demo07DeadQueue() {
            return new Queue(Demo07Message.DEAD_QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demo07Exchange() {
            return new DirectExchange(Demo07Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo07Message.EXCHANGE
        // Routing key：Demo07Message.ROUTING_KEY
        // Queue：Demo07Message.QUEUE
        @Bean
        public Binding demo07Binding() {
            return BindingBuilder.bind(demo07Queue()).to(demo07Exchange()).with(Demo07Message.ROUTING_KEY);
        }

        // 创建 Dead Binding
        // Exchange：Demo07Message.EXCHANGE
        // Routing key：Demo07Message.DEAD_ROUTING_KEY
        // Queue：Demo07Message.DEAD_QUEUE
        @Bean
        public Binding demo07DeadBinding() {
            return BindingBuilder.bind(demo07DeadQueue()).to(demo07Exchange()).with(Demo07Message.DEAD_ROUTING_KEY);
        }

    }

}
