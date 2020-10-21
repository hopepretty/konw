package com.pc.util.report;

/**
 * 基础报表类
 * @author pc
 * @Date 2020/10/16
 **/
public class CommonReport {

    /**
     * 报表小时
     */
    private String reportHour;

    /**
     * 报表日期
     */
    private String reportDate;

    /**
     * 报表月份
     */
    private String reportMonth;

    /**
     * 报表值
     */
    private String value = "0";

    public String getReportHour() {
        return reportHour;
    }

    public CommonReport setReportHour(String reportHour) {
        this.reportHour = reportHour;
        return this;
    }

    public String getReportDate() {
        return reportDate;
    }

    public CommonReport setReportDate(String reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public CommonReport setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
        return this;
    }

    public String getValue() {
        return value;
    }

    public CommonReport setValue(String value) {
        this.value = value;
        return this;
    }
}
