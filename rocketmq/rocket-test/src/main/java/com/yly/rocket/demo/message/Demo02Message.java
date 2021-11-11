package com.yly.rocket.demo.message;

/**
 * 示例 02 的 Message 消息
 * TOPIC 静态属性，我们设置该消息类对应 Topic 为 "DEMO_02" 。
 * 其它都和 「3.5 Demo01Message」 是一样的。重新申明的原因是，避免污染 「3. 快速入门」 。😈 后续，每个小节的内容，我们也会通过创建新的 Message 类，保证多个示例之间的独立。
 */
public class Demo02Message {

    public static final String TOPIC = "DEMO_02";

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
