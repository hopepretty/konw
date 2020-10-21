package com.pc.newtime;

import java.time.MonthDay;
import java.time.YearMonth;

/**
 * @author pc
 * @Date 2020/9/10
 **/
public class YearMonthDayTest {

    public static void main(String[] args) {
        //获取当前年月时间，并且获取今年有多少天，可以依据天数判断今年的二月是否是闰二月，是平年还是闰年
        YearMonth yearMonth = YearMonth.now();
        System.out.println(yearMonth + "=" + yearMonth.lengthOfYear());
        //获取月日时间
        MonthDay monthDay = MonthDay.now();
        System.out.println(monthDay);
    }

}
