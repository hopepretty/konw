package com.hanweb.optional;

import org.junit.Test;

import java.util.Optional;

/**
 * 测试
 */
public class OptionalTest {

    /**
     *
     * Optional它是一个容器
     * Optional.of(T t)  创建一个容器Optional实例
     * Optional.enpty() 创建一个空的Optional实例
     * Optional.ofNUllable(T t) 若t不为null，创建Optional实例，否则创建空实例
     * isPresent()  判断是否包含值
     * orElse(T t) 如果调用对象包含之，返回该值，否则返回预先指定的
     * orElseGet(Supplier s)   如果调用对象包含值，返回该值，否则返回 s 获取的值
     * map(Function f) 如果有值对其处理，并返回处理后的optional，反则返回Optional.enpty()
     * flatmap(Function map)  与nap类似，要求返回值必须是Optional
     *
     */
    @Test
    public void test() {
        Optional<Emplyee> emplyee = Optional.of(new Emplyee());

        Emplyee emplyee1 = emplyee.get();
        System.out.println(emplyee1);
    }

    class Emplyee {
        private String name;
    }

}
