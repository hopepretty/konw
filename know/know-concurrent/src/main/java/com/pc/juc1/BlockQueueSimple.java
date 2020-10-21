package com.pc.juc1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 *
 * 内部使用 ReentrantLock lock = this.lock;进行加锁的
 *
 * @author pc
 * @Date 2020/10/18
 **/
public class BlockQueueSimple {

    public static void main(String[] args) throws InterruptedException {
        //1、数组结构的阻塞队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);
        //add溢出会抛出异常
        blockingQueue.add("5");
        blockingQueue.add("5");
        blockingQueue.add("5");
        blockingQueue.add("5");
        blockingQueue.add("5");
        //移除获取先进的元素
        blockingQueue.remove();
        //检查
        blockingQueue.element();

        //不抛出异常，返回true或者false
        blockingQueue.offer("ccc");
        //移除获取
        System.out.println(blockingQueue.poll());
        //检查
        System.out.println(blockingQueue.peek());

        //阻塞  一直阻塞
        //添加
        blockingQueue.put("ccc");
        //移除获取
        System.out.println(blockingQueue.take());

        //阻塞超时
        //添加
        blockingQueue.offer("vv", 2, TimeUnit.SECONDS);
        //移除获取
        blockingQueue.poll(2, TimeUnit.SECONDS);
    }

}
