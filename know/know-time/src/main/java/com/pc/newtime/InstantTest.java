package com.pc.newtime;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author pc
 * @Date 2020/9/3
 **/
public class InstantTest {

    public static void main(String[] args) {
        //时刻
        Instant now = Instant.now();
        System.out.println(now.atZone(ZoneId.of("Asia/Shanghai")));

        //instant与date相互转化
        Date from = Date.from(now);
        System.out.println(from);
        //这个时区有问题
        Instant instant = from.toInstant();
        System.out.println(instant);
    }

}
