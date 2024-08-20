package com.example.multiTreadsPractice;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {
	private Queue<Integer> queue;

	private int capacity;

	public BlockingQueue(int capacity) {
		queue = new LinkedList<>();
		capacity = this.capacity;
	}

	public boolean add(int item) {
		synchronized (queue) {
			/*
			 * 這邊也要注意新人常犯的錯是使用if條件判斷。
			 * if是一次性檢查，while是循環結構。
			 * 所以當執行緒被喚醒要做事前，while迴圈會重新檢查條件，成立才繼續做事。
			 * if則是因為已經通過檢查了，被喚醒後就直接往下繼續做事，這時就繞過了檢查的條件，造成race condition。
			 */
			while (queue.size() == capacity) {
				/*
				 * 如果這個queue內已經滿了，則queue.wait()讓鎖釋放， 其他執行緒才可以使用remove()移除東西，否則就會一直被占著鎖。
				 */
				try {
					queue.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			queue.add(item);
			/*
			 * 當有放值進來,這個queue不為空，使用notifyAll()告知所有執行緒起床做事。
			 */
			queue.notifyAll();
			return true;
		}
	}

	public int remove() {
		synchronized (queue) {
			while (queue.size() == 0) {
				/*
				 * 如果初始這個queue內沒東西，size為0，則queue.wait()讓鎖釋放， 其他執行緒才可以使用add()加入東西，否則就會一直被占著鎖。
				 */
				try {
					queue.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int element = queue.poll();
			queue.notifyAll();
			return element;
		}
	}

}
