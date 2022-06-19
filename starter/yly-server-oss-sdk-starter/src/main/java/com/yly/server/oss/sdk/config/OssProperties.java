package com.yly.server.oss.sdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author by yangTao
 * @Classname OssProperties
 * @Description 参数属性
 * @Date 2021/7/5 14:25
 */
@Data
@ConfigurationProperties(prefix = OssProperties.OSS_CONFIG)
public class OssProperties {

    public static final String OSS_CONFIG = "nucarf.sdk.oss";
    private String type;
    private String endpoint;
    private String bucketDomain;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
    private String fileRootDir;
    private String licenceDir;
    private String endpointOut;
    private String region;
    private Integer expiration;
}