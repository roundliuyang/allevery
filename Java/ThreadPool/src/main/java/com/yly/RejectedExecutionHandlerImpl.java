package com.yly;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/*
    Executors 类使用 ThreadPoolExecutor 提供了 ExecutorService 的简单实现，但 ThreadPoolExecutor 提供的功能远不止这些
    我们可以在创建ThreadPoolExecutor实例时指定存活的线程数，我们可以限制线程池的大小，并创建我们自己的RejectedExecutionHandler实现，以处理无法容纳在工作者队列中的作业。
    这是我们对 RejectedExecutionHandler 接口的自定义实现。
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }

}