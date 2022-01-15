package com.yly.sentineldemo.web;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = "cn.iocoder.springboot.lab46.sentineldemo.controller")
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BlockException.class)
    public String blockExceptionHandler(BlockException blockException) {
        return "请求过于频繁";
    }

    /*
    在 #blockExceptionHandler(...) 方法中，我们处理 BlockException 异常。因为这里是示例，所以处理的比较简单。胖友可以看看《芋道 Spring Boot SpringMVC 入门》的「5. 全局异常处理」小节。
     */

}
