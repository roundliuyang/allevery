package com.yly.attach;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class PrintNumAgent {

    /**
     *
     * @param agentArgs 代理程序的参数
     * @param inst java.lang.instrument.Instrumentation对象，用于在运行时修改类定义
     */
    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        // 打印输出字符串"agentmain"，用于表示代理程序开始执行
        System.out.println("agentmain");
        // 使用Instrumentation对象的addTransformer方法，将一个名为PrintNumTransformer的转换器（Transformer）添加到目标Java虚拟机中。
        // 转换器是一个实现了java.lang.instrument.ClassFileTransformer接口的类，它可以对加载的类字节码进行转换或修改。
        inst.addTransformer(new com.yly.attach.PrintNumTransformer(), true);

        // 使用Instrumentation对象的getAllLoadedClasses方法获取目标Java虚拟机中已加载的所有类
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        // 遍历所有已加载的类
        for (Class allLoadedClass : allLoadedClasses) {
            // 判断类的简单名称是否与"PrintNumTest"相等。如果是，表示找到了名为PrintNumTest的类
            if (allLoadedClass.getSimpleName().equals("PrintNumTest")) {
                // 打印输出正在重新加载的类的名称
                System.out.println("Reloading: " + allLoadedClass.getName());
                // 使用Instrumentation对象的 retransformClasses 方法，重新转换指定的类。这将导致目标类的字节码被重新加载和转换。
                inst.retransformClasses(allLoadedClass);
                break;
            }
        }
    }
}
