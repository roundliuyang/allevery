package com.example.demo.demos.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;


@Service
public class DemoService {

    @SentinelResource(value = "DemoService#bonjour", defaultFallback = "bonjourFallback")
    public String bonjour(String name) {
        return "Bonjour, " + name;
    }

    public String bonjourFallback(Throwable t) {
        if (BlockException.isBlockException(t)) {
            return "Blocked by Sentinel: " + t.getClass().getSimpleName();
        }
        return "Oops, failed: " + t.getClass().getCanonicalName();
    }
}