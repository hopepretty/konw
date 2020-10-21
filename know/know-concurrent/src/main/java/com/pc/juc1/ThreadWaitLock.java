package com.pc.juc1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 现在两个线程，操作初始值为零的一个变量
 * 实现一个线程对该变量加1。 一个线程对该变量减一
 * 实现交替，来10轮，变量初始值为0
 *
 *  1、高内聚低耦合 线程 操作  资源类
 *  2、判断/干活/通知
 *  3、多线程交互中，必须要防止多线程的虚假唤醒 (1、不能使用if，使用while)
 * @author pc
 * @Date 2020/10/11
 **/
public class ThreadWaitLock {

    public static void main(String[] args) {
//        NewAirConditioner newAirConditioner = new NewAirConditioner();

        AirConditioner airConditioner = new AirConditioner();
        for (int i = 0; i < 20; i++) {
            new Thread(airConditioner::incr).start();
            new Thread(airConditioner::decr).start();
        }
    }

}

class NewAirConditioner {
    private int number = 0;

    /**
     * 使用lock替换synchronized
     */
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void incr() {
        lock.lock();
        try {
            //1、判断  注意这里不能使用if，防止虚假唤醒
            while (number != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //2、干活
            number ++;
            System.out.println(Thread.currentThread().getName() + "==" + number);
            //3、通知，可以取货了
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void decr() {
        lock.lock();
        try {
            //1、判断  注意这里不能使用if，防止虚假唤醒
            while (number != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //2、干活
            number --;
            System.out.println(Thread.currentThread().getName() + "==" + number);
            //3、通知，缺货了
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 资源类
 */
class AirConditioner {

    private int number = 0;

    /**
     * 这里要加锁，
     * 1、第一种情况因为调用wait或者notify方法必须要释放锁，所以与java.lang.IllegalMonitorStateException异常有关，状态不对，事先未获取锁
     * 2、保证时间同步，避免先调用了notify方法
     */
    public synchronized void incr() {
        //1、判断  注意这里不能使用if，防止虚假唤醒
        while (number != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //2、干活
        number ++;
        System.out.println(Thread.currentThread().getName() + "==" + number);
        //3、通知，可以取货了
        this.notifyAll();
    }

    public synchronized void decr() {
        //1、判断  注意这里不能使用if，防止虚假唤醒
        while (number != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //2、干活
        number --;
        System.out.println(Thread.currentThread().getName() + "==" + number);
        //3、通知，缺货了
        this.notifyAll();
    }

}
