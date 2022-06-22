package com.alibaba.cloud.demo.sentinel.api;

/**
 * 该接口为 Dubbo 的服务端、消费端公用的接口定义。
 * 当前案例中，通过复制代码的方式实现接口发布，这不是最优雅的使用方法。更好的建议是通过maven坐标的方式独立维护api。
 */
public interface FooService {

    String sayHello(String name);

    long getCurrentTime(boolean slow);
}
