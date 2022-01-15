package com.yly.sentineldemo.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebTotalInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcTotalConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add Sentinel interceptor
//        addSentinelWebTotalInterceptor(registry);
        addSentinelWebInterceptor(registry);
    }

    private void addSentinelWebInterceptor(InterceptorRegistry registry) {
        // <1.1>  创建 SentinelWebMvcConfig 对象
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        config.setHttpMethodSpecify(true); // <1.2>是否包含请求方法。即基于 URL 创建的资源，是否包含 Method。
        // config.setBlockExceptionHandler(new DefaultBlockExceptionHandler()); // <1.3>设置 BlockException 处理器。
//        config.setOriginParser(new RequestOriginParser() { // 设置请求来源解析器。用于黑白名单控制功能。
//
//            @Override
//            public String parseOrigin(HttpServletRequest request) {
//                // 从 Header 中，获得请求来源
//                String origin = request.getHeader("s-user");
//                // 如果为空，给一个默认的
//                if (StringUtils.isEmpty(origin)) {
//                    origin = "default";
//                }
//                return origin;
//            }
//
//        });

        // <2> 添加 SentinelWebInterceptor 拦截器
        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
    }

    private void addSentinelWebTotalInterceptor(InterceptorRegistry registry) {
        // <1> 创建 SentinelWebMvcTotalConfig 对象
        SentinelWebMvcTotalConfig config = new SentinelWebMvcTotalConfig();

        // <2> 添加 SentinelWebTotalInterceptor 拦截器
        registry.addInterceptor(new SentinelWebTotalInterceptor(config)).addPathPatterns("/**");
    }

    /*
    #addSentinelWebTotalInterceptor(InterceptorRegistry registry) 方法，添加 SentinelWebTotalInterceptor 拦截器。
    #addSentinelWebInterceptor(InterceptorRegistry registry) 方法，添加 SentinelWebInterceptor 拦截器。
    SentinelWebInterceptor 拦截器，针对每个 URL 进行流量控制。
        <1.1> 处，创建 SentinelWebMvcConfig 对象，用于作为 SentinelWebInterceptor 拦截器的配置。
        <1.2> 处，设置 是否包含请求方法。即基于 URL 创建的资源，是否包含 Method。这里有一个非常重要的概念，就是“资源”。

    资源是 Sentinel 的关键概念。它可以是 Java 应用程序中的任何内容，例如，由应用程序提供的服务，或由应用程序调用的其它应用提供的服务，甚至可以是一段代码。在接下来的文档中，我们都会用资源来描述代码块。
    只要通过 Sentinel API 定义的代码，就是资源，能够被 Sentinel 保护起来。大部分情况下，可以使用方法签名，URL，甚至服务名称作为资源名来标示资源。
    对于 SentinelWebInterceptor 拦截器来说，将 URL + Method 作为一个资源，进行流量控制。具体的，可以看看 SentinelWebInterceptor#getResourceName(HttpServletRequest request) 方法的代码。

    <1.3> 处，设置 BlockException 的处理器。Sentinel 在流量控制时，当请求到达阀值后，会抛出 BlockException 异常。此时，可以通过定义 BlockExceptionHandler 去处理。这里，我们使用 SpringMVC 提供的全局异常处理机制，具体可见「2.3 GlobalExceptionHandler」。

    <2> 处，添加 SentinelWebInterceptor 拦截器到 InterceptorRegistry 中。

    SentinelWebTotalInterceptor 拦截器，针对全局 URL 进行流量控制。简单来说，所有 URL 合计流量，全局统一进行控制。
        <1> 处，创建 SentinelWebMvcTotalConfig 对象，用于作为 SentinelWebTotalInterceptor 拦截器的配置。
        <2> 处，添加 SentinelWebTotalInterceptor 拦截器到 InterceptorRegistry 中。
     */

}
