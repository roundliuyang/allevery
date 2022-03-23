package com.yly;

import java.util.concurrent.*;

/*
    下面是 FutureTask 方法的一个例子，它展示了 FutureTask 的常用方法。
 */
public class FutureTaskExample {

    public static void main(String[] args) {
        MyCallable callable1 = new MyCallable(1000);
        MyCallable callable2 = new MyCallable(2000);

        FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<String>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                if(futureTask1.isDone() && futureTask2.isDone()){
                    System.out.println("Done");
                    //shut down executor service
                    executor.shutdown();
                    return;
                }

                if(!futureTask1.isDone()){
                    //wait indefinitely for future task to complete
                    System.out.println("FutureTask1 output="+futureTask1.get());
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if(s !=null){
                    System.out.println("FutureTask2 output="+s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }catch(TimeoutException e){
                //do nothing
            }
        }

    }

}

/*
    当我们运行上面的程序时，您会注意到它有一段时间没有打印任何内容，因为get()FutureTask 的方法等待任务完成然后返回输出对象。
    还有一个重载方法也只等待指定的时间量，我们将它用于 futureTask2。还要注意使用isDone()方法来确保在所有任务执行后程序终止。
    上述程序的输出将是：

    FutureTask1 output=pool-1-thread-1
    Waiting for FutureTask2 to complete
    Waiting for FutureTask2 to complete
    Waiting for FutureTask2 to complete
    Waiting for FutureTask2 to complete
    Waiting for FutureTask2 to complete
    FutureTask2 output=pool-1-thread-2
    Done
    因此，FutureTask并没有什么好处，但当我们想覆盖Future接口的一些方法，而不想实现Future接口的每一个方法时，它就很方便了。
 */