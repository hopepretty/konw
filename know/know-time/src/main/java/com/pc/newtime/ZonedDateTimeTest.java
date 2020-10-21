package com.pc.newtime;

import java.time.*;

/**
 * @author pc
 * @Date 2020/9/10
 **/
public class ZonedDateTimeTest {

    public static void main(String[] args) {
        // 获取当前时间日期
        //ZonedDateTime代表带日期时间时区的对象
        ZonedDateTime date1 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
        System.out.println("date1: " + date1);

        ZoneId id = ZoneId.of("Europe/Paris");
        System.out.println("ZoneId: " + id);

        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("当期时区: " + currentZone);

        //将时间带上时区
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        ZoneOffset of = ZoneOffset.of("+08:00");
        OffsetDateTime of1 = OffsetDateTime.of(localDateTime, of);
        System.out.println(of1);
    }

}
