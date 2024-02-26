package com.yly;



import java.util.concurrent.*;

public class RunnableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100));
        // 遍历所有中心，为每一个centerId提交一条任务到线程池
        Future<?> submit = executorService.submit(new MyRunnable());
        System.out.println(submit.get());
    }
}