package com.yly.rabbitmq.demo.batch.config;


import com.yly.rabbitmq.demo.batch.message.Demo05Message;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * 添加 BatchingRabbitTemplate 的配置
 * DirectExchangeDemoConfiguration 配置类，用于定义 Queue、Exchange、Binding 的配置。
 * #batchRabbitTemplate(ConnectionFactory connectionFactory) 方法，创建 BatchingRabbitTemplate Bean 对象。
 * 具体的 batchSize、bufferLimit、timeout 数值配置多少，根据自己的应用来。这里，我们故意将 timeout 配置成了 30 秒，主要为了演示之用。
 * 创建 BatchingRabbitTemplate 对象的代码，艿艿已经添加注释，可以自己阅读理解下噢。
 */
@Configuration
public class RabbitConfig {

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo05Queue() {
            return new Queue(Demo05Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demo05Exchange() {
            return new DirectExchange(Demo05Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo05Message.EXCHANGE
        // Routing key：Demo05Message.ROUTING_KEY
        // Queue：Demo05Message.QUEUE
        @Bean
        public Binding demo05Binding() {
            return BindingBuilder.bind(demo05Queue()).to(demo05Exchange()).with(Demo05Message.ROUTING_KEY);
        }

    }

    @Bean
    public BatchingRabbitTemplate batchRabbitTemplate(ConnectionFactory connectionFactory) {
        // 创建 BatchingStrategy 对象，代表批量策略
        int batchSize = 16384; // 超过收集的消息数量的最大条数。
        int bufferLimit = 33554432; // 每次批量发送消息的最大内存
        int timeout = 30000; // 超过收集的时间的最大等待时长，单位：毫秒
        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, bufferLimit, timeout);

        // 创建 TaskScheduler 对象，用于实现超时发送的定时器
        TaskScheduler taskScheduler = new ConcurrentTaskScheduler();

        // 创建 BatchingRabbitTemplate 对象
        BatchingRabbitTemplate batchTemplate = new BatchingRabbitTemplate(batchingStrategy, taskScheduler);
        batchTemplate.setConnectionFactory(connectionFactory);
        return batchTemplate;
    }

}
