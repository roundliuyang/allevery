package com.yly.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * redis lua工具类
 * @author xuzhipeng
 * @date 2021/01/31
 */
@Component
public class RedisLuaUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisLuaUtil.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String runLuaScript(LuaFileEnum luaFileEnum, List<String> keyList) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(String.format("lua/%s.lua",luaFileEnum.getName()))));
        redisScript.setResultType(String.class);

        String argsone = "none";
        String result = "";
        try {
            result = stringRedisTemplate.execute(redisScript,keyList,argsone);
        } catch (Exception e) {
            log.error("发号器RedisLuaUtil错误:{}",e);
        }

        return result;
    }
}

