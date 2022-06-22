package com.example.demo.demos.sentinel;

import com.alibaba.cloud.demo.sentinel.api.FooService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eric Zhao
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Reference
    private FooService fooService;

    @Autowired
    private DemoService demoService;

    @GetMapping("/hello")
    public String apiSayHello(@RequestParam String name) {
        return fooService.sayHello(name);
    }

    @GetMapping("/bonjour/{name}")
    public String apiSayHelloLocal(@PathVariable String name) {
        return demoService.bonjour(name);
    }

    @GetMapping("/time")
    public long apiCurrentTime(@RequestParam(value = "slow", defaultValue = "false") Boolean slow) {
        return fooService.getCurrentTime(slow);
    }
}