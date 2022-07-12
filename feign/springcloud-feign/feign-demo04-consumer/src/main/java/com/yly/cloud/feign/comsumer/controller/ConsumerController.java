package com.yly.cloud.feign.comsumer.controller;


import com.yly.cloud.feign.comsumer.dto.DemoDTO;
import com.yly.cloud.feign.comsumer.feign.DemoProviderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 在「2. 快速入门」小节中，考虑到简单上手 Spring Cloud OpenFeign 的使用，我们只提供了 Feign 请求单个参数的简单参数例子。但是实际项目中，
 * 我们必然会面临传递多个参数的复杂参数的场景。例如说：
 *   GET /demo/?param1=value1&param2=value2
 *   POST /demo {
 *         param1: value1,
 *         param2: value2
 *   }
 * 针对 GET 和 POST 类型的请求，Spring Cloud OpenFeign 传递复杂参数有不同的处理方式。下面，让我们来搭建下复杂参数的示例。
 */
@RestController
public class ConsumerController {

    @Autowired
    private DemoProviderFeignClient demoProviderFeignClient;

    @GetMapping("/hello02")
    public String hello02(String name) {
        // 使用 Feign 调用接口
        String response = demoProviderFeignClient.echo(name);
        // 返回结果
        return "consumer:" + response;
    }

    @GetMapping("/test_get_demo")
    public DemoDTO testGetDemo(@RequestParam("type") int type, DemoDTO demoDTO) {
        // 方式一
        if (type == 1) {
            return demoProviderFeignClient.getDemo(demoDTO);
        } else if (type == 2) {
            return demoProviderFeignClient.getDemo(demoDTO.getUsername(), demoDTO.getPassword());
        } else {
            // 方式三
            Map<String, Object> params = new HashMap<>();
            params.put("username", demoDTO.getUsername());
            params.put("password", demoDTO.getPassword());
            return demoProviderFeignClient.getDemo(params);
        }
    }

    @GetMapping("/test_post_demo")
    public DemoDTO testPostDemo(DemoDTO demoDTO) {
       return demoProviderFeignClient.postDemo(demoDTO);
    }

}
