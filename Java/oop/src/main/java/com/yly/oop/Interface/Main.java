package com.yly.oop.Interface;

public class Main implements MyInterface {
    public void abstractMethod() {
        System.out.println("Abstract method implementation.");
    }

    public static void main(String[] args) {
        Main obj = new Main();
        obj.abstractMethod(); // Output: Abstract method implementation.
        obj.defaultMethod(); // Output: This is a default method.

        // 调用接口中的静态方法
        MyInterface.staticMethod(); // Output: This is a static method.
    }
}