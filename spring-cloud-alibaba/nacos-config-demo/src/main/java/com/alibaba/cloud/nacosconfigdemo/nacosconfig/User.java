package com.alibaba.cloud.nacosconfigdemo.nacosconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/*
    使用 Nacos Config 实现 @ConfigurationPropertiesBean 属性动态刷新
 */
@RefreshScope
@ConfigurationProperties(prefix = "user")
public class User {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}