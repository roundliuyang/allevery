package com.yly;

import java.util.concurrent.*;
/*
    下面是使用 ThreadPoolExecutor 的线程池实现示例
 */
public class WorkerPool {

    public static void main(String args[]) throws InterruptedException{
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
        //start the monitoring thread
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        //submit work to the thread pool
        for(int i=0; i<10; i++){
            executorPool.execute(new WorkerThread("cmd"+i));
        }

        Thread.sleep(30000);
        //shut down the pool
        executorPool.shutdown();
        //shut down the monitor thread
        Thread.sleep(5000);
        monitor.shutdown();

    }
}
/*
    请注意，在初始化 ThreadPoolExecutor 时，我们保持初始池大小为 2，最大池大小为 4，工作队列大小为 2。因此，如果有 4 个正在运行的任务并且提交了更多任务，则工作队列将只保留其中的 2 个其余的将由RejectedExecutionHandlerImpl.
    这是上述程序的输出，确认了上述语句。

    pool-1-thread-1 Start. Command = cmd0
    pool-1-thread-4 Start. Command = cmd5
    cmd6 is rejected
    pool-1-thread-3 Start. Command = cmd4
    pool-1-thread-2 Start. Command = cmd1
    cmd7 is rejected
    cmd8 is rejected
    cmd9 is rejected
    [monitor] [0/2] Active: 4, Completed: 0, Task: 6, isShutdown: false, isTerminated: false
    [monitor] [4/2] Active: 4, Completed: 0, Task: 6, isShutdown: false, isTerminated: false
    pool-1-thread-4 End.
    pool-1-thread-1 End.
    pool-1-thread-2 End.
    pool-1-thread-3 End.
    pool-1-thread-1 Start. Command = cmd3
    pool-1-thread-4 Start. Command = cmd2
    [monitor] [4/2] Active: 2, Completed: 4, Task: 6, isShutdown: false, isTerminated: false
    [monitor] [4/2] Active: 2, Completed: 4, Task: 6, isShutdown: false, isTerminated: false
    pool-1-thread-1 End.
    pool-1-thread-4 End.
    [monitor] [4/2] Active: 0, Completed: 6, Task: 6, isShutdown: false, isTerminated: false
    [monitor] [2/2] Active: 0, Completed: 6, Task: 6, isShutdown: false, isTerminated: false
    [monitor] [2/2] Active: 0, Completed: 6, Task: 6, isShutdown: false, isTerminated: false
    [monitor] [2/2] Active: 0, Completed: 6, Task: 6, isShutdown: false, isTerminated: false
    [monitor] [2/2] Active: 0, Completed: 6, Task: 6, isShutdown: false, isTerminated: false
    [monitor] [2/2] Active: 0, Completed: 6, Task: 6, isShutdown: false, isTerminated: false
    [monitor] [0/2] Active: 0, Completed: 6, Task: 6, isShutdown: true, isTerminated: true
    [monitor] [0/2] Active: 0, Completed: 6, Task: 6, isShutdown: true, isTerminated: true

    请注意执行程序的活动、已完成和已完成任务总数的变化。我们可以调用shutdown()方法来完成所有提交的任务的执行并终止线程池
    如果要安排任务延迟或定期运行，则可以使用ScheduledThreadPoolExecutor类。在Java Schedule Thread Pool Executor阅读更多关于它们的信息。
 */