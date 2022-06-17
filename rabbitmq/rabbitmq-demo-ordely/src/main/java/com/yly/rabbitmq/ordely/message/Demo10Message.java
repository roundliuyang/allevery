package com.yly.rabbitmq.ordely.message;

import java.io.Serializable;

/**的
 * 定义了 QUEUE_DEMO_10- 的四个子 Queue
 * 定工了统一的Exchange
 * 暂未定义 RoutingKey 名字，我们会使用“队列编号”作为 RoutingKey,然后路由消息到每个子Queue 中。
 */
public class Demo10Message implements Serializable {

    private static final String QUEUE_BASE = "QUEUE_DEMO_10-";
    public static final String QUEUE_0 = QUEUE_BASE + "0";
    public static final String QUEUE_1 = QUEUE_BASE + "1";
    public static final String QUEUE_2 = QUEUE_BASE + "2";
    public static final String QUEUE_3 = QUEUE_BASE + "3";

    public static final int QUEUE_COUNT = 4;

    public static final String EXCHANGE = "EXCHANGE_DEMO_10";

    /**
     * 编号
     */
    private Integer id;

    public Demo10Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo10Message{" +
                "id=" + id +
                '}';
    }

}
