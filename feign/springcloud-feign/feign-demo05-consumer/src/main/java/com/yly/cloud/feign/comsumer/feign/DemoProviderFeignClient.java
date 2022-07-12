package com.yly.cloud.feign.comsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "demo-provider")
// 将 @FeignClient 注解的 url 属性设置要调用的服务的地址。不过要注意，保持 name 属性和 url 属性的 host 是一致的，不然还是会使用 Ribbon 进行负载均衡。
@FeignClient(name = "iocoder", url = "http://www.iocoder.cn") // 注意，保持 name 属性和 url 属性的 host 是一致的。
public interface DemoProviderFeignClient {

//    @GetMapping("/echo")
//    String echo(@RequestParam("name") String name);

    @GetMapping("/") // 请求首页
    String echo(@RequestParam("name") String name);

}
