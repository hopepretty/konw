package com.pc.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * 数字工具类
 * @author pc
 * @Date 2020/9/7
 **/
public class NumberUtil {

    /**
     * object转float
     *
     * @param obj
     * @return float
     */
    public static float getFloat(Object obj) {
        return NumberUtils.toFloat(ObjectUtils.toString(obj));
    }

    /**
     * object转long
     *
     * @param obj
     * @return
     */
    public static long getLong(Object obj) {
        return NumberUtils.toLong(ObjectUtils.toString(obj));
    }

    /**
     * object转double
     *
     * @param obj
     * @return
     */
    public static double getDouble(Object obj) {
        return NumberUtils.toDouble(ObjectUtils.toString(obj));
    }

    /**
     * object转int
     *
     * @param obj
     * @return
     */
    public static int getInt(Object obj) {
        String numStr = ObjectUtils.toString(obj);
        if (numStr.contains(".")) {
            numStr = numStr.split("\\.")[0];
        }
        return NumberUtils.toInt(numStr);
    }

    /**
     * object转float
     *
     * @param obj
     * @param defaultValue
     *            默认值
     * @return
     */
    public static float getFloat(Object obj, float defaultValue) {
        return NumberUtils.toFloat(ObjectUtils.toString(obj), defaultValue);
    }

    /**
     * object转long
     *
     * @param obj
     * @param defaultValue
     *            默认值
     * @return
     */
    public static long getLong(Object obj, long defaultValue) {
        return NumberUtils.toLong(ObjectUtils.toString(obj), defaultValue);
    }

    /**
     * object转double
     *
     * @param obj
     * @param defaultValue
     *            默认值
     * @return
     */
    public static double getDouble(Object obj, double defaultValue) {
        return NumberUtils.toDouble(ObjectUtils.toString(obj), defaultValue);
    }

    /**
     * object转int
     *
     * @param obj
     * @param defaultValue
     *            默认值
     * @return
     */
    public static int getInt(Object obj, int defaultValue) {
        return NumberUtils.toInt(ObjectUtils.toString(obj), defaultValue);
    }

    /**
     * 判断一个字符串是否是数字
     *
     * @param string
     *            源字符串
     * @return
     */
    public static boolean isNumeric(String string) {
        return NumberUtils.isNumber(string);
    }

    /**
     * 判断一个字符串是否是数字
     *
     * @param string
     *            源字符串
     * @return
     */
    public static boolean isNotNumeric(String string) {
        return !NumberUtils.isNumber(string);
    }

    /**
     * 是否相等
     *
     * @param number
     * @param number2
     * @return
     */
    public static boolean equals(Number number, Number number2) {
        if (number != null && number2 != null) {
            if (number.doubleValue() == number2.doubleValue()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 是否大于
     *
     * @param number
     * @param number2
     * @return
     */
    public static boolean great(Number number, Number number2) {
        if (number != null && number2 != null) {
            if (number.doubleValue() > number2.doubleValue()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 是否小于
     *
     * @param number
     * @param number2
     * @return
     */
    public static boolean less(Number number, Number number2) {
        if (number != null && number2 != null) {
            if (number.doubleValue() < number2.doubleValue()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 格式化double值
     * @param value
     * @param scale
     * @return
     */
    public static double formatDouble(double value, int scale) {
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
