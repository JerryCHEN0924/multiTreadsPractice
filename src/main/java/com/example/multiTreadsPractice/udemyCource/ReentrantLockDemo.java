/*
  介紹ReentrantLock可重入鎖,相較起物件的wait()與notify(),使用ReentrantLock的condition更為靈活
 */
package com.example.multiTreadsPractice.udemyCource;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    private void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("firstThread已取得鎖，鎖定中");
        System.out.println("firstThread即將進入await，並釋放鎖");
        condition.await();
        System.out.println("firstThread已從await中被喚醒，並重新取得鎖");
        try{
            increment();
        }finally {
            lock.unlock();
        }
    }

    private void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("secondThread已取得鎖，鎖定中");

        System.out.println("請按下Enter!");
        new Scanner(System.in).nextLine();
        System.out.println("已按下Enter,即將通知其他執行緒啟動!");


        condition.signal();

        try{
            increment();
        }finally {
            lock.unlock();
        }
    }

    private void finished(){
        System.out.println("count:"+count);
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo(); // 使用同一個 Processor 對象
        // 建立執行緒t1並負責做reentrantLockDemo.firstThread();
        Thread t1 = new Thread(() -> {
            try {
                reentrantLockDemo.firstThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 建立執行緒t2並負責做reentrantLockDemo.secondThread();
        Thread t2 = new Thread(() -> {
            try {
                reentrantLockDemo.secondThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        //記得！ join的作用是告知main這個主執行緒，「在t1,t2還沒執行完畢前，不要結束！」
        t1.join();
        t2.join();

        reentrantLockDemo.finished();
    }
}
