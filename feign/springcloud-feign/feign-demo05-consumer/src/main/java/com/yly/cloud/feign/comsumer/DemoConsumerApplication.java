package com.yly.cloud.feign.comsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 在使用 Spring Cloud 的项目中，我们大多数是通过 Feign 调用从 Ribbon 负载均衡选择的服务实例，而 Ribbon 是通过注册中心获取到的服务实例列表。但是有些场景下，可能想要单独使用 Feign 调用，例如说：
 *
 * 调用第三方服务，例如说短信云服务、推送云服务
 * 调用的虽然是内部服务，但是并没有注册到注册中心，而是使用 Nginx 代理并负载均衡实现高可用
 * 下面，让我们来搭建下 Feign 单独使用的示例。
 */
@SpringBootApplication
@EnableFeignClients
public class DemoConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

}
