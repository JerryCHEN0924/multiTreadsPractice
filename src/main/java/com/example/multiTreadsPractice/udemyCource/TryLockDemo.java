/*
  介紹ReentrantLock的TryLock
 */
package com.example.multiTreadsPractice.udemyCource;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {

    //範例用的Account類別
    class Account {
        private int balance = 10000;

        public void deposit(int amount) {
            balance += amount;
        }

        public void withdraw(int amount) {
            balance -= amount;
        }

        public int getBalance() {
            return balance;
        }

        public static void transfer(Account account1, Account account2, int amount) {
            account1.withdraw(amount);
            account2.deposit(amount);
        }
    }

    private Account account1 = new Account();
    private Account account2 = new Account();
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        while (true) {
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock) {
                    return;
                }
                if(gotFirstLock){
                    firstLock.unlock();
                }
                if (gotSecondLock){
                    secondLock.unlock();
                }
            }
            Thread.sleep(1);
        }
    }

    private void firstThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            /*
            原本我們會使用
            lock1.lock();
            lock2.lock();
            來上鎖，而現在避免因為鎖的順序造成死鎖問題，改使用acquireLocks(lock1,lock2);
             */
            acquireLocks(lock2, lock1);
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    private void secondThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            /*
            關鍵在於，如果上鎖的順序不同，例如
            lock2.lock();
            lock1.lock();
            這樣就會造成死鎖，因為彼此都在等待另一個鎖的釋放。
            所以可以使用try lock()建立一個方法「如果我沒有同時拿掉兩個鎖，那我就把鎖都放掉。」;
            也就是acquireLocks(lock1,lock2);
             */
            acquireLocks(lock1, lock2);
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }

    }

    private void finished() {
        System.out.println("Account1 balance:" + account1.getBalance());
        System.out.println("Account1 balance:" + account2.getBalance());
        System.out.println("Total balance:" + (account1.getBalance() + account2.getBalance()));
    }


    public static void main(String[] args) throws InterruptedException {
        TryLockDemo tryLockDemo = new TryLockDemo(); // 使用同一個 Processor 對象
        // 建立執行緒t1並負責做reentrantLockDemo.firstThread();
        Thread t1 = new Thread(() -> {
            try {
                tryLockDemo.firstThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 建立執行緒t2並負責做reentrantLockDemo.secondThread();
        Thread t2 = new Thread(() -> {
            try {
                tryLockDemo.secondThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        //記得！ join的作用是告知main這個主執行緒，「在t1,t2還沒執行完畢前，不要結束！」
        t1.join();
        t2.join();

        tryLockDemo.finished();
    }

}
