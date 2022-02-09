package com.yly.feign.provider.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProviderService {

    @GetMapping("/echo")
    String echo(@RequestParam("name") String name);

}
