package com.pc.juc1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *  定义初始资源桶，用于多线程抢占资源控制
 *  信号量作为一种流控手段，可以对特定资源的允许同时访问的操作数量进行控制，例如池化技术(连接池)中的并发数，有界阻塞容器的容量等
 *  可以将acquire操作视为是消费一个许可，而release操作是创建一个许可，Semaphore并不受限于它在创建时的初始许可数量。
 *  也就是说acquire与release并没有强制的一对一关系，release一次就相当于新增一个许可，许可的数量可能会由于没有与acquire操作
 *  一对一而导致超出初始化时设置的许可个数
 *
 * @author pc
 * @Date 2020/10/18
 **/
public class SemaphoreSimple {

    public static void main(String[] args) {
        //定义三个资源抢占名额
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    //这里的方法不需要传参，默认消耗一个信号量，可以指定消耗几个信号量
                    //未获得将被阻塞
                    semaphore.acquire();
                    System.out.println(finalI + "获得信号量\t");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }

}
