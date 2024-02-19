//package com.yly.jedis.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * @author LiSong-ux
// * @create 2021-06-10 18:13
// */
//@Configuration
//@EnableRedisHttpSession
//public class RedisConfig2 {
//
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.database:0}")
//    private int database;
//    @Bean
//    public RedisTemplate<String, Object> sessionRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> sessionRedisTemplate = new RedisTemplate<>();
//        sessionRedisTemplate.setConnectionFactory(redisConnectionFactory);
//        //JdkSerializationRedisSerializer jdkRedisSerializer = new JdkSerializationRedisSerializer();
//        RedisSerializer<String> keySerializer = new StringRedisSerializer();
//        RedisSerializer<Object> valueSerializer = new JdkSerializationRedisSerializer(
//                this.getClass().getClassLoader());
//        // 值采用json序列化
//        sessionRedisTemplate.setValueSerializer(valueSerializer);
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        sessionRedisTemplate.setKeySerializer(keySerializer);
//        // 设置hash key 和value序列化模式
//        sessionRedisTemplate.setHashKeySerializer(keySerializer);
//        sessionRedisTemplate.setHashValueSerializer(valueSerializer);
//        sessionRedisTemplate.afterPropertiesSet();
//        return sessionRedisTemplate;
//    }
//
//    @Bean
//    RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//        redisStandaloneConfiguration.setDatabase(database);
//
//        // 连接池配置
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setTestOnBorrow(true);
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisClientConfigurationBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        jedisClientConfigurationBuilder.poolConfig(poolConfig);
//        JedisClientConfiguration jedisClientConfiguration = jedisClientConfigurationBuilder.build();
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
//    }
//
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
//        StringRedisTemplate redisTemplate = new StringRedisTemplate();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        return redisTemplate;
//    }
//}
