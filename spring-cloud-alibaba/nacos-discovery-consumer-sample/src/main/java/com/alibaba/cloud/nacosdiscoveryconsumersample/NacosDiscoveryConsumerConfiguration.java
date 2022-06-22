package com.alibaba.cloud.nacosdiscoveryconsumersample;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients // 激活 @FeignClient
public class NacosDiscoveryConsumerConfiguration {

}