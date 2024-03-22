package concurrent.threadpool.custom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRunnable implements Runnable {

    private Runnable runnable;

    public CustomRunnable() {

    }

    public CustomRunnable(final Runnable runnable) {
        this.runnable = runnable;
    }

    private String taskId;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        if (runnable == null) {
            return;
        }

        try {
            runnable.run();
        } catch (Exception e) {
//            log.error("任务:{},错误栈:{}", taskId, e);
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return Thread.currentThread().getName();
    }
}