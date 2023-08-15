package com.yly.sentineldemo.web;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = "com.yly.sentineldemo.controller")
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BlockException.class)
    public String blockExceptionHandler(BlockException blockException) {
        return "请求过于频繁";
    }

    @ResponseBody
    @ExceptionHandler(value = DegradeException.class)
    public String blockExceptionHandler(DegradeException degradeException) {
        return "请求被降级了";
    }

    @ResponseBody
    @ExceptionHandler(value = FlowException.class)
    public String blockExceptionHandler(FlowException flowException) {
        return "请求被流控了";
    }

    @ResponseBody
    @ExceptionHandler(value = ParamFlowException.class)
    public String blockExceptionHandler(ParamFlowException paramFlowException) {
        return "热点参数限流了";
    }

    @ResponseBody
    @ExceptionHandler(value = SystemBlockException.class)
    public String blockExceptionHandler(SystemBlockException systemBlockException) {
        return "请求被系统规则限流了";
    }

    @ResponseBody
    @ExceptionHandler(value = AuthorityException.class)
    public String blockExceptionHandler(AuthorityException authorityException) {
        return "请求被黑白名单控制了";
    }
    /*
    在 #blockExceptionHandler(...) 方法中，我们处理 BlockException 异常。因为这里是示例，所以处理的比较简单。胖友可以看看《芋道 Spring Boot SpringMVC 入门》的「5. 全局异常处理」小节。
     */

}
