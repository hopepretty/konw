package com.pc.newtime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author pc
 * @Date 2020/9/10
 **/
public class LocalDateTest {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        //构造日期
        System.out.println(LocalDate.of(1996, 3, 14));
        //加一天
        System.out.println(localDate.plusDays(2));
        //下一周
        localDate.plus(1, ChronoUnit.WEEKS);
        //去年
        localDate.minus(1, ChronoUnit.YEARS);
        //比较时间
        LocalDate ld2 = LocalDate.now();
        ld2.isBefore(localDate);

        //判断是否是平年
        localDate.isLeapYear();

        //字符串转时间格式化
        String dayFormat = "20200910";
        LocalDate parse = LocalDate.parse(dayFormat, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(parse);
        //自定义字符串转时间格式化
        String df = "2020 09 10";
        DateTimeFormatter yyyy_mm_dd = DateTimeFormatter.ofPattern("yyyy MM dd");
        LocalDate parse1 = LocalDate.parse(df, yyyy_mm_dd);
        System.out.println(parse1);

        //时间转字符串
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm:ss");
        String format = ldt.format(dateTimeFormatter);
        System.out.println(format);
    }

}
