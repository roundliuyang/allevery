package com.yly.sentineldemo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    /**
     * 流量控制
     */
    @GetMapping("/echo")
    public String echo() {
        return "echo";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    /**
     * 熔断降级
     */
    @GetMapping("/sleep")
    public String sleep() throws InterruptedException {
        Thread.sleep(100L);
        return "sleep";
    }

    /**
     * 热点参数限流
     */
    @GetMapping("/product_info")
    @SentinelResource("demo_product_info_hot")
    public String productInfo(Integer id) {
        return "商品编号：" + id;
    }

    /**
     * 手动使用 Sentinel 客户端 API（手动对资源进行限流）
     */
    @GetMapping("/entry_demo")
    public String entryDemo() {
        Entry entry = null;
        try {
            // <1> 访问资源,其中，参数 name 就是在 Sentinel 中定义的资源名。如果访问资源被拒绝，例如说被限流或降级，则会抛出 BlockException 异常。
            entry = SphU.entry("entry_demo");

            //<2> ... 执行业务逻辑

            return "执行成功";
        } catch (BlockException ex) {    //<3>
            return "被拒绝";
        } finally {
            // <4>释放资源
            if (entry != null) {
                entry.exit();
            }
        }
    }

    /**
     * 测试 @SentinelResource 注解
     * 上面手动进行资源的保护时，对代码的侵入性太强，需要将业务逻辑进行修改，
     * 因此Sentinel 提供了 @SentinelResource 注解声明自定义资源，通过 Spring AOP 拦截该注解的方法，自动调用 Sentinel 客户端 API，进行指定资源的保护。
     *
     * 特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 BlockException 时只会进入 blockHandler 处理逻辑。
     * fallback 和 blockHandler 的差异点，在于 blockHandler 只能处理 BlockException 异常，fallback 能够处理所有异常。
     * 如果都配置的情况下，BlockException 异常分配给 blockHandler 处理，其它异常分配给 fallback 处理.
     */
    @GetMapping("/annotations_demo")
    @SentinelResource(value = "annotations_demo_resource",
            blockHandler = "blockHandler",
            fallback = "fallback")
    public String annotationsDemo(@RequestParam(required = false) Integer id) throws InterruptedException {
        if (id == null) {
            throw new IllegalArgumentException("id 参数不允许为空");
        }
        return "success...";
    }


    // BlockHandler 处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String blockHandler(Integer id, BlockException ex) {
        return "block：" + ex.getClass().getSimpleName();
    }

    // Fallback 处理函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String fallback(Integer id, Throwable throwable) {
        return "fallback：" + throwable.getMessage();
    }

}
