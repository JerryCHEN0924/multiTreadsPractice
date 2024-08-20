package com.example.multiTreadsPractice;

public class Runnable1 implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.printf("%s running,index:%s\n",Thread.currentThread().getName(),i);
		}

	}

}
