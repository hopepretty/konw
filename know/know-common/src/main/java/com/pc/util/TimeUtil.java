package com.pc.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间操作工具类
 *
 * @author pc
 * @Date 2020/11/10
 **/
public class TimeUtil {

    /**
     * 获取时间区间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private static List<String> getDateSection(Date startTime, Date endTime, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        List<String> betweenTime = new ArrayList();
        LocalDateTime start = LocalDateTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault());

        betweenTime.add(df.format(start));
        while (start.isBefore(end)) {
            start = start.plusDays(1);
            betweenTime.add(df.format(start));
        }
        return betweenTime;
    }

}
