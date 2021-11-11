package com.yly.rocket.demo.producer;


import com.yly.rocket.demo.Application;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/*
    在分布式消息队列中，目前唯一提供完整的事务消息的，只有 RocketMQ 。关于这一点，还是可以鼓吹下的。
    可能会有胖友怒喷艿艿，RabbitMQ 和 Kafka 也有事务消息啊，也支持发送事务消息的发送，以及后续的事务消息的 commit提交或 rollbackc 回滚。但是要考虑一个极端的情况，在本地数据库事务已经提交的时时候，如果因为网络原因，又或者崩溃等等意外，导致事务消息没有被 commit ，最终导致这条事务消息丢失，分布式事务出现问题。
    相比来说，RocketMQ 提供事务回查机制，如果应用超过一定时长未 commit 或 rollback 这条事务消息，RocketMQ 会主动回查应用，询问这条事务消息是 commit 还是 rollback ，从而实现事务消息的状态最终能够被 commit 或是 rollback ，达到最终事务的一致性。
    这也是为什么艿艿在上面专门加粗“完整的”三个字的原因。可能上述的描述，对于绝大多数没有了解过分布式事务的胖友，会比较陌生，所以推荐阅读如下两篇文章：
    《阿里云消息队列 MQ —— 事务消息》
    《芋道 RocketMQ 源码解析 —— 事务消息》
    热心的艿艿：虽然说 RabbitMQ、Kafka 并未提供完整的事务消息，但是社区里，已经基于它们之上拓展，提供了事务回查的功能。例如说：Myth ，采用消息队列解决分布式事务的开源框架, 基于 Java 语言来开发（JDK1.8），支持 Dubbo，Spring Cloud，Motan 等 RPC 框架进行分布式事务。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo07ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo07Producer producer;

    @Test
    public void testSendMessageInTransaction() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.sendMessageInTransaction(id);
        logger.info("[testSendMessageInTransaction][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
    /*
        我们来执行 #testSendMessageInTransaction() 方法，测试发送事务消息。控制台输出如下：
        # TransactionListenerImpl 执行 executeLocalTransaction 方法，先执行本地事务的逻辑
        2019-12-06 01:23:00.928  INFO 3205 --- [           main] p.Demo07Producer$TransactionListenerImpl : [executeLocalTransaction][执行本地事务，消息：GenericMessage [payload=byte[17], headers={rocketmq_TOPIC=DEMO_07, rocketmq_FLAG=0, id=ce85ed2a-d7ae-9cc6-226d-a8beb2e219ab, contentType=application/json, rocketmq_TRANSACTION_ID=0AAB01730C8518B4AAC214E570BD0002, timestamp=1575480180928}] arg：1575480180]

        # Producer 发送事务消息成功，但是因为 executeLocalTransaction 方法返回的是 UNKOWN 状态，所以事务消息并未提交或者回滚
        2019-12-06 01:23:00.930  INFO 3205 --- [           main] c.i.s.l.r.producer.Demo07ProducerTest    : [testSendMessageInTransaction][发送编号：[1575480180] 发送结果：[SendResult [sendStatus=SEND_OK, msgId=0AAB01730C8518B4AAC214E570BD0002, offsetMsgId=null, messageQueue=MessageQueue [topic=DEMO_07, brokerName=broker-a, queueId=3], queueOffset=38]]]

        # RocketMQ Broker 在发送事务消息 30 秒后，发现事务消息还未提交或是回滚，所以回查 Producer 。此时，checkLocalTransaction 方法返回 COMMIT ，所以该事务消息被提交
        2019-12-06 01:23:35.155  INFO 3205 --- [pool-1-thread-1] p.Demo07Producer$TransactionListenerImpl : [checkLocalTransaction][回查消息：GenericMessage [payload=byte[17], headers={rocketmq_QUEUE_ID=3, TRANSACTION_CHECK_TIMES=1, rocketmq_BORN_TIMESTAMP=1575480180925, rocketmq_TOPIC=DEMO_07, rocketmq_FLAG=0, rocketmq_MESSAGE_ID=0AAB017300002A9F0000000000132AC3, rocketmq_TRANSACTION_ID=0AAB01730C8518B4AAC214E570BD0002, rocketmq_SYS_FLAG=0, id=0fc2f199-25fb-5911-d577-f81b8003f0f8, CLUSTER=DefaultCluster, rocketmq_BORN_HOST=10.171.1.115, contentType=application/json, timestamp=1575480215155}]]

        # 事务消息被提交，所以该消息被 Consumer 消费
        2019-12-06 01:23:35.160  INFO 3205 --- [MessageThread_1] c.i.s.l.r.consumer.Demo07Consumer        : [onMessage][线程编号:89 消息内容：Demo07Message{id=1575480180}]

        在TransactionListenerImpl 中，我们已经使用了 @RocketMQTransactionListener 注解，设置 MQ 事务监听器的信息。具体属性如下：
     */

}


/*
    想写点彩蛋，又发现没有什么好写的。咳咳咳。
    从个人使用感受上来说，RocketMQ 提供的特性，可能是最为丰富的，可以说是最适合业务团队的分布式消息队列。艿艿是从 2013 年开始用 RocketMQ 的，主要踩的坑，都是自己错误使用导致的。例如说：

    刚开始略微抠门，只搭建了 RocketMQ 一主一从集群，结果恰好倒霉，不小心挂了主。
    多个 Topic 公用一个消费者集群，导致使用相同线程池。结果，嘿~有个消费逻辑需要调用第三方服务，某一天突然特别慢，导致消费积压，进而整个线程池堵塞。
    相同消费者分组，订阅了不同的 Topic ，导致相互覆盖。
    如果胖友在使用阿里云的话，建议量级较小的情况下，可以考虑先使用 阿里云 —— 消息队列 MQ 服务 。毕竟搭建一个高可用的 RocketMQ 量主两从的集群，最最最起码要两个 ECS 节点。同时，需要一定的维护和监控成本。😈 我们目前有个项目，就是直接使用阿里云的消息队列服务。

    消息队列是非常重要的组件，推荐阅读下 RocketMQ 的最佳实践：
    《阿里云 —— 消息队列 MQ 服务 —— 最佳实践》
    《RocketMQ 官方文档 —— 最佳实践》
 */