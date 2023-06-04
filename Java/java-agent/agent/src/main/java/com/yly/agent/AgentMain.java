package com.yly.agent;

import java.lang.instrument.Instrumentation;

public class AgentMain {
    // premain()函数中注册MyClassFileTransformer转换器
    public static void premain (String agentArgs, Instrumentation instrumentation) {
        System.out.println("premain方法");
        instrumentation.addTransformer(new MyClassFileTransformer(), true);
    }
}