package com.yly.allevery.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = " com.yly.allevery.mybatis.mapper")
public class Application {
}
