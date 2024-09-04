/*
  BlockingQueue為執行緒安全
  用Java內建的queue實作producer生產者與consumer消費者
 */

package com.example.multiTreadsPractice.udemyCource;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private static void producer() throws InterruptedException {
        Random random = new Random();
        while (true) {
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {
        Random random = new Random();
        while (true) {
            Thread.sleep(100);
            if (random.nextInt(10) == 0) {
                Integer value = queue.take();
                System.out.println("拿走的值" + value + "; Queue Size為:" + queue.size());
            }
        }

    }
}
