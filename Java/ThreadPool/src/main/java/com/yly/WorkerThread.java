package com.yly;

/*
    Java 线程池管理工作线程池。它包含一个队列，使任务等待执行。我们可以使用ThreadPoolExecutorJava 来创建线程池。
    Java 线程池管理 Runnable 线程的集合。工作线程从队列中执行 Runnable 线程。java.util.concurrent.Executors为java.util.concurrent.Executor接口提供了工厂和支持方法，用于在java 中创建线程池。
    Executors 是一个实用程序类，它还提供有用的方法来通过各种工厂方法与 ExecutorService、ScheduledExecutorService、ThreadFactory 和 Callable 类一起工作。

    让我们编写一个简单的程序来解释它的工作原理。
    首先，我们需要有一个 Runnable 类，名为 WorkerThread.java
 */
public class WorkerThread implements Runnable {

    private String command;

    public WorkerThread(String s){
        this.command=s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
        processCommand();
        System.out.println(Thread.currentThread().getName()+" End.");
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.command;
    }
}