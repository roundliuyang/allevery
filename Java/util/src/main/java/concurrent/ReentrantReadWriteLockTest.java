package concurrent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
    public static void main(String[] args) throws Exception {
        //线程池, 这里重写线程创建工厂, 指定线程名称
        ExecutorService executorService = Executors.newFixedThreadPool(20, new ThreadFactory() {
            AtomicInteger counter = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                final Thread thread = new Thread(r);
                thread.setName("线程" + counter.getAndIncrement());
                return thread;
            }
        });

        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);//参数boolean表示公平和非公平
        int taskCount = 4;//任务数量
        CountDownLatch latch = new CountDownLatch(taskCount << 1);//读和写线程, 所以x2
        final WriteTask writeTask = new WriteTask(readWriteLock, latch);//写线程
        final ReadTask readTask = new ReadTask(readWriteLock, latch);//读线程
        for (int i = 0; i < taskCount; i++) {
            executorService.execute(readTask);//读线程
            executorService.execute(writeTask);//写线程
        }

        latch.await();
        System.out.println("===============================程序退出===============================");
        executorService.shutdown();
    }

    private static class WriteTask implements Runnable {
        /** 读写锁 */
        final ReentrantReadWriteLock readWriteLock;
        /** CountDownLatch */
        final CountDownLatch latch;

        public WriteTask(ReentrantReadWriteLock readWriteLock, CountDownLatch latch) {
            this.readWriteLock = readWriteLock;
            this.latch = latch;
        }

        @Override
        public void run() {
            final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
            try {
                writeLock.lock();//写锁
                final String name = Thread.currentThread().getName();
                System.out.println(name + "在时间 " + getTime() +" 成功获取写锁, 开始业务操作......");
                sleep(2, TimeUnit.SECONDS);//此处模拟业务操作时间
            } finally {
                writeLock.unlock();
                latch.countDown();
            }
        }
    }

    private static class ReadTask implements Runnable {
        /** 读写锁 */
        final ReentrantReadWriteLock readWriteLock;
        /** CountDownLatch */
        final CountDownLatch latch;

        public ReadTask(ReentrantReadWriteLock readWriteLock, CountDownLatch latch) {
            this.readWriteLock = readWriteLock;
            this.latch = latch;
        }

        @Override
        public void run() {
            final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
            try {
                readLock.lock();//读锁
                final String name = Thread.currentThread().getName();
                System.out.println(name + "在时间 " + getTime() +" 成功获取读锁, 开始业务操作......");
                sleep(2, TimeUnit.SECONDS);//此处模拟业务操作时间
            } finally {
                readLock.unlock();
                latch.countDown();
            }
        }
    }

    public static void sleep(int timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
