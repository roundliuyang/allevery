package com.yly.springcloud.config.userapplication.controller;


import com.yly.springcloud.config.userapplication.config.OrderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private OrderProperties orderProperties;

    /**
     * 测试 @ConfigurationProperties 注解的配置属性类
     */
    @GetMapping("/test01")
    public OrderProperties test01() {
        return orderProperties;
    }

    @Value(value = "${order.pay-timeout-seconds}")
    private Integer payTimeoutSeconds;
    @Value(value = "${order.create-frequency-seconds}")
    private Integer createFrequencySeconds;

    /**
     * 测试 @Value 注解的属性
     */
    @GetMapping("/test02")
    public Map<String, Object> test02() {
        Map<String, Object> result = new HashMap<>();
        result.put("payTimeoutSeconds", payTimeoutSeconds);
        result.put("createFrequencySeconds", createFrequencySeconds);
        return result;
    }

    @Value(value = "${xx-password:''}")
    private String xxPassword;

    @GetMapping("/xx_password")
    public String xxPassword() {
        return xxPassword;
    }

    @Value(value = "${yy-password:''}")
    private String yyPassword;

    @GetMapping("/yy_password")
    public String yyPassword() {
        return yyPassword;
    }

}
