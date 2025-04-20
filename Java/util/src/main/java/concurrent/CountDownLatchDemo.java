package concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * CountDownLatch 主要用来解决一个线程等待多个线程的场景，可以类比旅游团团长要等待所有的游客到齐才能去下一个景点；而CyclicBarrier 是一组线程之间互相等待，更像是几个驴友之间不离不弃。
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建 2 个线程的线程池
        Executor executor = Executors.newFixedThreadPool(2);

        // 模拟存在未对账订单的情况
        while (true) {
            // 计数器初始化为 2
            CountDownLatch latch = new CountDownLatch(2);
            final List<Order> pos = new ArrayList<>();
            final List<Order> dos = new ArrayList<>();
            Diff diff;

            // 查询未对账订单
            executor.execute(() -> {
                pos.addAll(getPOrders());
                latch.countDown();
            });

            // 查询派送单
            executor.execute(() -> {
                dos.addAll(getDOrders());
                latch.countDown();
            });

            // 等待两个查询操作结束
            latch.await();

            // 执行对账操作
            diff = check(pos, dos);

            // 差异写入差异库
            save(diff);
        }
    }

    // 模拟获取未对账的采购订单
    private static List<Order> getPOrders() {
        // 实现获取未对账采购订单的逻辑
        return Collections.singletonList(new Order());
    }

    // 模拟获取未对账的派送单
    private static List<Order> getDOrders() {
        // 实现获取未对账派送单的逻辑
        return Collections.singletonList(new Order());
    }

    // 模拟对账操作
    private static Diff check(List<Order> pos, List<Order> dos) {
        // 实现对账操作的逻辑
        return new Diff();
    }

    // 模拟将差异写入差异库
    private static void save(Diff diff) {
        // 实现将差异写入差异库的逻辑
        // ...
    }

    // 定义订单和差异的类，具体实现根据业务逻辑而定
    private static class Order {
        // 订单的属性
        // ...
    }

    private static class Diff {
        // 差异的属性
        // ...
    }
}
