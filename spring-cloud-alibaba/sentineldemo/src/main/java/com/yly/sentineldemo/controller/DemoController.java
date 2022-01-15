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

    @GetMapping("/echo")
    public String echo() {
        return "echo";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/sleep")
    public String sleep() throws InterruptedException {
        Thread.sleep(100L);
        return "sleep";
    }

    // 测试热点参数限流
//    @GetMapping("/product_info")
//    @SentinelResource("demo_product_info_hot")
//    public String productInfo(Integer id) {
//        return "商品编号：" + id;
//    }
//
//    // 手动使用 Sentinel 客户端 API
//    @GetMapping("/entry_demo")
//    public String entryDemo() {
//        Entry entry = null;
//        try {
//            // <1> 访问资源
//            entry = SphU.entry("entry_demo");
//
//            //<2> ... 执行业务逻辑
//
//            return "执行成功";
//        } catch (BlockException ex) {<3>
//            return "被拒绝";
//        } finally {
//            // <4>释放资源
//            if (entry != null) {
//                entry.exit();
//            }
//        }
//    }
//
//    // 测试 @SentinelResource 注解
//    @GetMapping("/annotations_demo")
//    @SentinelResource(value = "annotations_demo_resource",
//            blockHandler = "blockHandler",
//            fallback = "fallback")
//    public String annotationsDemo(@RequestParam(required = false) Integer id) throws InterruptedException {
//        if (id == null) {
//            throw new IllegalArgumentException("id 参数不允许为空");
//        }
//        return "success...";
//    }
//
//    // BlockHandler 处理函数，参数最后多一个 BlockException，其余与原函数一致.
//    public String blockHandler(Integer id, BlockException ex) {
//        return "block：" + ex.getClass().getSimpleName();
//    }
//
//    // Fallback 处理函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
//    public String fallback(Integer id, Throwable throwable) {
//        return "fallback：" + throwable.getMessage();
//    }


    /*
    整个逻辑，和我们使用 Java 进行 I/O 操作的代码比较像，通过 try catch finally 经典套路。
    <1> 处，调用 Sentinel 的 SphU#entry(String name) 方法，访问资源。其中，参数 name 就是在 Sentinel 中定义的资源名。如果访问资源被拒绝，例如说被限流或降级，则会抛出 BlockException 异常。
    <2> 处，编写具体的业务逻辑代码。
    <3> 处，处理访问资源被拒绝所抛出的 BlockException 异常。这里，我们是直接返回 "被拒绝" 的字符串。
    <4> 处，调用 Sentinel 的 Entry#exit() 方法，释放对资源的访问。注意，entry 和 exit 必须成对出现，不然资源一直被持有者。
    这里我们编写的示例是比较简单的，推荐胖友后续自己看下 sentinel-spring-webmvc-adapter 提供的 AbstractSentinelInterceptor 拦截器对 Sentinel 客户端 API 的使用。
     */

}
