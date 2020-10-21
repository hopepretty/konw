package com.pc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 解决上面线程安全问题
 * 使用  ThreadLocal
 */
public class DateFormatThreadLocal {

    /**
     * 下面大括号内部实现的是重写此方法
     */
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    /**
     * 转化
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date convert(String source) throws ParseException {
        return df.get().parse(source);
    }

}
