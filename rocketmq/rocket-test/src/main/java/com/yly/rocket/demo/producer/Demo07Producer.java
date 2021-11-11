package com.yly.rocket.demo.producer;


import com.yly.rocket.demo.message.Demo07Message;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class Demo07Producer {

    private static final String TX_PRODUCER_GROUP = "demo07-producer-group";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public TransactionSendResult sendMessageInTransaction(Integer id) {
        // 创建 Demo07Message 消息 ,创建内容为 Demo07Message 的 Spring Messaging Message 消息。
        Message<Demo07Message> message = MessageBuilder.withPayload(new Demo07Message().setId(id))
                .build();
        // 发送事务消息
        return rocketMQTemplate.sendMessageInTransaction(TX_PRODUCER_GROUP, Demo07Message.TOPIC, message,
                id);
        /*
            方法参数 txProducerGroup ，事务消息的生产者分组。因为 RocketMQ 是回查（请求）指定指定生产分组下的 Producer ，从而获得事务消息的状态，所以一定要正确设置。这里，我们设置了 "demo07-producer-group" 。
            方法参数 destination ，消息的 Topic + Tag 。
            方法参数 message ，消息，没什么特别。
            方法参数 arg ，后续我们调用本地事务方法的时候，会传入该 arg 。如果要传递多个方法参数给本地事务的方法，可以通过数组，例如说 Object[]{arg1, arg2, arg3} 这样的形式。
         */
    }






    /*
        在 Demo07Producer 类中，创建一个内部类 TransactionListenerImpl ，实现 MQ 事务的监听。
        在类上，添加 @RocketMQTransactionListener 注解，声明监听器的是生产者分组是 "demo07-producer-group" 的 Producer 发送的事务消息。
        实现 RocketMQLocalTransactionListener 接口，实现执行本地事务和检查本地事务的方法。

        实现 #executeLocalTransaction(...) 方法，实现执行本地事务。
        注意，这是一个模板方法。在调用这个方法之前，RocketMQTemplate 已经使用 Producer 发送了一条事务消息。然后根据该方法执行的返回的 RocketMQLocalTransactionState 结果，提交还是回滚该事务消息。
        这里，我们为了模拟 RocketMQ 回查 Producer 来获得事务消息的状态，所以返回了 RocketMQLocalTransactionState.UNKNOWN 未知状态。

        实现 #checkLocalTransaction(...) 方法，检查本地事务。
        在事务消息长事件未被提交或回滚时，RocketMQ 会回查事务消息对应的生产者分组下的 Producer ，获得事务消息的状态。此时，该方法就会被调用。
        这里，我们直接返回 RocketMQLocalTransactionState.COMMIT 提交状态。

        一般来说，有两种方式实现本地事务回查时，返回事务消息的状态。
        第一种，通过 msg 消息，获得某个业务上的标识或者编号，然后去数据库中查询业务记录，从而判断该事务消息的状态是提交还是回滚。

        第二种，记录 msg 的事务编号，与事务状态到数据库中。
        第一步，在 #executeLocalTransaction(...) 方法中，先存储一条 id 为 msg 的事务编号，状态为 RocketMQLocalTransactionState.UNKNOWN 的记录。
        第二步，调用带有事务的业务 Service 的方法。在该 Service 方法中，在逻辑都执行成功的情况下，更新 id 为 msg 的事务编号，状态变更为 RocketMQLocalTransactionState.COMMIT 。这样，我们就可以伴随这个事务的提交，更新 id 为 msg 的事务编号的记录的状为 RocketMQLocalTransactionState.COMMIT ，美滋滋。。
        第三步，要以 try-catch 的方式，调用业务 Service 的方法。如此，如果发生异常，回滚事务的时候，可以在 catch 中，更新 id 为 msg 的事务编号的记录的状态为 RocketMQLocalTransactionState.ROLLBACK 。😭 极端情况下，可能更新失败，则打印 error 日志，告警知道，人工介入。
        如此三步之后，我们在 #checkLocalTransaction(...) 方法中，就可以通过查找数据库，id 为 msg 的事务编号的记录的状态，然后返回。
        相比来说，艿艿倾向第二种，实现更加简单通用，对于业务开发者，更加友好。和有几个朋友沟通了下，他们也是采用第二种。
     */
    @RocketMQTransactionListener(txProducerGroup = TX_PRODUCER_GROUP)
    public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

        private Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            // ... local transaction process, return rollback, commit or unknown
            logger.info("[executeLocalTransaction][执行本地事务，消息：{} arg：{}]", msg, arg);
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            // ... check transaction status and return rollback, commit or unknown
            logger.info("[checkLocalTransaction][回查消息：{}]", msg);
            return RocketMQLocalTransactionState.COMMIT;
        }
    }

}
