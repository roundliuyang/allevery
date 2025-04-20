package com.yly.jvm.classloader;

public class Test {

    public static void main(String[] args) {
        // JVM是根据按需来初始化类的，此时我们的调用好像没有用到类A的信息。所以结果只是：初始化类C，最后构造了一个类C的实例对象
        new StaticInnerClassTest.C();
    }
}
