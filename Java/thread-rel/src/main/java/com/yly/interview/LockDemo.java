package com.yly.interview;


public class LockDemo {
    public synchronized void instanceLock() {
    }

    public static synchronized void classLock() {
    }

    public void blockLock() {
        synchronized (this) {
        }
    }
}
