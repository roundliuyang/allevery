package com.yly.rabbitmq.demo.config;


import com.yly.rabbitmq.demo.message.Demo01Message;
import com.yly.rabbitmq.demo.message.Demo02Message;
import com.yly.rabbitmq.demo.message.Demo03Message;
import com.yly.rabbitmq.demo.message.Demo04Message;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 在 DirectExchangeDemoConfiguration 内部静态类中，我们创建了 Exchange、Queue、Binding 三个 Bean ，后续 RabbitAdmin 会自动创建交换器、队列、绑定器。
 */
@Configuration
public class RabbitConfig {

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo01Queue() {
            return new Queue(Demo01Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demo01Exchange() {
            return new DirectExchange(Demo01Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo01Message.EXCHANGE
        // Routing key：Demo01Message.ROUTING_KEY
        // Queue：Demo01Message.QUEUE
        @Bean
        public Binding demo01Binding() {
            return BindingBuilder.bind(demo01Queue()).to(demo01Exchange()).with(Demo01Message.ROUTING_KEY);
        }

    }

    /**
     * 在 TopicExchangeDemoConfiguration 内部静态类中，我们也是创建了 Exchange、Queue、Binding 三个 Bean 。有差异点的是，这次我们创建的是 TopicExchange
     * Topic Exchange 示例的配置类
     */
    public static class TopicExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo02Queue() {
            return new Queue(Demo02Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Topic Exchange
        @Bean
        public TopicExchange demo02Exchange() {
            return new TopicExchange(Demo02Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo02Message.EXCHANGE
        // Routing key：Demo02Message.ROUTING_KEY
        // Queue：Demo02Message.QUEUE
        @Bean
        public Binding demo02Binding() {
            return BindingBuilder.bind(demo02Queue()).to(demo02Exchange()).with(Demo02Message.ROUTING_KEY);
        }

    }

    /**
     * 在 FanoutExchangeDemoConfiguration 内部静态类中，我们也是创建了 Exchange、Queue、Binding 三个 Bean 。有差异点的是，这次我们创建的是 FanoutExchange 。
     * 同时，因为我们要投递到两个 Queue 中，所以我们创建了两个 Binding 。
     * Fanout Exchange 示例的配置类
     */
    public static class FanoutExchangeDemoConfiguration {

        // 创建 Queue A
        @Bean
        public Queue demo03QueueA() {
            return new Queue(Demo03Message.QUEUE_A, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Queue B
        @Bean
        public Queue demo03QueueB() {
            return new Queue(Demo03Message.QUEUE_B, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Fanout Exchange
        @Bean
        public FanoutExchange demo03Exchange() {
            return new FanoutExchange(Demo03Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding A
        // Exchange：Demo03Message.EXCHANGE
        // Queue：Demo03Message.QUEUE_A
        @Bean
        public Binding demo03BindingA() {
            return BindingBuilder.bind(demo03QueueA()).to(demo03Exchange());
        }

        // 创建 Binding B
        // Exchange：Demo03Message.EXCHANGE
        // Queue：Demo03Message.QUEUE_B
        @Bean
        public Binding demo03BindingB() {
            return BindingBuilder.bind(demo03QueueB()).to(demo03Exchange());
        }

    }

    /**
     * Headers Exchange 示例的配置类
     * 在 TopicExchangeDemoConfiguration 内部静态类中，我们也是创建了 Exchange、Queue、Binding 三个 Bean 。有差异点的是，这次我们创建的是 HeadersExchange 。
     * 同时，我们创建的 Binding 是基于 Headers 匹配。
     */
    public static class HeadersExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo04Queue() {
            return new Queue(Demo04Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Headers Exchange
        @Bean
        public HeadersExchange demo04Exchange() {
            return new HeadersExchange(Demo04Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo04Message.EXCHANGE
        // Queue：Demo04Message.QUEUE
        // Headers: Demo04Message.HEADER_KEY + Demo04Message.HEADER_VALUE
        @Bean
        public Binding demo4Binding() {
            return BindingBuilder.bind(demo04Queue()).to(demo04Exchange())
                    .where(Demo04Message.HEADER_KEY).matches(Demo04Message.HEADER_VALUE); // 配置 Headers 匹配
        }

    }

}
