package com.yly.rocket.demo.message;

/**
 * 示例 06 的 Message 消息
 * TOPIC 静态属性，我们设置该消息类对应 Topic 为 "DEMO_06"
 */
public class Demo06Message {

    public static final String TOPIC = "DEMO_06";

    /**
     * 编号
     */
    private Integer id;

    public Demo06Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo06Message{" +
                "id=" + id +
                '}';
    }

}
