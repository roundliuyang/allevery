package com.alibaba.cloud.dubboprovidersample;

import com.alibaba.cloud.EchoService;
import org.apache.dubbo.config.annotation.Service;


/**
 * 其中，@org.apache.dubbo.config.annotation.Service是 Dubbo 服务注解，仅声明该 Java 服务（本地）实现为 Dubbo 服务。
 * 因此，下一步需要将其配置 Dubbo 服务（远程）。
 */
@Service
public class SimpleEchoService implements EchoService {
    @Override
    public String echo(String s) {
        return "[ECHO] " + s;
    }
}
