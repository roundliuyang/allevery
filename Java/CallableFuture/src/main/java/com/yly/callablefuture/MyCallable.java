package com.yly.callablefuture;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/*
    Java Callable 和 Future 在多线程编程中被大量使用。在前几篇文章中，我们学到了很多关于Java 线程的知识，但有时我们希望线程可以返回一些我们可以使用的值。
    Java 5在并发包中引入了java.util.concurrent.Callable接口，类似于 Runnable 接口，但它可以返回任何对象并能够抛出异常。

    Callable
    Java Callable 接口使用 Generic 来定义 Object 的返回类型。Executors类提供了在线程池中执行 Java Callable 的有用方法。
    由于可调用的任务是并行运行的,我们必须等待返回的对象。

    Future
    Java Callable 任务返回java.util.concurrent.Future对象。使用Java Future对象，我们可以找出Callable 任务的状态并获取返回的Object。它提供了get()方法，可以等待 Callable 完成然后返回结果。
    Java Future 提供了cancel()方法来取消关联的 Callable 任务。有一个 get() 方法的重载版本，我们可以在其中指定等待结果的时间，这有助于避免当前线程被阻塞更长时间。
    有isDone()和isCancelled()方法可以找出关联的 Callable 任务的当前状态。

    这是 Java Callable 任务的一个简单示例，它在一秒后返回执行任务的线程名称。我们使用Executor 框架并行执行 100 个任务，并使用 Java Future 获取提交任务的结果。
 */
public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }

    public static void main(String args[]){
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<Future<String>>();
        //Create MyCallable instance
        Callable<String> callable = new MyCallable();
        for(int i=0; i< 100; i++){
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for(Future<String> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
    }

}
/*
    一旦我们执行上述程序，您会注意到输出延迟，因为 java Future get() 方法等待 java 可调用任务完成。另请注意，只有 10 个线程执行这些任务。
    这是上述程序的输出片段。
    Mon Dec 31 20:40:15 PST 2012::pool-1-thread-1
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-2
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-3
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-4
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-5
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-6
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-7
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-8
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-9
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-10
    Mon Dec 31 20:40:16 PST 2012::pool-1-thread-2
    ...
    提示：如果我们想覆盖 Java Future 接口的一些方法，例如覆盖get()方法在默认时间后超时而不是无限期等待，
    在这种情况下，Java FutureTask类就派上用场了，它是 Future 接口的基本实现。查看Java FutureTask 示例以了解有关此类的更多信息。
 */