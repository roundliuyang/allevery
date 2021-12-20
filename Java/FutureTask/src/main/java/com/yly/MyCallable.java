package com.yly;

import java.util.concurrent.Callable;


/*
    前段时间我写了一篇关于Java Callable Future接口的文章，我们可以使用它来获得线程的并发处理优势，以及它们能够向调用程序返回值。
    FutureTask是Future接口的基本具体实现，提供异步处理,它包含启动和取消任务的方法以及可以返回 FutureTask 状态的方法，无论它是完成还是取消。
    我们需要一个可调用的对象来创建未来的任务，然后我们可以使用Java 线程池执行器来异步处理这些。

    让我们用一个简单的程序来看看 FutureTask 的例子。
    由于 FutureTask 需要一个可调用对象，我们将创建一个简单的 Callable 实现。
 */
public class MyCallable implements Callable<String> {

    private long waitTime;

    public MyCallable(int timeInMillis){
        this.waitTime=timeInMillis;
    }
    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }

}