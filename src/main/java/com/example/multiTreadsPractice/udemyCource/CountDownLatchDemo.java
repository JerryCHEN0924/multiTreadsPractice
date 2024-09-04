/*
  CountDownLatch允許一個或多個執行緒等待，直到其他執行緒完成一組操作後，再繼續執行。
  CountDownLatch 是一種計數器機制，用來管理多執行緒之間的協作。
 */
package com.example.multiTreadsPractice.udemyCource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo implements Runnable {
    private CountDownLatch countDownLatch;

    public CountDownLatchDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + "開始");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        countDownLatch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.submit(new CountDownLatchDemo(latch));
        }

        latch.await();
        executorService.shutdown();
        System.out.println("結束");
    }
}
