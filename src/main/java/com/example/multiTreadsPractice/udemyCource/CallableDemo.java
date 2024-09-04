/*
Callable 允許在任務完成後返回一個結果或拋出一個異常。
也是為了補充 Runnable 的不足，因為 Runnable 的 run() 方法不返回結果，也不會拋出受檢異常。
 */
package com.example.multiTreadsPractice.udemyCource;

import java.io.IOException;
import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        //new Callable<欲返回的結果類>，Future<欲返回的結果類>
        Future<Integer> future = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
//                如果要測試拋例外,就把下面打開
//                throw new IOException("測試用，可以拋出例外");
                return 50;
            }
        });
        //上面寫成lambda就是 Future<Integer> future = executorService.submit(() -> 50);

        executorService.shutdown();
        //future.get();會直到執行緒完成，取得結果。 所以我沒有使用executorService.awaitTermination()
        try {
            Integer result = future.get();
            System.out.println("返回的值:" + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            System.out.println(e);
        }
    }
}
