package com.yly.custom;

import java.util.concurrent.Callable;


public class CustomCallable<T> implements Callable {

    Callable<T> callable;

    public CustomCallable(Callable<T> callable) {
        this.callable = callable;
    }

    private String taskId;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public T call() throws Exception {
        if (callable == null) {
            return null;
        }

        try {
            return callable.call();
        } catch (Exception e) {
//            log.error("任务:{},错误栈:{}", taskId, e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return Thread.currentThread().getName();
    }
}
