package com.yly.concurrent.locks;

/*
    现在让我们看一个简单的例子，我们将用 Java Lock API 替换 synchronized 关键字。
    假设我们有一个 Resource 类，其中包含一些我们希望它是线程安全的操作和一些不需要线程安全的方法。
 */

public class Resource {
    public void doSomething(){
        //do some operation, DB read, write etc
    }

    public void doLogging(){
        //logging, no need for thread safety
    }
}
