package com.yly.custom;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.*;


public class CustomTaskThreadPool implements CustomThreadPool {
    /**
     * 核心大小
     */
    private static int CORE_POOLSIZE = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 上限
     */
    private static int MAX_POOLSIZE = CORE_POOLSIZE * 10;

    //留存时间ms
    private static long KEEP_ALIVETIME = 300000L;

    private static long SHUTDOWN_WAIT_TIME = 300000L;

    /**
     * 时间单位
     */
    private static TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /**
     * 队列容量
     */
    private static int CAPACITY = 20;

    /**
     * 队列
     */
    private static BlockingQueue<Runnable> WORK_QUEUE;

    /**
     * 线程工厂
     */
    private static ThreadFactory THREAD_FACTORY;

    /**
     * 默认拒绝策略
     */
    private static RejectedExecutionHandler REJECTED_EXECUTION_HANDLER;


    private static ThreadPoolExecutor executor = null;

    private static class CustomTaskThreadPoolFactory {
        public static CustomTaskThreadPool CustomTaskThreadPool = new CustomTaskThreadPool();
    }

    public CustomTaskThreadPool() {
        if (CORE_POOLSIZE <= 0 || CORE_POOLSIZE > 10) {
            CORE_POOLSIZE = 4;
            MAX_POOLSIZE = CORE_POOLSIZE * 10;
        }

        THREAD_FACTORY = new CustomThreadFactory();
        WORK_QUEUE = new ArrayBlockingQueue<>(CAPACITY);       REJECTED_EXECUTION_HANDLER = new AbortPolicy();

        executor = new ThreadPoolExecutor(
                CORE_POOLSIZE,
                MAX_POOLSIZE,
                KEEP_ALIVETIME,
                UNIT,
                WORK_QUEUE,
                THREAD_FACTORY,
                REJECTED_EXECUTION_HANDLER
        );
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new CustomRunnable() {
            @Override
            public void run() {
                close();
            }
        }));
    }

    private static void close() {
        try {
//            log.warn("即将关闭线程池，等待:{}ms,活跃:{},未完成:{},最大完成:{},总完成:{}",
//                    SHUTDOWN_WAIT_TIME,
//                    executor.getActiveCount(),
//                    executor.getQueue().size(),
//                    executor.getLargestPoolSize(),
//                    executor.getCompletedTaskCount());
            executor.shutdown();

            //等待关闭
            executor.awaitTermination(SHUTDOWN_WAIT_TIME, UNIT);

//            log.warn("已关闭线程池");

            //未完成
            List<Runnable> CustomRunnableList = executor.shutdownNow();
            if (!CollectionUtils.isEmpty(CustomRunnableList)) {
//                CustomRunnableList.forEach(e -> log.warn("关闭未完成任务:" + e.toString()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(Runnable runnable) {
        try {
            executor.execute(new CustomRunnable(runnable));
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public <T> Future<T> submit(Callable<T> tCallable) {
        try {
            return executor.submit(new CustomCallable<T>(tCallable));
        } catch (RejectedExecutionException e) {
//            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void shutDown() {
        executor.shutdown();
    }
    /**
     * 拒绝策略，丢弃不作任何处理
     */
    public static class AbortPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {

            String info = "";

            try {
                Field field = FutureTask.class.getDeclaredField("callable");
                field.setAccessible(true);

                FutureTask futureTask = (FutureTask) r;
                Callable callable = (Callable) field.get(futureTask);
                CustomCallable CustomCallable = (CustomCallable) ((CustomCallable) callable).callable;
                info = CustomCallable.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

//            log.error("AbortPolicy: " + info + " rejected from " + e.toString());
        }
    }
}
