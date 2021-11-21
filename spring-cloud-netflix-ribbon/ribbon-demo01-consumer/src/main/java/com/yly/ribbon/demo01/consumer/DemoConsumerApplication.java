package com.yly.ribbon.demo01.consumer;

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

        /*
            loadBalancerClient 属性，LoadBalancerClient 对象，负载均衡客户端。稍后我们会使用它，
            从 Nacos 获取的服务 demo-provider 的实例列表中，选择一个进行 HTTP 调用。
            当然，LoadBalancerClient 的服务的实例列表，是来自 DiscoveryClient 提供的
         */
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
        /*
            /hello 接口，使用 LoadBalancerClient 先选择服务 demo-provider 的一个实例，
            在使用 RestTemplate 调用服务 demo-provider 的 /demo 接口。不过要注意，这里执行会报如下异常：
            java.lang.IllegalStateException: No instances available for 10.171.1.115
            因为我们这里创建的 RestTemplate Bean 是添加了 @LoadBalanced 注解，它会把传入的 "10.171.1.115" 当做一个服务，
            显然是找不到对应的服务实例，所以会报 IllegalStateException 异常。
            解决办法也非常简单，再声明一个未使用 @LoadBalanced 注解的 RestTemplate Bean 即可，并使用它发起请求。
         */

        @GetMapping("/hello02")
        public String hello02(String name) {
            // 直接使用 RestTemplate 调用服务 `demo-provider`
            String targetUrl = "http://demo-provider/echo?name=" + name;
            String response = restTemplate.getForObject(targetUrl, String.class);
            // 返回结果
            return "consumer:" + response;
        }
        /*
            /hello02 接口，直接使用 RestTemplate 调用服务 demo-provider，代码精简了。这里要注意，在使用 @LoadBalanced 注解的 RestTemplate Bean 发起 HTTP 请求时，
            需要将原本准备传入的 host:port 修改成服务名，例如这里我们传入了 demo-provider。
         */
    }
}

/*
    虽然 /hello02 接口相比 /hello 接口只精简了一行代码，但是它带来的不仅仅是表面所看到的。例如说，如果我们调用服务的一个实例失败时，想要重试另外一个示例，就存在了很大的差异。
    /hello02 接口的方式，可以自动通过 LoadBalancerClient 重新选择一个该服务的实例，再次发起调用。
    /hello 接口的方式，需要自己手动写逻辑，使用 LoadBalancerClient 重新选择一个该服务的实例，后交给 RestTemplate 再发起调用。

    在默认配置下，Ribbon 采用 ZoneAvoidanceRule 负载均衡策略，在未配置所在区域的情况下，和轮询负载均衡策略是相对等价的。
    所以服务消费者 demo-consumer 调用服务提供者 demo-provider 时，顺序将请求分配给每个实例。
 */