package com.yly.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author houchenglong
 * @date 2021-05-12 12:13
 */
@Configuration
public class RedissonAutoConfiguration {
    @Value("${redisson.address}")
    private String addressUrl;
    @Value("${redisson.password}")
    private String password;
    @Value("${redisson.database}")
    private Integer database;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(addressUrl)
                .setPassword(password)
                .setConnectTimeout(10000)
                .setRetryInterval(5000)
                .setTimeout(10000)
                .setDatabase(database);
        return Redisson.create(config);
    }
}
