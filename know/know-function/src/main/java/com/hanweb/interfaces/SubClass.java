package com.hanweb.interfaces;

/**
 *
 */
public class SubClass /**extends SuperClass*/ implements MyInterface, MyInterface1{
    @Override
    public void test() {

    }

    @Override
    public void test2() {
//        MyInterface.super.test2();
        MyInterface1.super.test2();
    }

    /**
     * 注意了，父类与父接口有重名的方法，以类优先原则
     */
//    @Override
//    public void test2() {
//        super.test2();
//    }

}
