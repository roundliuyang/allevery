package com.yly.sentineldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 在 <X> 处，设置系统属性 project.name，提供给 Sentinel 读取。比较特殊，该配置项无法在 csp.sentinel.dashboard.server 配置文件中设置。
        // <X> 设置系统属性 project.name，提供给 Sentinel 读取
        System.setProperty("project.name", "demo-application");

        // 启动 Spring Boot 应用
        SpringApplication.run(Application.class, args);
    }

}
