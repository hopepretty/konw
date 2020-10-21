package com.pc.newtime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author pc
 * @Date 2020/9/10
 **/
public class DurationTest {

    public static void main(String[] args) {
        LocalDateTime from = LocalDateTime.of(2017, Month.JANUARY, 5, 10, 7, 0);
        LocalDateTime to = LocalDateTime.of(2017, Month.FEBRUARY, 5, 10, 7, 0);
        Duration duration = Duration.between(from, to);
        System.out.println(duration.toDays());
        //是否是负数
        System.out.println(duration.isNegative());
    }

}
