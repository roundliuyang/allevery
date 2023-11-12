package com.yly.server.oss.sdk.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author by yangTao
 * @Classname OssProperties
 * @Description 参数属性
 * @Date 2021/7/5 14:25
 */

@ConfigurationProperties(prefix = OssProperties.OSS_CONFIG)
public class OssProperties {

    public static final String OSS_CONFIG = "yly.sdk.oss";
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

    public static String getOssConfig() {
        return OSS_CONFIG;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketDomain() {
        return bucketDomain;
    }

    public void setBucketDomain(String bucketDomain) {
        this.bucketDomain = bucketDomain;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFileRootDir() {
        return fileRootDir;
    }

    public void setFileRootDir(String fileRootDir) {
        this.fileRootDir = fileRootDir;
    }

    public String getLicenceDir() {
        return licenceDir;
    }

    public void setLicenceDir(String licenceDir) {
        this.licenceDir = licenceDir;
    }

    public String getEndpointOut() {
        return endpointOut;
    }

    public void setEndpointOut(String endpointOut) {
        this.endpointOut = endpointOut;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}