package concurrent.threadpool.custom;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class CustomThreadFactory implements ThreadFactory {
    private AtomicInteger threadNum = new AtomicInteger(1);
    private final static String prefix = "Custom-thread-";

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + threadNum.getAndIncrement();
        ThreadGroup threadGroup = new CustomThreadGroup("Custom_GROUP");
        //非守护进程
        threadGroup.setDaemon(false);
        //优先执行
        threadGroup.setMaxPriority(Thread.MAX_PRIORITY);

        Thread thread = new Thread(threadGroup, r, name);
        return thread;
    }


    static class CustomThreadGroup extends ThreadGroup {

        public CustomThreadGroup(String name) {
            super(name);
        }

        public CustomThreadGroup(ThreadGroup parent, String name) {
            super(parent, name);
        }

        /**
         * 异常处理
         *
         * @param t
         * @param e
         */
        @Override
        public void uncaughtException(Thread t, Throwable e) {
//            log.error("线程名称:{},异常信息:{},异常栈:{}",
//                    t.getName(), e.getMessage(), e);
        }
    }
}