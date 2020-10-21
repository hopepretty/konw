package com.pc.innerclass;

/**
 * 外部类
 *
 * 普通内部类与嵌套类
 * 1、创建嵌套类不需要外部类对象
 * 2、普通内部类对象隐式的保存了一个引用，指向创建它的外部类对象
 * 3、不能从嵌套类的对象中访问非静态的外部类对象
 * 4、普通的内部类不能有static数据与static字段，也不能包含嵌套类，但嵌套类可以包含所有这些东西
 *
 */
public class DotThis {

    public void f() {
        System.out.println("外部类的方法");
    }

    /**
     * 内部类
     */
    public class Inner {
        public DotThis outer() {
            //内部类可以通过外部类.this获取外部类对象
            return DotThis.this;
        }
    }

    /**
     * 嵌套类
     */
    public static class StaticInner {
    }

    public Inner inner() {
        return new Inner();
    }

    public static void main(String[] args) {
        //内部类可以通过外部类.this获取外部类对象
//        DotThis dotThis = new DotThis();
//        Inner inner = dotThis.inner();
//        inner.outer().f();

        /**
         * 内部类不是嵌套类（静态内部类）
         *
         * 在拥有外部类对象之前是不可能创建内部类对象的，这是因为
         * 内部类对象会暗暗的连接到创建它的外部类对象上
         */
        DotThis dotThis = new DotThis();
        Inner inner = dotThis.new Inner();
        System.out.println(inner);

        /**
         * 内部类是嵌套类(嵌套类的定义是如果我们不希望内部类对象与其外部类对象之间有联系)
         */
        DotThis.StaticInner staticInner = new DotThis.StaticInner();
        System.out.println(staticInner);

    }

}
