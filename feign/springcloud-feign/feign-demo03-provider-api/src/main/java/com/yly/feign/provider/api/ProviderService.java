package com.yly.feign.provider.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Spring Cloud OpenFeign 提供了 SpringMVC 注解的支持，所以我们可以将服务提供者 Controller 提取出一个接口，让服务提供者和消费者共同使用，
 */
public interface ProviderService {

    @GetMapping("/echo")
    String echo(@RequestParam("name") String name);

}
