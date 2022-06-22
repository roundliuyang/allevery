package com.example.demo.demos.sentinelprovider;

import com.alibaba.cloud.demo.sentinel.api.FooService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author Eric Zhao
 */
@Service
public class FooServiceImpl implements FooService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }

    @Override
    public long getCurrentTime(boolean slow) {
        // Simulate slow invocations randomly.
        if (slow) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        return System.currentTimeMillis();
    }
}
