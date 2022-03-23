package com.yly.rabbitmq.demo.message;

import java.io.Serializable;

/**
 * 我们未定 RoutingKey 的名字。因为，Fanout Exchange 仅需要 Exchange 即可。
 * 我们定义两个 Queue 的名字。因为，我们要测试 Fanout Exchange 投递到多个 Queue 的效果。
 */
public class Demo03Message implements Serializable {

    public static final String QUEUE_A = "QUEUE_DEMO_03_A";
    public static final String QUEUE_B = "QUEUE_DEMO_03_B";

    public static final String EXCHANGE = "EXCHANGE_DEMO_03";

    /**
     * 编号
     */
    private Integer id;

    public Demo03Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo03Message{" +
                "id=" + id +
                '}';
    }

}
