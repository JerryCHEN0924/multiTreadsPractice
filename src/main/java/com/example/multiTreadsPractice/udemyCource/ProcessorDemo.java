package com.example.multiTreadsPractice.udemyCource;

import java.util.Scanner;

public class ProcessorDemo {

    public static void main(String[] args) throws InterruptedException {
        ProcessorDemo processor = new ProcessorDemo(); // 使用同一個 Processor 對象
        // 建立並啟動執行緒t1負責做processor.produce();
        Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 建立並啟動執行緒t2負責做processor.consume();
        Thread t2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        //記得！ join的作用是告知main這個主執行緒，「在t1,t2還沒執行完畢前，不要結束！」
        t1.join();
        t2.join();
    }

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("produce的執行緒正在執行中");
            System.out.println("produce的執行緒即將wait");
            wait();
            System.out.println("produce的執行緒已被喚醒");
            System.out.println("produce的執行緒執行結束");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);

        synchronized (this) {
            System.out.println("consume的執行緒正在執行中");
            System.out.println("等待按下ENTER鍵");
            scanner.nextLine();
            System.out.println("已按下ENTER鍵，執行notify()通知其他執行緒");
            notify();
        }
    }
}
