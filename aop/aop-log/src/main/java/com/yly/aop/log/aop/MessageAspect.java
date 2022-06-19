package com.yly.aop.log.aop;


import com.yly.aop.log.annotation.MessageDispose;
import com.yly.aop.log.pojo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 **/
@Aspect
@Component
@Slf4j
@Order(999)
public class MessageAspect {


    @Around("@annotation(messageDispose)")
    public void Translation(final ProceedingJoinPoint joinPoint, MessageDispose messageDispose) {
        Object[] args = joinPoint.getArgs();

        try {
            long startTimeMillis = System.currentTimeMillis();
            ResponseResult result = (ResponseResult) joinPoint.proceed();
            long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
            StringBuffer sb = new StringBuffer();
            sb.append("接口<"+messageDispose.interfaceType().getType()+">的")
                    .append("调用时长为:"+execTimeMillis+"毫秒");
//                    .append(",请求参数为:"+ JSON.toJSONString(args))
//                    .append(",响应结果为:"+JSON.toJSONString(result));
            log.info(sb.toString());
        } catch (Throwable te) {
            log.error(te.getMessage(), te);
            throw new RuntimeException(te.getMessage());
        }
    }
}
