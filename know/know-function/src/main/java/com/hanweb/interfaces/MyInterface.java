package com.hanweb.interfaces;

/**
 * 接口
 */
public interface MyInterface {

    String TTT = "你好";

    void test();

    default void test2() {
        System.out.println("这是默认实现");
    }

    static void test3() {
        System.out.println("这是一个静态方法");
    }

}
