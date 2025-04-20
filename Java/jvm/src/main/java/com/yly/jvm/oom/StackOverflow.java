package com.yly.jvm.oom;

public class StackOverflow {
    public static long counter = 0L;

    public static void main(String[] args) {
        work();
    }

    private static void work() {
        System.out.println("第" + ++counter + "次调用work方法");
        work();
    }
}
