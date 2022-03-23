## 1.概述



如果胖友还没了解过分布式消息队列 RabbitMQ ，建议先阅读下艿艿写的 《芋道 RabbitMQ 极简入门》 文章。虽然这篇文章标题是安装部署，实际可以理解成《一文带你快速入门 RabbitMQ》，哈哈哈。

考虑这是 RabbitMQ 如何在 Spring Boot 整合与使用的文章，所以还是简单介绍下 RabbitMQ 是什么？

FROM 《AMQP 消息服务器 RabbitMQ》

RabbitMQ 是由 LShift 提供的一个 Advanced Message Queuing Protocol (AMQP) 的开源实现，由以高性能、健壮以及可伸缩性出名的 Erlang 写成，因此也是继承了这些优点。

- AMQP 里主要要说两个组件：Exchange 和 Queue ，绿色的 X 就是 Exchange ，红色的是 Queue ，这两者都在 Server 端，又称作 Broker ，这部分是 RabbitMQ 实现的。
- 而蓝色的则是客户端，通常有 Producer 和 Consumer 两种类型（角色）。

在本文中，我们会比 [《芋道 RabbitMQ 极简入门》](http://www.iocoder.cn/RabbitMQ/install/?self) 提供更多的生产者 Producer 和消费者 Consumer 的使用示例。例如说：

- 四种类型的交换机( Exchange )
- Producer 发送**顺序**消息，Consumer **顺序**消费消息。
- Producer 发送**定时**消息。
- Producer **批量**发送消息。
- Producer 发送**事务**消息。
- Consumer **广播**和**集群**消费消息。
- Consumer 批量消费消息。

> Exchange 根据 Routing Key 和 Binding Key 将消息路由到 Queue 。目前提供了 Direct、Topic、Fanout、Headers 四种类型。

















