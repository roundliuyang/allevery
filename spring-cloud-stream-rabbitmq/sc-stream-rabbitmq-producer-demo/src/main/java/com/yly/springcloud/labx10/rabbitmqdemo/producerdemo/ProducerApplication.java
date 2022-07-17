package com.yly.springcloud.labx10.rabbitmqdemo.producerdemo;


import com.yly.springcloud.labx10.rabbitmqdemo.producerdemo.message.MySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
// 使用 @EnableBinding 注解，声明指定接口开启 Binding 功能，扫描其 @Input 和 @Output 注解。这里，我们设置为 MySource 接口。
@EnableBinding(MySource.class)
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}
