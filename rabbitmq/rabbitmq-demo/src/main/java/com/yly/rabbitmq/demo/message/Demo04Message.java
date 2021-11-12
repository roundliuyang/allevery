package com.yly.rabbitmq.demo.message;

import java.io.Serializable;

/**
 * 我们未定意思 RoutingKey 的名字。因为，Headers Exchange 是通过 Exchange + Headers 的组合。
 * 我们定义一个 Headers 键值对，color = red 。
 */
public class Demo04Message implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_04_A";

    public static final String EXCHANGE = "EXCHANGE_DEMO_04";

    public static final String HEADER_KEY = "color";
    public static final String HEADER_VALUE = "red";

    /**
     * 编号
     */
    private Integer id;

    public Demo04Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo04Message{" +
                "id=" + id +
                '}';
    }

}
