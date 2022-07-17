package com.yly.springcloud.labx10.rabbitmqdemo.producerdemo.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {

    // 通过 @Output 注解，声明了一个名字为 demo01-output 的 Output Binding。注意，这个名字要和我们配置文件中的 spring.cloud.stream.bindings 配置项对应上
    // 同时，@Output 注解的方法的返回结果为 MessageChannel 类型，可以使用它发送消息。
    @Output("demo01-output")
    MessageChannel demo01Output();

}
