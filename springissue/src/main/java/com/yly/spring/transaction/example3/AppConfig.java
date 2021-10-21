package com.yly.spring.transaction.example3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.yly.spring.transaction.example3")
@Import({JdbcConfig.class})
@PropertySource("classpath:jdbc.properties")
@MapperScan("com.yly.spring.transaction.example3")
@EnableTransactionManagement(order = 2, proxyTargetClass = true)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)


// 案例 2：多数据源间切换之谜

//  在前面的案例中，我们完成了学生注册功能和课程注册功能。假设新需求又来了，每个学
//  生注册的时候，需要给他们发一张校园卡，并给校园卡里充入 50 元钱。但是这个校园卡管
//  理系统是一个第三方系统，使用的是另一套数据库，这样我们就需要在一个事务中同时操作两个数据库。
//


public class AppConfig {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StudentService studentService = (StudentService) context.getBean("studentService");
        studentService.saveStudent("小明");
    }
}

// 案例解析
// 这是一个相对常见的需求，学生注册和发卡都要在一个事务里完成，但是我们都默认只会
// 连一个数据源，之前我们一直连的都是学生信息这个数据源，在这里，我们还需要对校园
// 卡的数据源进行操作。于是，我们需要在一个事务里完成对两个数据源的操作，该如何实
// 现这样的功能呢

// 以后看吧！


// 重点回顾
// 通过以上两个案例，相信你对 Spring 的事务机制已经有了深刻的认识，最后总结下重点：
// Spring 在事务处理中有一个很重要的属性 Propagation，主要用来配置当前需要执行的方法如何使用事务，以及与其它事务之间的关系。
// Spring 默认的传播属性是 REQUIRED，在有事务状态下执行，如果当前没有事务，则创建新的事务；
// Spring 事务是可以对多个数据源生效，它提供了一个抽象类AbstractRoutingDataSource，通过实现这个抽象类，我们可以实现自定义的数据库切换.

