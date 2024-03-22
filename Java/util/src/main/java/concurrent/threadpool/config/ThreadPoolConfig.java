package concurrent.threadpool.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@PropertySource("classpath:threadpool-config.yml")
public class ThreadPoolConfig {

    /* Message Queue Thread Pool */
    /* start */
    @Value("${task.execution.pool.message-queue.core-size}")
    private int mqCorePoolSize;
    @Value("${task.execution.pool.message-queue.max-size}")
    private int mqMaxPoolSize;
    @Value("${task.execution.pool.message-queue.queue-capacity}")
    private int mqQueueCapacity;
    @Value("${task.execution.pool.message-queue.keep-alive}")
    private int mqKeepAliveSeconds;
    @Value("${task.execution.pool.message-queue.allow-core-thread-timeout}")
    private boolean mqAllowCoreThreadTimeOut;
    @Value("${task.execution.pool.message-queue.name}")
    private String mqThreadName;

    @Bean("messageQueueExecutor")
    public Executor messageQueueExecutor() {
        return threadPoolTaskExecutor(mqMaxPoolSize, mqMaxPoolSize, mqQueueCapacity, mqKeepAliveSeconds, mqAllowCoreThreadTimeOut, mqThreadName);
    }
    /* end */

    private ThreadPoolTaskExecutor threadPoolTaskExecutor(int corePoolSize, int maxPoolSize,
                                                          int queueCapacity, int keepAliveSeconds,
                                                          boolean allowCoreThreadTimeOut, String threadName) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        threadPoolTaskExecutor.setThreadNamePrefix(threadName);
        threadPoolTaskExecutor.initialize();
        threadPoolTaskExecutor.setRejectedExecutionHandler((runnable, executor) -> {
            executor.execute(runnable);
        });
        return threadPoolTaskExecutor;
    }

}