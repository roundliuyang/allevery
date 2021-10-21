package com.yly.spring.transaction.example1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan
@Import({JdbcConfig.class})
@PropertySource("classpath:jdbc.properties")
@MapperScan("com.yly.spring.transaction")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)


// 案例 1：unchecked 异常与事务回滚

public class AppConfig {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StudentService studentService = (StudentService) context.getBean("studentService");
        studentService.saveStudent("小明");

        //  执行结果打印出了这样的信息：Exception in thread "main" java.lang.Exception: 该学生已存在
        //  at com.spring.puzzle.others.transaction.example1.StudentService.saveStudent(StudentService.java:23)

        //  可以看到，异常确实被抛出来，但是检查数据库，你会发现数据库里插入了一条新的记录。但是我们的常规思维可能是：
        //  在 Spring 里，抛出异常，就会导致事务回滚，而回滚以后，是不应该有数据存入数据库才对啊。而在这个案例中，异常也抛了，回滚却没有如期而至，这是什么原因呢？我们需要研究一下 Spring 的源码，来找找答案。

        // .........................略
        //  查到这里，真相大白，Spring 处理事务的时候，如果没有在 @Transactional 中配置 rollback 属性，那么只有捕获到 RuntimeException 或者 Error 的时候才会触发回滚操作。
        //  而我们案例抛出的异常是 Exception，又没有指定与之匹配的回滚规则，所以我们不能触发回滚。
    }


    //  问题修正从上述案例解析中，我们了解到，Spring 在处理事务过程中，并不会对 Exception 进行回滚，而会对 RuntimeException 或者 Error 进行回滚。这么看来，修改方法也可以很简单，
    //  只需要把抛出的异常类型改成 RuntimeException 就可以了。于是这部分代码就可以修改如下：  见: StudentService2

    // 再执行一下，这时候异常会正常抛出，数据库里不会有新数据产生，表示这时候 Spring 已经对这个异常进行了处理，并将事务回滚。但是很明显，这种修改方法看起来不够优美，
    // 毕竟我们的异常有时候是固定死不能随意修改的。所以结合前面的案例分析，我们还有一个更好的修改方式。
    // 具体而言，我们在解析 RuleBasedTransactionAttribute.rollbackOn 的代码时提到过 rollbackFor 属性的处理规则。也就是我们在 @Transactional 的 rollbackFor 加入需要支持的异常类型（在这里是 Exception）就可以匹配上我们抛出的异常，
    // 进而在异常抛出时进行回滚。于是我们可以完善下案例中的注解，修改后代码如下：
    // @Transactional(rollbackFor = Exception.class)
    // 再次测试运行，你会发现一切符合预期了。



    // 案例 2：试图给 private 方法添加事务
    // 接着上一个案例，我们已经实现了保存学生信息的功能。接下来，我们来优化一下逻辑，让学生的创建和保存逻辑分离，
    // 于是我就对代码做了一些重构，把 Student 的实例创建和保存逻辑拆到两个方法中分别进行。然后，把事务的注解 @Transactional 加在了保存数据库的方法上。见： StudentService3
    // 执行的时候，继续传入参数“小明”，看看执行结果是什么样子？异常正常抛出，事务却没有回滚。明明是在方法上加上了事务的注解啊，为什么没有生效呢？我们还是从 Spring 源码中找答案。
    // ...........................略
    // 综合上述两个条件，你会发现，只有当注解为事务的方法被声明为 public 的时候，才会被 Spring 处理。


    // 问题修正
    // 了解了问题的根源以后，解决它就变得很简单了，我们只需要把它的修饰符从 private 改成 public 就可以了。
    // 不过需要额外补充的是，我们调用这个加了事务注解的方法，必须是调用被 Spring AOP 代理过的方法，也就是不能通过类的内部调用或者通过 this 的方式调用。
    // 所以我们的案例的 StudentService，它含有一个自动装配（Autowired）了自身（StudentService）的实例来完成代理方法的调用。这个问题我们在之前 Spring AOP 的代码解析中重点强调过，此处就不再详述了。

    //重新运行一下，异常正常抛出，数据库也没有新数据产生，事务生效了，问题解决。
}

// 重点回顾通过以上两个案例，相信你对 Spring 的声明式事务机制已经有了进一步的了解，最后总结下重点：Spring 支持声明式事务机制，
// 它通过在方法上加上 @Transactional，表明该方法需要事务支持。于是，在加载的时候，根据 @Transactional 中的属性，决定对该事务采取什么样
// 的策略；@Transactional 对 private 方法不生效，所以我们应该把需要支持事务的方法声明为 public 类型；Spring 处理事务的时候，
// 默认只对 RuntimeException 和 Error 回滚，不会对 Exception 回滚，如果有特殊需要，需要额外声明，例如指明 Transactional 的属性 rollbackFor 为 Exception.class。


