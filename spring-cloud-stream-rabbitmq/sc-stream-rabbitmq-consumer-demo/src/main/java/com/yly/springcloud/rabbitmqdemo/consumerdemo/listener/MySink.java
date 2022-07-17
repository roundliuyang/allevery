package com.yly.springcloud.rabbitmqdemo.consumerdemo.listener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

    /**
     * 我们通过 @Input 注解，声明了一个名字为 demo01-input 的 Input Binding。注意，这个名字要和我们配置文件中的 spring.cloud.stream.bindings 配置项对应上。
     * 同时，@Input 注解的方法的返回结果为 SubscribableChannel 类型，可以使用它订阅消息来消费。
     *
     * 那么，我们是否要实现 MySink 接口呢？答案也是不需要，还是全部交给 Spring Cloud Stream 的 BindableProxyFactory 大兄弟来解决。
     * BindableProxyFactory 会通过动态代理，自动实现 MySink 接口。 而 @Input 注解的方法的返回值，BindableProxyFactory 会扫描带有 @Input 注解的方法，自动进行创建。
     */
    String DEMO01_INPUT = "demo01-input";

    @Input(DEMO01_INPUT)
    SubscribableChannel demo01Input();

}
