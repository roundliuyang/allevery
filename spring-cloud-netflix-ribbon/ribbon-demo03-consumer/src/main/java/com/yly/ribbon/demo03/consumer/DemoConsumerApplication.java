package com.yly.ribbon.demo03.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 在 Spring Cloud Alibaba Nacos Discovery 组件，在和 Ribbon 集成时，提供了自定义负载均衡规则 NacosRule。规则如下：
 * 第一步，获得健康的方服务实例列表
 * 第二步，优先选择相同 Nacos 集群的服务实例列表，保证高性能。如果选择不到，则允许使用其它 Nacos 集群的服务实例列表，保证高可用
 * 第三步，从服务实例列表按照权重进行随机，选择一个服务实例返回
 *
 * 拓展小知识：如果我们想要实现类似 Dubbo 多版本 的功能，应该怎么实现呢？步骤如下：
 * 首先，通过 Nacos 元数据，在服务注册到 Nacos 时，将服务的版本号一起带上。
 * 然后，自定义 Ribbon 负载均衡规则 VersionRule，实现基于服务版本号来筛选服务实例。
 * 最后，使用 Ribbon 客户端级别的自定义配置，设置每个服务的负载均衡规则为 VersionRule，并配置调用的服务的版本号。
 */
@SpringBootApplication
public class DemoConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

    @Configuration
    public class RestTemplateConfiguration {

        @Bean
        @LoadBalanced
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

    }

    @RestController
    static class TestController {

        @Autowired
        private RestTemplate restTemplate;
        @Autowired
        private LoadBalancerClient loadBalancerClient;

        @GetMapping("/hello")
        public String hello(String name) {
            // 获得服务 `demo-provider` 的一个实例
            ServiceInstance instance = loadBalancerClient.choose("demo-provider");
            // 发起调用
            String targetUrl = instance.getUri() + "/echo?name=" + name;
            String response = restTemplate.getForObject(targetUrl, String.class);
            // 返回结果
            return "consumer:" + response;
        }

        @GetMapping("/hello02")
        public String hello02(String name) {
            // 直接使用 RestTemplate 调用服务 `demo-provider`
            String targetUrl = "http://demo-provider/echo?name=" + name;
            String response = restTemplate.getForObject(targetUrl, String.class);
            // 返回结果
            return "consumer:" + response;
        }

    }

}
