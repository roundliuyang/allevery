package com.yly.server.oss.sdk.config;


import com.yly.server.oss.sdk.util.AliOssClientUtil;
import com.yly.server.oss.sdk.util.OssClientUtil;
import com.yly.server.oss.sdk.util.QiNiuOssClientUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by yangTao
 * @Classname OssSdkAutoConfiguration
 * @Description 自动配置类
 * @Date 2021/7/6 9:45
 */
@Configuration
@ConditionalOnClass(OssProperties.class)
@EnableConfigurationProperties(OssProperties.class)
public class OssSdkAutoConfiguration {
    private static final String A_LI_YUN = "aliyun";
    private static final String QI_NIU_YUN = "qiniuyun";

    @Bean
    @ConditionalOnMissingBean(name = "ossClientUtil")
    public OssClientUtil aliOssClientUtil(OssProperties ossProperties) {
        if (A_LI_YUN.equals(ossProperties.getType())) {
            AliOssClientUtil instance = AliOssClientUtil.getInstance();
            instance.setOssProperties(ossProperties);
            return instance;
        } else if (QI_NIU_YUN.equals(ossProperties.getType())) {
            QiNiuOssClientUtil instance = QiNiuOssClientUtil.getInstance();
            instance.setOssProperties(ossProperties);
            return instance;
        }
        return null;
    }

}