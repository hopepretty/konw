package com.pc.juc1;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 1 故障现象
 *java.util.ConcurrentModificationException  并发修改异常
 *
 * @author pc
 */
public class SafeCollect {

    public static void main(String[] args) {
        Object s = new Object();
//        1 使用vector() 里面add方法加了synchronized   并发性不行
        List<String> vector = new Vector<>();

//        2 使用Collections.synchronizedList加锁
//        List<String> list = Collections.synchronizedList(new ArrayList<>());

        List<String> list1 = new ArrayList<>();

//        3 使用写时复制
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

        //4 hashSet底层就是一个hashMap  使用CopyOnWriteArraySet实现线程安全
        //但是 CopyOnWriteArraySet底层是 CopyOnWriteArrayList  这个要特别注意了
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new CopyOnWriteArraySet<>();

        //5  map
        Map<String, String> mqp = new HashMap();
        Set<Map.Entry<String, String>> entries = mqp.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
//        iterator.next()
        Map<String, String> map1 = new ConcurrentHashMap<>();
        map1.put("", "");

    }

}
