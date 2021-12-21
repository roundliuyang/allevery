package com.yly.reentrantlock;

/**
 *  现在假设我们有一个 Runnable 类，我们将在其中使用Resource 方法
 */
public class SynchronizedLockExample implements Runnable{
    private Resource resource;

    public SynchronizedLockExample(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        synchronized (resource) {
            resource.doSomething();
        }
        resource.doLogging();
    }
}

// 请注意，我正在使用同步块来获取对 Resource 对象的锁定。我们可以在类中创建一个虚拟对象并将其用于锁定目的