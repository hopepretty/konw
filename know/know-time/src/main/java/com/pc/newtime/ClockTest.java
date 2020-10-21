package com.pc.newtime;

import java.time.Clock;

/**
 * @author pc
 * @Date 2020/9/10
 **/
public class ClockTest {

    public static void main(String[] args) {
        //时钟使用
        //世界标准时区对应的瞬时时间
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
    }

}
