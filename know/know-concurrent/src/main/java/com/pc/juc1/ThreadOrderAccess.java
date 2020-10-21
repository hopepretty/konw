package com.pc.juc1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 *
 * AA打印5次  BB打印10次  CC打印15次
 * 接着
 * AA打印5次  BB打印10次  CC打印15次
 * 重复10次
 *
 * 1、高内聚低耦合 线程 操作  资源类
 * 2、判断/干活/通知
 * 3、多线程交互中，必须要防止多线程的虚假唤醒 (1、不能使用if，使用while)
 * 4、标志位
 *
 * @author pc
 * @Date 2020/10/11
 **/
public class ThreadOrderAccess {

    /**
     * 资源锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 等待与唤醒条件
     */
    private Condition condition1 = lock.newCondition();

    private Condition condition2 = lock.newCondition();

    private Condition condition3 = lock.newCondition();

    /**
     * 状态位
     */
    private int number = 1;

    private void print5() {
        lock.lock();
        try {
            //1 判断
            while (number != 1) {
                condition1.await();
            }
            //2 打印
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void print10() {
        lock.lock();
        try {
            //1 判断
            while (number != 2) {
                condition2.await();
            }
            //2 打印
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void print15() {
        lock.lock();
        try {
            //1 判断
            while (number != 3) {
                condition3.await();
            }
            //2 打印
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        //创建一个资源类
        ThreadOrderAccess threadOrderAccess = new ThreadOrderAccess();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                threadOrderAccess.print5();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                threadOrderAccess.print10();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                threadOrderAccess.print15();
            }
        }).start();
    }

}
