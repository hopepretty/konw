package com.pc.newtime;

import java.time.LocalTime;

/**
 * @author pc
 * @Date 2020/9/10
 **/
public class LocalTimeTest {

    public static void main(String[] args) {
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        //构造时间
        System.out.println(LocalTime.of(3, 14));
        //解析相关时间
        System.out.println(LocalTime.parse("15:33:56"));
    }

}
