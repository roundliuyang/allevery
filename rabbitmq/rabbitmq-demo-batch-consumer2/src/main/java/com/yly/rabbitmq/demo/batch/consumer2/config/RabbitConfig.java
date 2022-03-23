package com.yly.rabbitmq.demo.batch.consumer2.config;


import com.yly.rabbitmq.demo.batch.consumer2.message.Demo06Message;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    创建 RabbitConfig 配置类，添加自定义的 SimpleRabbitListenerContainerFactory Bean ，支持用于创建支持批量消费的 SimpleRabbitListenerContainer 。代码如下：
    DirectExchangeDemoConfiguration 配置类，用于定义 Queue、Exchange、Binding 的配置。
    相比「5.1 RabbitConfig」来说，额外增加了 batchSize = 10、receiveTimeout = 30 * 1000L、consumerBatchEnabled = 30 * 1000L 属性。😈 严格意义上来说，本小节才是真正意义上的批量消费消息。
 */
@Configuration
public class RabbitConfig {

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo06Queue() {
            return new Queue(Demo06Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demo06Exchange() {
            return new DirectExchange(Demo06Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo06Message.EXCHANGE
        // Routing key：Demo06Message.ROUTING_KEY
        // Queue：Demo06Message.QUEUE
        @Bean
        public Binding demo06Binding() {
            return BindingBuilder.bind(demo06Queue()).to(demo06Exchange()).with(Demo06Message.ROUTING_KEY);
        }

    }

    @Bean(name = "consumerBatchContainerFactory")
    public SimpleRabbitListenerContainerFactory consumerBatchContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        // 创建 SimpleRabbitListenerContainerFactory 对象
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        // 额外添加批量消费的属性
        factory.setBatchListener(true);
        factory.setBatchSize(10);
        factory.setReceiveTimeout(30 * 1000L);
        factory.setConsumerBatchEnabled(true);
        return factory;
    }

}
