package com.pc.util.report;

import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 报表相关数据处理
 * @author pc
 * @Date 2020/10/16
 **/
public class ReportUtil {

    /**
     * 每小时
     */
    private static List<String> hourDic = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");

    /**
     * 月份每天
     */
    private static List<String> dayDic = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");

    /**
     * 年份每月
     */
    private static List<String> monthDic = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

    /**
     * 填充缺失小时
     *  填充值为 0
     * @param data
     */
    public static List<CommonReport> paddingMissingHourData(List<CommonReport> data) {
        List<CommonReport> reportHour = handleData(data, "reportHour", hourDic, 0);
        reportHour.sort(Comparator.comparing(CommonReport::getReportHour));
        return reportHour;
    }

    /**
     * 填充缺失日期
     *  填充值为 0
     * @param data
     */
    public static List<CommonReport> paddingMissingDayData(List<CommonReport> data) {
        List<CommonReport> reportDate = handleData(data, "reportDate", dayDic, getCurrentMonthDay());
        reportDate.sort(Comparator.comparing(CommonReport::getReportDate));
        return reportDate;
    }

    /**
     * 填充缺失月份
     *  填充值为 0
     * @param data
     */
    public static List<CommonReport> paddingMissingMonthData(List<CommonReport> data) {
        List<CommonReport> reportMonth = handleData(data, "reportMonth", monthDic, 0);
        reportMonth.sort(Comparator.comparing(CommonReport::getReportMonth));
        return reportMonth;
    }

    /**
     * 同意处理数据
     * @param data
     * @param field
     * @param dic
     * @return
     */
    private static List<CommonReport> handleData(List<CommonReport> data, String field, List<String> dic, int max) {
        if (CollectionUtils.isNotEmpty(data)) {
            List<CommonReport> missingData = new ArrayList<>();
            if (max > 0) {
                dic = dic.subList(0, max - 1);
            }
            try {
                for (String s : dic) {
                    for (int i = 0; i < data.size(); i++) {
                        CommonReport baseReport = data.get(i);
                        Field declaredField = CommonReport.class.getDeclaredField(field);
                        declaredField.setAccessible(true);
                        String value = (String) declaredField.get(baseReport);
                        if (s.equals(value)) {
                            break;
                        } else if (i == data.size() - 1) {
                            CommonReport commonReport = new CommonReport();
                            declaredField.set(commonReport, s);
                            missingData.add(commonReport);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            missingData.addAll(data);
            return missingData;
        }
        return null;
    }

    /**
     * 获取当前月份天数
     * @return
     */
    private static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    /**
     * 根据每月天数截取数据
     * @param source
     * @param max
     * @return
     */
    private static List<String> subSource(List<String> source, Integer max) {
        return dayDic.subList(0, max - 1);
    }

}
