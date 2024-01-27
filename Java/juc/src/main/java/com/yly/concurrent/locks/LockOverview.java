package com.yly.concurrent.locks;

/*
    欢迎使用 Java Lock 示例教程。通常在多线程环境中工作时，我们使用同步来保证线程安全。

    Java Lock
    大多数时候，synchronized关键字是一种可行的方式，但它有一些缺点，导致在Java并发包中加入了Lock API。Java 1.5并发API中出现了带有Lock接口的java.util.concurrent.locks包和一些实现类，以改善对象的锁定机制

    Java Lock API 中的一些重要接口和类是：
    Lock：这是 Lock API 的基本接口。它提供了synchronized关键字的所有功能，并提供了额外的方式来创建不同的锁定条件，为线程等待锁定提供超时。一些重要的方法是lock()获取锁，unlock()释放锁，tryLock()等待锁一段时间，newCondition()创建Condition等。
    Condition: 条件对象类似于对象的等待-通知模型，具有创建不同 wait 集的额外功能。一个Condition对象总是由Lock对象创建。其中一些重要的方法是await()，它类似于wait()和signal()，signalAll()类似于notify()和notifyAll()方法。
    ReadWriteLock: 它包含一对关联的锁，一个用于只读操作，另一个用于写入。只要没有写线程，读锁就可以被多个读线程同时持有。写锁是独占的。
    ReentrantLock: 这是最广泛使用的Lock接口的实现类。该类以类似于synchronized关键字的方式实现了Lock接口。除了Lock接口的实现，ReentrantLock还包含一些实用的方法来获取持有锁的线程和等待获得锁的线程等。

    同步块在本质上是可重入的，即如果一个线程在监控对象上有锁，如果另一个同步块需要在同一个监控对象上有锁，那么线程可以进入该代码块。我想这就是类的名字为ReentrantLock的原因。让我们通过一个简单的例子来理解这个特性。


    public class Test{

    public synchronized foo(){
        //do something
        bar();
      }
      public synchronized bar(){
        //do some more
      }
    }
    如果一个线程进入foo()，它拥有Test对象的锁，所以当它试图执行bar()方法时，该线程被允许执行bar()方法，因为它已经持有Test对象的锁，即与synchronized(this)相同。
 */
public class LockOverview {
}
