/*
  本篇介紹如何中斷執行緒
 */
package com.example.multiTreadsPractice.udemyCource;

import java.util.Random;

public class InterruptedDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("開始");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                //這邊1E8是10的8次方，科學記數法（Scientific Notation）。人類一點就是100000000
                for (int i = 0; i < 1E8; i++) {

                    //這邊就是加入執行緒的狀態檢查，如果有被設定成interrupt，就自行break中止。
                    boolean interrupted = Thread.currentThread().isInterrupted();
                    if (interrupted){
                        break;
                    }

                    /*
                    或是這種方式也會檢查是否被中斷Interrupted
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                     */

                    Math.sin(random.nextDouble());
                }
            }
        });

        t1.start();
        /*
          注意! 這邊interrupt實際上只是告訴執行緒「要中斷」，但事實上並不會中斷，可以當作一個狀態表示。
          所以上面會加入一段程式去檢查狀態。
         */
        t1.interrupt();

        t1.join();

        System.out.println("結束");
    }
}
