package com.yly.rabbitmq.transaction.config;


import com.yly.rabbitmq.transaction.message.Demo11Message;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement                // 开启Spring Transaction 的支持。
public class RabbitConfig {

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo11Queue() {
            return new Queue(Demo11Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demo11Exchange() {
            return new DirectExchange(Demo11Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo11Message.EXCHANGE
        // Routing key：Demo11Message.ROUTING_KEY
        // Queue：Demo11Message.QUEUE
        @Bean
        public Binding demo11Binding() {
            return BindingBuilder.bind(demo11Queue()).to(demo11Exchange()).with(Demo11Message.ROUTING_KEY);
        }

    }

    // 创建 RabbitTransactionManager 事务管理器 Bean 。
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
        // 设置 RabbitTemplate 支持事务
        rabbitTemplate.setChannelTransacted(true);

        // 创建 RabbitTransactionManager 对象
        return new RabbitTransactionManager(connectionFactory);
    }

}
