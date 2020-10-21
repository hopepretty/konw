package com.pc.juc1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * jvm进程级别的读写锁
 *
 * @author pc
 * @Date 2020/10/18
 **/
public class ReadWriteLockSimple {

    /**
     *缓存
     */
    private Map<String, String> map = new HashMap<>();

    /**
     * 读写锁
     */
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, String value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(key + "开始写入数据");
            map.put(key, value);
            TimeUnit.SECONDS.sleep(2);
            System.out.println(key + "写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(key + "开始读数据");
            System.out.println(map.get(key));
            TimeUnit.SECONDS.sleep(2);
            System.out.println(key + "读完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockSimple readWriteLockSimple = new ReadWriteLockSimple();
        //读
        for (int i = 0; i < 5; i++) {
            int finalI1 = i;
            new Thread(() -> {
                readWriteLockSimple.get(String.valueOf(finalI1));
            }).start();
        }

        //写
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                readWriteLockSimple.put(String.valueOf(finalI), "2");
            }).start();
        }

        //1、在写的时候，不允许读与写,即上了写锁，则读是不允许的
        //2、在读的时候，不允许写，允许读，即先上了读锁，允许读，不允许写
    }

}
