package com.yly.rabbitmq.demo.message;

import java.io.Serializable;

/**
 * 在消息类里，我们枚举了 Exchange、Queue、RoutingKey 的名字
 * 重点看我们新定义的路由键 ROUTING_KEY = "#.yu.nai" ，我们要匹配以 "yu.nai" 结尾，开头可以是任意个单词的.
 */
public class Demo02Message implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_02";

    public static final String EXCHANGE = "EXCHANGE_DEMO_02";

    public static final String ROUTING_KEY = "#.yu.nai";

    /**
     * 编号
     */
    private Integer id;

    public Demo02Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo02Message{" +
                "id=" + id +
                '}';
    }

}
