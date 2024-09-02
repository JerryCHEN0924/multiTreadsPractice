/*
  這邊介紹
  解決辦法一:synchronized關鍵字
  解決辦法二:AtomicInteger類
  如果是多執行緒需要共享和操作整數數據的情境，方法二的AtomicInteger類會比方法一synchronized效能優秀。
  因為AtomicInteger 使用的是無鎖設計 (lock-free)，這意味著在高併發情況下，它的性能比使用傳統的鎖 (如 synchronized) 更好。
 */
package com.example.multiTreadsPractice.udemyCource;

import java.util.concurrent.atomic.AtomicInteger;

public class synchronlizedDemo {
    //解決辦法一:使用synchronized關鍵字
    private int count = 0;

    //解決辦法二:AtomicInteger
    private static final AtomicInteger counter = new AtomicInteger(0);

    //此方法會造成執行緒安全問題,count數值將不會是200000
    public void increment() {
        count++;
    }

    //加上synchronized關鍵字使得此方法為同步，執行緒安全。
    public synchronized void incrementWithSynchronized() {
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        new synchronlizedDemo().doWork();
    }

    void doWork() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
//                increment();

                //解決辦法一:
                incrementWithSynchronized();

                //解決辦法二:
                counter.incrementAndGet();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
//                increment();

                //解決辦法一:
                incrementWithSynchronized();

                //解決辦法二:
                counter.incrementAndGet();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("計數器1值:" + count);
        System.out.println("計數器2值:" + counter);
    }
}
