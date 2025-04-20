package concurrent;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor fixDelayExecutor = new ScheduledThreadPoolExecutor(2);
        ScheduledThreadPoolExecutor fixRateExecutor = new ScheduledThreadPoolExecutor(2);

        //固定延迟任务 Start
        fixDelayExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(getTime() + " - 固定延迟任务");
                sleep(2, TimeUnit.SECONDS);
            }
        }, 2, 3, TimeUnit.SECONDS);
        //固定延迟任务 End

        //固定频率任务 Start
        fixRateExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(getTime() + " - 固定频率任务");
                sleep(2, TimeUnit.SECONDS);
            }
        }, 2, 3, TimeUnit.SECONDS);
        //固定频率任务 End

        //非周期任务 Start
        fixRateExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(getTime() + " - 非周期任务");
            }
        }, 2, TimeUnit.SECONDS);
        //非周期任务 End


    }

    private static String getTime() {
        
        return LocalDateTime.now().toString();
    }

    public static void sleep(int timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
