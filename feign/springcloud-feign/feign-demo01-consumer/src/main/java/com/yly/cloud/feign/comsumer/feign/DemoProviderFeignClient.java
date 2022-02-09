package com.yly.cloud.feign.comsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @FeignClient 注解，声明 Feign 客户端。其中 name 属性，为 Feign 客户端的名字，也为 Ribbon 客户端的名字，也为注册中心的服务的名字。
 */
@FeignClient(name = "demo-provider")
public interface DemoProviderFeignClient {

    @GetMapping("/echo")
    String echo(@RequestParam("name") String name);

}
