package concurrent;

import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier 的计数器是可以循环利用的，而且具备自动重置的功能，一旦计数器减到 0 会自动重置到你设置的初始值。
 * 除此之外，CyclicBarrier 还可以设置回调函数，可以说是功能丰富
 */
public class CyclicBarrierDemo {

    // 订单队列
    Vector<P> pos = new Vector<>();
    // 派送单队列
    Vector<D> dos = new Vector<>();
    // 执行回调的线程池
    Executor executor = Executors.newFixedThreadPool(1);
    final CyclicBarrier barrier = new CyclicBarrier(2, () -> executor.execute(() -> check()));

    public static void main(String[] args) {
        CyclicBarrierDemo demo = new CyclicBarrierDemo();
        demo.checkAll();
    }

    void check() {
        P p = pos.remove(0);
        D d = dos.remove(0);
        // 执行对账操作
        Diff diff = check(p, d);
        // 差异写入差异库
        save(diff);
    }

    void checkAll() {
        // 循环查询订单库
        Thread T1 = new Thread(() -> {
            // 模拟存在未对账订单
            while (true) {
                // 查询订单库
                pos.add(getPOrders());
                // 等待
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        T1.start();
        // 循环查询运单库
        Thread T2 = new Thread(() -> {
            // 模拟存在未对账订单
            while (true) {
                // 查询运单库
                dos.add(getDOrders());
                // 等待
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        T2.start();
    }

    // 模拟获取未对账的采购订单
    private P getPOrders() {
        // 实现获取未对账采购订单的逻辑
        return new P();
    }

    // 模拟获取未对账的派送单
    private D getDOrders() {
        // 实现获取未对账派送单的逻辑
        return new D();
    }

    // 模拟对账操作
    private Diff check(P p, D d) {
        // 实现对账操作的逻辑
        return new Diff();
    }

    // 模拟将差异写入差异库
    private void save(Diff diff) {
        // 实现将差异写入差异库的逻辑
        // ...
        System.out.println(diff.toString());
    }

    // 定义订单和差异的类，具体实现根据业务逻辑而定
    private static class P {
        // 订单的属性
        // ...
    }

    private static class D {
        // 派送单的属性
        // ...
    }

    private static class Diff {
        // 差异的属性
        // ...
    }
}
