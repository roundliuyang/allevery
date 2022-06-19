package com.yly.aop.log.annotation;



import com.yly.aop.log.emums.InterfaceType;

import java.lang.annotation.*;

/**
 * @ClassName Messge
 * @Description: 消息标识 方法加上此注解自动记录日志
 * @Author houchenglong
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
@Documented
public @interface MessageDispose {

    InterfaceType interfaceType();
}
