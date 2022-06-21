package com.alibaba.cloud.nacosconfigdemo.nacosconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/*
    使用 Nacos Config 实现 Bean 动态刷新
    Nacos Confg 支持标准 Spring Cloud @RefreshScope特性，即应用订阅某个 Nacos 配置后，当配置内容变化时，Refresh Scope Beans 中的绑定配置的属性将有条件的更新。所谓的条件是指 Bean 必须:

    必须条件：Bean 的声明类必须标注 @RefreshScope
    二选一条件：
        --属性（非 static 字段）标注 @Value
        - @ConfigurationPropertiesBean

    除此之外，Nacos Confg 也引入了 Nacos Client 底层数据变化监听接口，即 com.alibaba.nacos.api.config.listener.Listener。下面的内容将分别讨论这三种不同的使用场景。
    使用 Nacos Config 实现 Bean @Value属性动态刷新
    使用 Nacos Config 实现 @ConfigurationPropertiesBean 属性动态刷新
    使用 Nacos Config 监听实现 Bean 属性动态刷新
 */

// 使用 Nacos Config 实现 Bean @Value属性动态刷新,基于应用 nacos-config-sample 修改，将引导类 NacosConfigDemo标注@RefreshScope和
// @RestController，使得该类变为 Spring MVC REST 控制器，同时具备动态刷新能力，
@RestController
@RefreshScope
public class NacosConfigDemo1 {

    @Value("${user.name}")
    private String userName;

    @Value("${user.age}")
    private int userAge;

    @PostConstruct
    public void init() {
        System.out.printf("[init] user name : %s , age : %d%n", userName, userAge);
    }

    @RequestMapping("/user")
    public String user() {
        return String.format("[HTTP] user name : %s , age : %d", userName, userAge);
    }

}