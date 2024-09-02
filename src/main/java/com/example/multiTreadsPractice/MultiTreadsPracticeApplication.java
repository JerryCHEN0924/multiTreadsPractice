package com.example.multiTreadsPractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiTreadsPracticeApplication {

	public static void main(String[] args) {
		System.out.println("main starting");
		SpringApplication.run(MultiTreadsPracticeApplication.class, args);
		
		/*
		 * 方法一：將繼承Thread的Thread1實例化
		 * 
		 */
		Thread1 thread1 = new Thread1("thread1執行緒1"); //將做好的Thread1實例化，傳入的參數只是執行緒名稱
//		thread1.setDaemon(true); //像這樣就是把這條thread1設置成Daemon thread。
		thread1.start(); //注意，這邊不使用thread1.run()，是因為這樣並無法啟動執行緒，只是調用thread1的run方法而已。
		
		/*
		 * 方法二：實作Runnable介面
		 * 
		 */
		Thread thread2 = new Thread(new Runnable1(),"Runnable1實作Runnable介面");//將Runnable1作為參數傳入Thread中建立。
		thread2.start();
		
		//方法2-1:lambda作法
		Runnable myrunnable = () -> System.out.println("lambda寫法");
		Thread thread3 = new Thread(myrunnable,"myrunnable lambda作法");
		/*
		 * 所以其實又可以寫成
		 * Thread thread4 = new Thread(() -> System.out.println("lambda寫法2"),"myrunnable");
		 */
		thread3.start();
		try {
			/*
			 * 在這邊使用thread3.join()，表示此子執行緒thread3完成前，主執行緒(在此也就是main)會等待。
			 */
			thread3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("主程序最後才完成");
		
		//死鎖Dead lock，兩個執行緒都在等待彼此釋放鎖的情況就是死鎖
		String lock1 = "lock1";
		String lock2 = "lock2";
		
		Thread thread5 = new Thread( () -> {
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
		},"thread5");
		
		Thread thread6 = new Thread( () -> {
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
		},"thread6");
	}

}
