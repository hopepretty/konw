package com.pc.newtime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author pc
 * @Date 2020/9/10
 **/
public class LocalDateTimeTest {

    public static void main(String[] args) {
        //本地时间，不受时区限制 （也可以定义时区 通过ZoneId）
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        //当前月份中的天数
        System.out.println(localDateTime.getDayOfMonth());
        //当前年份中的天数
        System.out.println(localDateTime.getDayOfYear());
        //日期转化
        System.out.println(localDateTime.toLocalDate());
        //构造相应时间
        System.out.println(localDateTime.withYear(1996).withDayOfMonth(14).withMonth(3));

        //获取当天或者某天的起止时间字符串
        String beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(beginDate);
        System.out.println(endDate);
    }

}
