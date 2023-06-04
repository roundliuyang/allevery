package com.yly.agent;

/**
 * 当你使用java -javaagent命令来运行Java应用程序时，指定的代理（Agent）将被加载并与目标应用程序一起启动。代理可以通过Java的Instrumentation机制来修改或增强目标应用程序的行为。
 *
 * java -javaagent:/mnt/d/xx/untitled/target/untitled-1.0-SNAPSHOT-jar-with-dependencies.jar com.yly.MyTest中，-javaagent选项指定了一个代理的路径，
 * 一旦代理被加载，它会根据代理的实现来修改或增强目标应用程序的行为。这可能包括在目标应用程序的类加载过程中进行字节码转换、修改方法的行为等操作。
 */
public class MyTest {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(3000);
    }
}