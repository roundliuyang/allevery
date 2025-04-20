package com.yly.redisson;

import com.yly.redisson.service.RedissonService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedissonServiceTest {

    @Resource
    RedissonService redissonService;

    @Test
    void testDelete() {
        this.redissonService.doJobTask();
    }
}
