package com.yly.rocket.demo.producer;


import com.yly.rocket.demo.message.Demo06Message;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
相比 「3.6 Demo01Producer」 来说，调用了对应的 Orderly 方法，从而实现发送顺序消息。
同时，需要传入方法参数 hashKey ，作为选择消息队列的键。

@param hashKey      use this key to select queue. for example: orderId, productId ...
一般情况下，可以使用订单号、商品号、用户编号。
在 RocketMQ 中，Producer 可以根据定义 MessageQueueSelector 消息队列选择策略，选择 Topic 下的队列。目前提供三种策略：

SelectMessageQueueByHash ，基于 hashKey 的哈希值取余，选择对应的队列。
SelectMessageQueueByRandom ，基于随机的策略，选择队列。
SelectMessageQueueByMachineRoom ，😈 有点看不懂，目前是空的实现，暂时无视吧。
未使用 MessageQueueSelector 时，采用轮询的策略，选择队列。

RocketMQTemplate 在发送顺序消息时，默认采用 SelectMessageQueueByHash 策略。如此，相同的 hashKey 的消息，就可以发送到相同的 Topic 的对应队列中。这种形式，就是我们上文提到的普通顺序消息的方式。
 */
@Component
public class Demo06Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSendOrderly(Integer id) {
        // 创建 Demo06Message 消息
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 同步发送消息
        return rocketMQTemplate.syncSendOrderly(Demo06Message.TOPIC, message, String.valueOf(id));
    }

    public void asyncSendOrderly(Integer id, SendCallback callback) {
        // 创建 Demo06Message 消息
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 异步发送消息
        rocketMQTemplate.asyncSendOrderly(Demo06Message.TOPIC, message, String.valueOf(id), callback);
    }

    public void onewaySendOrderly(Integer id) {
        // 创建 Demo06Message 消息
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 异步发送消息
        rocketMQTemplate.sendOneWayOrderly(Demo06Message.TOPIC, message, String.valueOf(id));
    }

}
