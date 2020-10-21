package com.pc;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pc
 * @Date 2020/9/5
 **/
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1471870569842037426L;

    private Integer id;

    /**
     * 申请人名称
     */
    private String holidayName;

    private Date beginDate;

    private Date endDate;

    /**
     * 请假天数
     */
    private Float num;

    /**
     * 原因
     */
    private String reason;

    /**
     * 请假类型
     */
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Float getNum() {
        return num;
    }

    public void setNum(Float num) {
        this.num = num;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
