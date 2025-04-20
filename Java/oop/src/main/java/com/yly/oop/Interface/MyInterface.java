package com.yly.oop.Interface;

public interface MyInterface {
    // 抽象方法
    void abstractMethod();

    // 默认方法
    default void defaultMethod() {
        System.out.println("This is a default method.");
    }

    // 静态方法
    static void staticMethod() {
        System.out.println("This is a static method.");
    }
}
