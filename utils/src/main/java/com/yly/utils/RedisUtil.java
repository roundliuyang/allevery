package com.yly.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author xuzhipeng
 * @date 2021/01/31
 */
@Component
public class RedisUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

//    @Resource
    public RedisTemplate<String, Object> redisTemplate;

//    @Resource
    public StringRedisTemplate stringRedisTemplate;

    /**
     * 添加一个key，无过期时间
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        log.info("[redis.set:({},{})]", key, value);
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 添加value为字符串的数据
     * @param key
     * @param value
     */
    public void setString(String key, String value){
        log.info("[redis.setString:({},{})]", key, value);
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取value为字符串的数据
     * @param key
     * @return
     */
    public String getString(String key) {
        log.info("[redis.getString:({})]", key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 添加一个key，并设置过期时间
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        log.info("[redis.set:({},{})-({} {})]", key, value, time, timeUnit);
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * get redis value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        log.info("[redis.get:({})]", key);
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置key的过期时间
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expire(String key, long time, TimeUnit timeUnit) {
        log.info("[redis.expire:({})-({} {})]", key, time, timeUnit);
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void delete(String key) {
        log.info("[redis.delete:({})]", key);
        redisTemplate.delete(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        log.info("[redis.hasKey:({})]", key);
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置hash
     */
    public void setHash(String key, Map<String, Object> mp) {
        redisTemplate.opsForHash().putAll(key, mp);
    }

    /**
     * 设置hash
     */
    public void setHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 判断hash中是否存在某个key
     */
    public boolean hasHash(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取hash
     */
    public Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取hash某个key的值
     */
    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除hash中的某个key
     */
    public Object deleteHash(String key, String hashKey) {
        return redisTemplate.opsForHash().delete(key, hashKey);
    }
}

