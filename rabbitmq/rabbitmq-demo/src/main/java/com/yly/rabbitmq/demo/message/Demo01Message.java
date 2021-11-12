package com.yly.rabbitmq.demo.message;

import java.io.Serializable;

/**
 * 注意，要实现 Java Serializable 序列化接口。因为 RabbitTemplate 默认使用 Java 自带的序列化方式，进行序列化 POJO 类型的消息。
 * 在消息类里，我们枚举了 Exchange、Queue、RoutingKey 的名字。
 */
public class Demo01Message implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_01";

    public static final String EXCHANGE = "EXCHANGE_DEMO_01";

    public static final String ROUTING_KEY = "ROUTING_KEY_01";

    /**
     * 编号
     */
    private Integer id;

    public Demo01Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo01Message{" +
                "id=" + id +
                '}';
    }

}
