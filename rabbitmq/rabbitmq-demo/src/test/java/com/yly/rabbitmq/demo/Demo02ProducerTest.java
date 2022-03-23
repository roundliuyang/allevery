package com.yly.rabbitmq.demo;


import com.yly.rabbitmq.demo.producer.Demo02Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
/*
    前面讲到 Direct Exchange路由规则，是完全匹配 binding key 与routing key，但这种严格的匹配方式在很多情况下不能满足实际业务需求。

    Topic Exchange 在匹配规则上进行了扩展，它与 Direct 类型的Exchange 相似，也是将消息路由到 binding key 与 routing key 相匹配的 Queue 中，但这里的匹配规则有些不同，它约定：

    routing key 为一个句点号 "." 分隔的字符串。我们将被句点号"."分隔开的每一段独立的字符串称为一个单词，例如 “stock.usd.nyse”、”nyse.vmw”、”quick.orange.rabbit”
    binding key 与 routing key 一样也是句点号 "." 分隔的字符串。
    binding key 中可以存在两种特殊字符 "*" 与 "#"，用于做模糊匹配。其中 "*" 用于匹配一个单词，"#" 用于匹配多个单词（可以是零个）。

    以下面中的配置为例：
    routingKey="quick.orange.rabbit" 的消息会同时路由到 Q1 与 Q2 。
    routingKey="lazy.orange.fox" 的消息会路由到 Q1 。
    routingKey="lazy.brown.fox" 的消息会路由到 Q2 。
    routingKey="lazy.pink.rabbit" 的消息会路由到Q2（只会投递给 Q2 一次，虽然这个 routingKey 与 Q2 的两个 bindingKey 都匹配）。
    routingKey="quick.brown.fox"、routingKey="orange"、routingKey="quick.orange.male.rabbit" 的消息将会被丢弃，因为它们没有匹配任何 bindingKey 。

 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo02ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo02Producer producer;

    // #testSyncSendSuccess() 方法，发送消息的 RoutingKey 是 "da.yu.nai" ，可以匹配到 "DEMO_QUEUE_02" 。
    @Test
    public void testSyncSendSuccess() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id, "da.yu.nai");
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    // #testSyncSendFailure() 方法，发送消息的 RoutingKey 是 "yu.nai.shuai" ，无法匹配到 "DEMO_QUEUE_02",因为 无法匹配到 "DEMO_QUEUE_02" ，自然 Demo02Consumer 无法进行消费。
    @Test
    public void testSyncSendFailure() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id, "yu.nai.shuai");
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
