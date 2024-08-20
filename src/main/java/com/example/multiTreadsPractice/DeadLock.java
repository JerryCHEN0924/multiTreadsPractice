package com.example.multiTreadsPractice;

public class DeadLock {
	/*
	 * 注意到這個程式啟動後永遠不會中止，因為已經發生死鎖，兩個執行緒都在等待對方釋放。
	 * 但其實，實務上發生的DeadLock較難去發現原因，不然設計時就會避開了...。
	 */
	public static void main(String[] args) {
		System.out.println("main 啟動");
		// 死鎖Dead loock
		String lock1 = "lock1";
		String lock2 = "lock2";

		Thread thread1 = new Thread(() -> {
			synchronized (lock1) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (lock2) {
					System.out.println("lock acquired");
				}
			}
		}, "thread5");

		Thread thread2 = new Thread(() -> {
			//這邊故意先使用lock2造成死鎖
			synchronized (lock2) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (lock1) {
					System.out.println("lock acquired");
				}
			}
		}, "thread6");

		thread1.start();
		thread2.start();
		System.out.println("main 結束");
	}
}
