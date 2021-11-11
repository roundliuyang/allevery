package com.yly.rocket.demo.consumer;


import com.yly.rocket.demo.message.Demo01Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
    整体和 「3.8 Demo01Consumer」 是一致的，主要有两个差异点，也是为什么我们又额外创建了这个消费者的原因。
    差异一，在类上，添加了 @RocketMQMessageListener 注解，声明消费的 Topic 还是 "DEMO_01" ，消费者分组修改成了 "demo01-A-consumer-group-DEMO_01" 。这样，我们就可以测试 RocketMQ 集群消费的特性。
    集群消费（Clustering）：集群消费模式下，相同 Consumer Group 的每个 Consumer 实例平均分摊消息。

    也就是说，如果我们发送一条 Topic 为 "DEMO_01" 的消息，可以分别被 "demo01-A-consumer-group-DEMO_01" 和 "demo01-consumer-group-DEMO_01" 都消费一次。
    但是，如果我们启动两个该示例的实例，则消费者分组 "demo01-A-consumer-group-DEMO_01" 和 "demo01-consumer-group-DEMO_01" 都会有多个 Consumer 示例。此时，我们再发送一条 Topic 为 "DEMO_01" 的消息，只会被 "demo01-consumer-group-DEMO_01" 的一个 Consumer 消费一次，也同样只会被 "demo01-A-consumer-group-DEMO_01" 的一个 Consumer 消费一次。
    好好理解上述的两段话，非常重要。

    通过集群消费的机制，我们可以实现针对相同 Topic ，不同消费者分组实现各自的业务逻辑。例如说：用户注册成功时，发送一条 Topic 为 "USER_REGISTER" 的消息。然后，不同模块使用不同的消费者分组，订阅该 Topic ，实现各自的拓展逻辑：
    积分模块：判断如果是手机注册，给用户增加 20 积分。
    优惠劵模块：因为是新用户，所以发放新用户专享优惠劵。
    站内信模块：因为是新用户，所以发送新用户的欢迎语的站内信。
    ... 等等
    这样，我们就可以将注册成功后的业务拓展逻辑，实现业务上的解耦，未来也更加容易拓展。同时，也提高了注册接口的性能，避免用户需要等待业务拓展逻辑执行完成后，才响应注册成功。

    差异二，实现 RocketMQListener 接口，在 T 泛型里，设置消费的消息对应的类不是 Demo01Message 类，而是 RocketMQ 内置的 MessageExt 类。通过 MessageExt 类，我们可以获取到消费的消息的更多信息，例如说消息的所属队列、创建时间等等属性，
    不过消息的内容(body)就需要自己去反序列化。当然，一般情况下，我们不会使用 MessageExt 类。

 */
@Component
@RocketMQMessageListener(
        topic = Demo01Message.TOPIC,
        consumerGroup = "demo01-A-consumer-group-" + Demo01Message.TOPIC
)
public class Demo01AConsumer implements RocketMQListener<MessageExt> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(MessageExt message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
