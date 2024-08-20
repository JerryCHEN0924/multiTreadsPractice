package com.example.multiTreadsPractice;

/**
 * 使用執行緒Thread方法一：
 * 繼承Thread類，並Override run方法
 */


public class Thread1 extends Thread {
	
	public Thread1(String threadName) {
		super(threadName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		for (int i = 0; i <= 5; i++) {
			System.out.printf("inside %s:%s\n",Thread.currentThread().getName(),i);
		}
		
	}

}
