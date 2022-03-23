package com.yly.ribbon.demo02B.consumer.config;

import com.yly.ribbon.demo02B.consumer.ribbon.DefaultRibbonClientConfiguration;
import com.yly.ribbon.demo02B.consumer.ribbon.UserProviderRibbonClientConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;


/*
        ① 对于 DefaultRibbonClientConfiguration 和 UserProviderRibbonClientConfiguration 两个配置类，我们并没有和 DemoConsumerApplication 启动类放在一个包路径下。
        因为，Spring Boot 项目默认扫描 DemoConsumerApplication 所在包以及子包下的所有 Bean 们。而 @Configuration 注解也是一种 Bean，也会被扫描到。
        如果将 DefaultRibbonClientConfiguration 和 UserProviderRibbonClientConfiguration 放在 DemoConsumerApplication 所在包或子包中，将会被 Spring Boot 所扫描到，导致整个项目的 Ribbon 客户端都使用相同的 Ribbon 配置，就无法到达 Ribbon 客户端级别的自定义配置的目的。
        因此，这里在根路径下又创建了 ribbon 包，并将 DefaultRibbonClientConfiguration、UserProviderRibbonClientConfiguration 放入其中，避免被 Spring Boot 所扫描到。
        ② @RibbonClients 注解，通过 defaultConfiguration 属性声明 Ribbon 全局级别的自定义配置，通过 value 属性声明多个 Ribbon 客户端级别的自定义配置。
        具体的自定义配置，通过创建 Spring 配置类，例如说 DefaultRibbonClientConfiguration 和 UserProviderRibbonClientConfiguration。
        ③ @RibbonClient 注解，声明一个 Ribbon 客户端级别的自定义配置，其中 name 属性用于设置 Ribbon 客户端的名字。
        为了避免多个 Ribbon 客户端级别的配置类创建的 Bean 之间互相冲突，Spring Cloud Netflix Ribbon 通过 SpringClientFactory 类，为每一个 Ribbon 客户端创建一个 Spring 子上下文。
        不过这里要注意，因为 DefaultRibbonClientConfiguration 和 UserProviderRibbonClientConfiguration 都创建了 IRule Bean，而 DefaultRibbonClientConfiguration 是在 Spring 父上下文生效，会和 UserProviderRibbonClientConfiguration 所在的 Spring 子上下文共享。这样就导致从 Spring 获取 IRule Bean 时，存在两个而不知道选择哪一个。因此，我们声明 UserProviderRibbonClientConfiguration 创建的 IRule Bean 为 @Primary，优先使用它。
 */
@Configuration
@RibbonClients(
        value = {
                @RibbonClient(name = "demo-provider", configuration = UserProviderRibbonClientConfiguration.class) // 客户端级别的配置
        },
        defaultConfiguration = DefaultRibbonClientConfiguration.class // 全局配置
)
public class RibbonConfiguration {
}
