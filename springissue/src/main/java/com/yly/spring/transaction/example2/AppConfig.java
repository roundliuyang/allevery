package com.yly.spring.transaction.example2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.yly.spring.transaction.example2")
@Import({JdbcConfig.class})
@PropertySource("classpath:jdbc.properties")
@MapperScan("com.yly.spring.transaction.example2")
@EnableTransactionManagement(order = 2, proxyTargetClass = true)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)


// 案例 1：嵌套事务回滚错误

//  上一节课我们完成了学生注册功能，假设我们需要对这个功能继续进行扩展，当学生注册
//  完成后，需要给这个学生登记一门英语必修课，并更新这门课的登记学生数。为此，我添
//  加了两个表  课程表 course  学生选课表 student_course   同时我为课程表初始化了一条课程信息，id = 1，course_name = "英语"，number = 0


public class AppConfig {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StudentService studentService = (StudentService) context.getBean("studentService");
        studentService.saveStudent("小明");
    }
    // 运行一下这段代码，在控制台里我们看到了以下提示信息：
    // java.lang.Exception: 注册失败  ....

    // 其中，注册失败部分的异常符合预期，但是后面又多了一个这样的错误提示：Transaction rolled back because it has been marked as rollback-only
    // 最后的结果是，学生和选课的信息都被回滚了，显然这并不符合我们的预期。我们期待的结果是即便内部事务 regCourse() 发生异常，外部事务 saveStudent() 俘获该异常后，内
    //部事务应自行回滚，不影响外部事务。那么这是什么原因造成的呢？我们需要研究一下Spring 的源码，来找找答案

//     案例解析
//    在做进一步的解析之前，我们可以先通过伪代码把整个事务的结构梳理一下：
//     外层事务
//        @Transactional(rollbackFor = Exception.class)
//        public void saveStudent(String realname) throws Exception {
//            //......省略逻辑代码.....
//            studentService.doSaveStudent(student);
//            try {
//                // 嵌套的内层事务
//                @Transactional(rollbackFor = Exception.class)
//                public void regCourse(int studentId) throws Exception {
//                    //......省略逻辑代码.....
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    // 可以看出来，整个业务是包含了 2 层事务，外层的 saveStudent() 的事务和内层的regCourse() 事务。
    // 在 Spring 声明式的事务处理中，有一个属性 propagation，表示打算对这些方法怎么使
    // 用事务，即一个带事务的方法调用了另一个带事务的方法，被调用的方法它怎么处理自己
    // 事务和调用方法事务之间的关系。
    // 其中 propagation 有 7 种配置：REQUIRED、SUPPORTS、MANDATORY、
    // REQUIRES_NEW、NOT_SUPPORTED、NEVER、NESTED。默认是 REQUIRED，它的含
    // 义是：如果本来有事务，则加入该事务，如果没有事务，则创建新的事务。
    // 结合我们的伪代码示例，因为在 saveStudent() 上声明了一个外部的事务，就已经存在一
    // 个事务了，在 propagation 值为默认的 REQUIRED 的情况下， regCourse() 就会加入到
    // 已有的事务中，两个方法共用一个事务


    // 至此，答案基本浮出水面了，我们把整个逻辑串在一起就是：外层事务是否回滚的关键，
    //最终取决于 DataSourceTransactionObject 类中的 isRollbackOnly()，而该方法的返回值，正是我们在内层异常的时候设置的。
    //所以最终外层事务也被回滚了，从而在控制台中打印出异常信息："Transaction rolled
    //back because it has been marked as rollback-only"。

    // 所以到这里，问题也就清楚了，Spring 默认的事务传播属性为 REQUIRED，如我们之前介
    //绍的，它的含义是：如果本来有事务，则加入该事务，如果没有事务，则创建新的事务，
    //因而内外两层事务都处于同一个事务中。所以，当我们在 regCourse() 中抛出异常，并触
    //发了回滚操作时，这个回滚会进一步传播，从而把 saveStudent() 也回滚了。最终导致整个事务都被回滚了。



    //问题修正
    //从上述案例解析中，我们了解到，Spring 在处理事务过程中，有个默认的传播属性REQUIRED，在整个事务的调用链上，任何一个环节抛出的异常都会导致全局回滚。
    //知道了这个结论，修改方法也就很简单了，我们只需要对传播属性进行修改，把类型改成REQUIRES_NEW 就可以了。于是这部分代码就修改成这样：

//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIR
//            public void regCourse(int studentId) throws Exception {
//        studentCourseMapper.saveStudentCourse(studentId, 1);
//        courseMapper.addCourseNumber(1);
//        throw new Exception("注册失败");
//    }

    //运行一下看看
    // 异常正常抛出，注册课程部分的数据没有保存，但是学生还是正常注册成功。这意味着此时 Spring 只对注册课程这部分的数据进行了回滚，并没有传播到上一级。

    // 这里我简单解释下这个过程：
    // 当子事务声明为 Propagation.REQUIRES_NEW 时，在TransactionAspectSupport.invokeWithinTransaction() 中调用
    //createTransactionIfNecessary() 就会创建一个新的事务，独立于外层事务。而在 AbstractPlatformTransactionManager.processRollback() 进行 rollback 处理
    //时，因为 status.isNewTransaction() 会因为它处于一个新的事务中而返回 true，所以它走入到了另一个分支，执行了 doRollback() 操作，让这个子事务单独回滚，不会影响到主事务。






}
