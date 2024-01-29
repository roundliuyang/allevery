package com.yly.uniform.all.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManualInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle()方法");
        MethodParameter[] methodParameters = ((HandlerMethod) handler).getMethodParameters();
        for (MethodParameter methodParameter : methodParameters) {
            RequestAttribute requestAttribute = methodParameter.getParameterAnnotation(RequestAttribute.class);
            if (requestAttribute != null && requestAttribute.name().equals("name")) {
                request.setAttribute("name", "test");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("执行了postHandle()方法");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion()方法");
        super.afterCompletion(request, response, handler, ex);
    }
}
