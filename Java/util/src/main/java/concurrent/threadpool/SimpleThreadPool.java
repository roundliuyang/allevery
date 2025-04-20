package concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    这是测试程序类SimpleThreadPool.java，我们在其中从Executors 框架创建固定线程池。
 */
public class SimpleThreadPool {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("" + i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}
/*
    在上面的程序中，我们创建了一个由 5 个工作线程组成的固定大小的线程池。然后我们向这个池提交 10 个作业，由于池大小为 5，它将开始处理 5 个作业，其他作业将处于等待状态，
    一旦其中一个作业完成，等待队列中的另一个作业就会被工作线程拾取并执行。这是上述程序的输出。
    pool-1-thread-2 Start. Command = 1
    pool-1-thread-4 Start. Command = 3
    pool-1-thread-1 Start. Command = 0
    pool-1-thread-3 Start. Command = 2
    pool-1-thread-5 Start. Command = 4
    pool-1-thread-4 End.
    pool-1-thread-5 End.
    pool-1-thread-1 End.
    pool-1-thread-3 End.
    pool-1-thread-3 Start. Command = 8
    pool-1-thread-2 End.
    pool-1-thread-2 Start. Command = 9
    pool-1-thread-1 Start. Command = 7
    pool-1-thread-5 Start. Command = 6
    pool-1-thread-4 Start. Command = 5
    pool-1-thread-2 End.
    pool-1-thread-4 End.
    pool-1-thread-3 End.
    pool-1-thread-5 End.
    pool-1-thread-1 End.
    Finished all threads
    输出确认池中有从“pool-1-thread-1”到“pool-1-thread-5”的五个线程，它们负责执行提交到池中的任务。
 */