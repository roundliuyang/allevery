package com.yly.interview;

class PrintNumber implements Runnable {
    private static final Object lock = new Object();
    private static int currentNumber = 0;

    @Override
    public void run() {
        while (currentNumber < 10) {
            synchronized (lock) {
                // 判断当前线程是否应该打印
                if (currentNumber % 10 == Integer.parseInt(Thread.currentThread().getName())) {
                    System.out.println(Thread.currentThread().getName());
                    currentNumber++;
                    lock.notifyAll();  // 唤醒其他等待的线程
                } else {
                    try {
                        lock.wait();  // 当前线程等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        PrintNumber printNumber = new PrintNumber();

        for (int i = 0; i < 10; i++) {
            new Thread(printNumber, Integer.toString(i)).start();
        }
    }
}