package com.yly.utils;



import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mengyuan Xing
 * @Date 2020/11/9
 */


@Configuration
@EnableAsync
public class ThreadConfig {

    private static int corePoolSize = 15;
    private static int maximumPoolSize = 20;
    private static long keepAliveTime = 2000;

    public static ExecutorService createThread(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(12),
                new ThreadPoolExecutor.DiscardPolicy());
        return executor;
    }

}
