package com.yly.config;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Properties;

/**
 * RocketMQ属性配置类
 * @author xuzhipeng
 * @date 2021/3/19
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties implements Serializable {
    private static final long serialVersionUID = -414105115056148944L;

    /**
     * 查询延时时间
     */
    private final long[] QUERY_DELAY_TIME = {0, 2000, 2000, 2000, 2000, 2000, 2000};

    /**
     * 通知延时时间
     */
    private final long[] NOTIFY_DELAY_TIME = {0, 10000, 30000, 60000, 600000, 1800000, 3600000};

    /**
     * 队列最大次数
     */
    private final int MAX_TIMES = 7;

    /**
     * AccessKey, 用于标识、校验用户身份
     */
    private String accessKey;

    /**
     * SecretKey, 用于标识、校验用户身份
     */
    private String secretKey;

    /**
     * Name Server地址
     */
    private String nameSrvAddr;

    /**
     * topic
     */
    private String topic;

    private String testMqGroupId;

    
    private String testMqTag;

    private String remitGroupId;
    private String spdbRemitGroupId;

    private String remitTag;
    private String remitSpdbTag;

    /**
     * 获取rocketMQ基本配置信息
     * @return Properties
     */
    public Properties getMQProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        return properties;
    }
}
