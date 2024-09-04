/*
  這邊示範Thread Pool執行緒池的使用方式。

 */
package com.example.multiTreadsPractice.udemyCource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo implements Runnable {
    private int id;

    public ThreadPoolDemo(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("啟動中的ID:" + id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("已結束的ID:" + id);
    }

    public static void main(String[] args) throws InterruptedException {
        //建立一個固定大小的ThreadPool執行緒池，ThreadPool就像工廠裡面的工人，在此有2個工人。
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //將工作重複指派5次，給2個工人。
        for (int i = 0; i < 5; i++) {
            executorService.submit(new ThreadPoolDemo(i));
        }
        executorService.shutdown();

        System.out.println("所有任務已提交");

        //等待工作結束，這邊單位是一天，如果要以小時計算，則第二個參數要調整。
        executorService.awaitTermination(1, TimeUnit.DAYS);

        System.out.println("所有任務已結束");
    }
}
