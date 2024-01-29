//package com.yly.uniform.returnformatandexception.advice;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.yly.uniform.returnformatandexception.result.ResultData;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
///**
// * @author jam
// * swagger相当于是寄宿在应用程序中的一个web服务，统一响应处理器拦截了应用所有的响应，对swagger-ui的响应产生了影响。
// * 解决集成Swagger出现404问题，配置统一响应处理器拦截的范围，只拦截本项目的Controller类
// * @date 2021/7/8 10:10 上午
// */
//@Slf4j
//@RestControllerAdvice(basePackages = "com.yly.uniform.returnformatandexception")
//public class ResponseAdvice implements ResponseBodyAdvice<Object> {
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
//        return true;
//    }
//
//    @SneakyThrows
//    @Override
//    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        if(o instanceof String){
//            return objectMapper.writeValueAsString(ResultData.success(o));
//        }
//        if(o instanceof ResultData){
//            return o;
//        }
//        return ResultData.success(o);
//    }
//}