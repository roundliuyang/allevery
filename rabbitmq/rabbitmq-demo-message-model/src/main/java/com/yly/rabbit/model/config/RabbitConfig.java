package com.yly.rabbit.model.config;


import com.yly.rabbit.model.message.BroadcastMessage;
import com.yly.rabbit.model.message.ClusteringMessage;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * 广播消费的示例的配置
     */
    public static class BroadcastingConfiguration {

        // 创建 Topic Exchange
        @Bean
        public TopicExchange broadcastingExchange() {
            return new TopicExchange(BroadcastMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

    }

    /**
     * 集群消费的示例的配置
     * 在这里，我们创建了 Exchange 类型是 Topic 。为什么不选择Exchange 类型是Direct 呢?考虑到集群消费的模式，会存在多 Consumer Group 消费的情况，
     * 显然我们要支持一条消息投递到多个 Queue 中，所以 Direct Exchange 基本就被排除了。
     * 为什么不选择 Exchange 类型是 Fanout 或者 Headers 呢？实际是可以的，不过询问了朋友(didi) Spring Cloud Stream RabbitMQ 是怎么实现的。
     * 得知答案是默认是使用 Topic Exchange 的，所以艿艿示例这里也就使用 Topic Exchange 类型了。
     */
    public static class ClusteringConfiguration {

        // 创建 Topic Exchange
        @Bean
        public TopicExchange clusteringExchange() {
            return new TopicExchange(ClusteringMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

    }

}
