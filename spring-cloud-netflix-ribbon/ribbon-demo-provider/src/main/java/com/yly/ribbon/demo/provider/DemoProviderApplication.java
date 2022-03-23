package com.yly.ribbon.demo.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController 提供的 /echo 接口，返回的结果会带有启动的服务器的端口。
 * 这样我们稍后在服务消费者使用 Ribbon 调用服务提供者时，从返回结果就知道调用的是哪个实例。
 */
@SpringBootApplication
public class DemoProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoProviderApplication.class, args);
    }

    @RestController
    static class TestController {

        private Logger logger = LoggerFactory.getLogger(TestController.class);

        @Value("${server.port}")
        private Integer serverPort;

        @GetMapping("/echo")
        public String echo(String name) throws InterruptedException {
            // 模拟执行 100ms 时长。方便后续我们测试请求超时
            Thread.sleep(100L);

            // 记录被调用的日志
            logger.info("[echo][被调用啦 name({})]", name);

            return serverPort + "-provider:" + name;
        }

    }

}
