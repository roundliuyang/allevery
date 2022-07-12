package com.yly.cloud.feign.comsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 引入依赖,修改 pom.xml，额外引入 feign-okhttp 依赖
 * 修改 application.yaml 配置文件，额外添加如下配置：
 * feign:
 *   httpclient:
 *     enabled: false # 是否开启。默认为 true
 *   okhttp:
 *     enabled: true # 是否开启。默认为 false
 * 通过设置 feign.okhttp.enabled 配置项为 true，我们可以开启 Feign OkHttp。目前暂无其它 feign.okhttp 配置项。
 *
 * 另外，因为 feign.httpclient.enabled 配置项默认为 true，所以需要手动设置成 false，避免使用了 Feign Apache HttpClient。
 */
@SpringBootApplication
@EnableFeignClients
public class DemoConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

}
