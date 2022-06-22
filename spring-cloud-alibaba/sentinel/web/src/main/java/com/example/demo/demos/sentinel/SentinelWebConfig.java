package com.example.demo.demos.sentinel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;

import java.io.PrintWriter;

@Configuration
public class SentinelWebConfig {

    @Bean
    public BlockExceptionHandler sentinelBlockExceptionHandler() {
        return (request, response, e) -> {
            // 429 Too Many Requests
            response.setStatus(429);

            PrintWriter out = response.getWriter();
            out.print("Oops, blocked by Sentinel: " + e.getClass().getSimpleName());
            out.flush();
            out.close();
        };
    }
}