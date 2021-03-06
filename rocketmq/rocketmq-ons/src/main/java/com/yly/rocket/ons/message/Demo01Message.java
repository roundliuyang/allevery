package com.yly.rocket.ons.message;

/**
 * 示例 01 的 Message 消息
 */
public class Demo01Message {

    public static final String TOPIC = "TOPIC_YUNAI_TEST";

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
