package com.yly.cloud.feign.comsumer.feign;

import com.yly.cloud.feign.comsumer.config.DemoProviderFeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "demo-provider", configuration = DemoProviderFeignClientConfiguration.class)
public interface DemoProviderFeignClient {

    @GetMapping("/echo")
    String echo(@RequestParam("name") String name);

}
