package com.pc.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pc
 * @Date 2021/1/18
 **/
@Data
public class IndexBean implements Serializable {

	@ExcelProperty("指标名称(indexName)")
	private String indexName;

	@ExcelProperty("指标标识(indexKey)")
	private String indexKey;

	@ExcelProperty("指标值(indexValue)")
	private String indexValue;

	@ExcelProperty("最大值(maxValue)")
	private String maxValueT;

	@ExcelProperty("单位(unit)")
	private String unit;

	@ExcelProperty("报表时间(reportDate)")
	private String reportDate;

	@ExcelProperty("展示顺序(orderId)")
	private Integer orderId;

}
