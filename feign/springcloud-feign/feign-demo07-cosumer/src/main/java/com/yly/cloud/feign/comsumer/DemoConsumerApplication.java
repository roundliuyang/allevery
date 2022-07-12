package com.yly.cloud.feign.comsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign 和 Ribbon 都有请求重试的功能，两者都启用该功能的话，会产生冲突的问题。因此，有且只能启动一个的重试。目前比较推荐的是使用 Ribbon 来提供重试，如下是来自 Spring Cloud 开发者的说法：
 * 在 Spring Cloud OpenFeign 中，默认创建的是 NEVER_RETRY 不进行重试。如此，我们只需要配置 Ribbon 的重试功能即可。
 * 下面，让我们来搭建下 Feign + Ribbon 请求重试的使用示例。
 */
@SpringBootApplication
@EnableFeignClients
public class DemoConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

}
