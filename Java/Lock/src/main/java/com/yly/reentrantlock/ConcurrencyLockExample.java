package com.yly.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 看看 现在让我们如何 使用 java Lock API 并在不使用synchronized关键字的情况下重写上面的程序。我们将在java中使用 ReentrantLock
 */
public class ConcurrencyLockExample implements Runnable{
    private Resource resource;
    private Lock lock;

    public ConcurrencyLockExample(Resource resource) {
        this.resource = resource;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        try {
            if(lock.tryLock(10, TimeUnit.SECONDS)){
                resource.doSomething();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            // release lock
            lock.unlock();
        }
        resource.doLogging();
    }
}
// 正如你所看到的，我是用TryLock()方法来确保我的线程只等待确定的时间，如果锁定它没有获得对象的锁定，他只是记录和退出。
// 另一个注意的重点是使用try-finally 块来确保及时doSomething()方法调用抛出任何异常也释放锁。
// Java Lock vs synchronized
/*
    基于上述细节和程序，我们可以很容易地得出以下Java锁和synchronized的区别。
    Java Lock API 为锁定提供了更多的可见性和选项，与synchronized 线程可能最终无限期地等待锁定不同，我们可以使用 tryLock() 来确保线程仅等待特定时间。
    synchronized代码更加简洁，易于维护，而使用Lock时，我们不得不使用try-finally块来确保Lock被释放，即使在lock()和unlock()方法调用之间抛出了一些异常
    同步块或方法只能覆盖一种方法，而我们可以使用 Lock API 在一种方法中获取锁并在另一种方法中释放它.
    synchronized 关键字不提供公平性，而我们可以在创建 ReentrantLock 对象时将公平性设置为 true，以便最长等待的线程首先获得锁。
    我们可以为 Lock 创建不同的条件，不同的线程可以为不同的条件 await()。
 */



