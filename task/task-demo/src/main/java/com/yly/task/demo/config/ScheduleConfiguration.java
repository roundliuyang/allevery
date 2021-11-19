package com.yly.task.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 创建 ScheduleConfiguration 类，配置 Spring Task
 * 在类上，添加 @EnableScheduling 注解，启动 Spring Task 的定时任务调度的功能
 */
@Configuration
@EnableScheduling
public class ScheduleConfiguration {
}
