package com.yly.cloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 默认情况下，Feign 通过 JDK 自带的 HttpURLConnection 封装了 Client.Default，实现 HTTP 调用的客户端。因为 HttpURLConnection 缺少对 HTTP 连接池的支持，所以性能较低，在并发到达一定量级后基本会出现。
 * 因此 Feign 提供了另外两个 HTTP 客户端：
 * ApacheHttpClient，基于 Apache HttpClient 封装
 * OkHttpClient，基于 OkHttp 封装
 *
 * 修改 application.yaml 配置文件，额外添加如下配置：
 * feign:
 *   # Feign Apache HttpClient 配置项，对应 FeignHttpClientProperties 配置属性类
 *   httpclient:
 *     enabled: true # 是否开启。默认为 true
 *     max-connections: 200 # 最大连接数。默认为 200
 *     max-connections-per-route: 50 # 每个路由的最大连接数。默认为 50。router = host + port
 *
 *  通过 feign.httpclient 配置项，我们可以开启 Feign Apache HttpClient，并进行自定义配置。在 FeignHttpClientProperties 配置属性类中，还有其它配置项，胖友可以简单看看。
 *  不过有一点要注意，虽然说 feign.httpclient.enable 默认为 true 开启，但是还是需要引入 feign-httpclient 依赖，才能创建 ApacheHttpClient 对象。
 *
 *  OkHttp 和 Apache HttpClient 在性能方面是基本接近的，有资料说 OkHttp 好一些，也有资料说 HttpClient 好一些。艿艿建议的话，按照自己对哪一个更熟悉一点，就选择哪一个。
 *  这里有一篇两者对比的文章《HTTP 客户端连接，选择 HttpClient 还是OkHttp？》，感兴趣的胖友可以阅读一波。
 */
@SpringBootApplication
@EnableFeignClients
public class DemoConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

}
