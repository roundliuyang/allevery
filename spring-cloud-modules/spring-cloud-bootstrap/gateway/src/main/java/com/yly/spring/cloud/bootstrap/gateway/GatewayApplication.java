package com.yly.spring.cloud.bootstrap.gateway;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Configuration
    public class FeignResponseDecoderConfig {
        @Bean
        public Decoder feignDecoder() {

            ObjectFactory<HttpMessageConverters> messageConverters = () -> {
                HttpMessageConverters converters = new HttpMessageConverters();
                return converters;
            };
            return new SpringDecoder(messageConverters);
        }
    }
}
