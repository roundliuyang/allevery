package com.yly.custom;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface CustomThreadPool {


    boolean execute(Runnable runnable);

    <T> Future<T> submit(Callable<T> tCallable);

    void shutDown();
}
