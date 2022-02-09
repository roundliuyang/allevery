package com.yly.cloud.feign.comsumer.feign;


import com.yly.feign.provider.api.ProviderService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "demo-provider")
public interface DemoProviderFeignClient extends ProviderService {

//    @GetMapping("/echo")
//    String echo(@RequestParam("name") String name);

}
