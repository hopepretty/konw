package com.pc.innerclass;

/**
 * 内部类实现多重继承
 */
public class TestInnerClass extends A {

    /**
     * 这里我们通过匿名内部类的形式实现了多重继承
     * @return
     */
    B make() {
        return new B() {};
    }

}
