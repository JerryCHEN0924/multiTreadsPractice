/*
  介紹Semaphore訊號/訊號量
  Semaphore 通常被比作一個具有一定數量“許可證”（permits）的計數器。
  每個許可證允許一個執行緒訪問受限的資源。當執行緒完成工作後，它會歸還許可證，使得其他等待的執行緒可以繼續工作。

  我的認知：反正把執行緒的架構內容準備好之後，用Semaphore可以控制「使用的流量」。

  GPT:Semaphore是一種控制對共享資源併發訪問的工具，適用於需要限制同一時間內多少個執行緒可以訪問特定資源的情況。
  它通過一組許可證來管理訪問，並提供了公平和非公平的模式選擇。
  通過合理地使用 Semaphore，可以有效地防止過多的執行緒同時訪問資源，避免系統過載或資源競爭問題。
 */
package com.example.multiTreadsPractice.udemyCource;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class SemaphoreDemo {

    private static final int NUM_PERMITS = 3;
    private static final Semaphore semaphore = new Semaphore(NUM_PERMITS);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(new Task(i));
        }

        executorService.shutdown();
    }

    static class Task implements Runnable {
        private final int id;

        Task(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire(); // 獲取許可證
                System.out.println("Task " + id + " is working...");
                Thread.sleep(2000); // 模擬工作
                System.out.println("Task " + id + " is done.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                semaphore.release(); // 釋放許可證
            }
        }
    }

}
