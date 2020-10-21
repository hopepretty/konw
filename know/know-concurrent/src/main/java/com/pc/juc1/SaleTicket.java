package com.pc.juc1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *企业级售票
 *
 * 线程  操作（对外暴露的方法）  资源类
 * @author pc
 */
public class SaleTicket {

    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "C").start();
    }

}

//资源类
class Ticket {

    //票数
    private int number = 30;

    //定义一个可重入锁
    private Lock lock = new ReentrantLock();

    //操作
    public void sale() {
        //上锁
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number --) + "\t还剩下：" + number);
            }
        } finally {
            lock.unlock();//释放锁
        }
    }

}
