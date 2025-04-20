package com.yly.spring.cloud.bootstrap.gateway.client.rating;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// todo  注册中心 ，配置中心，序列化等等
@FeignClient(value = "rating-service",url ="http://127.0.0.1:8084")
public interface RatingsClient {

    @RequestMapping(value = "/ratings", method = { RequestMethod.GET })
    List<Rating> getRatingsByBookId(@RequestParam("bookId") Long bookId, @RequestHeader("Cookie") String session);
}
