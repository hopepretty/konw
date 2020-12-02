package com.pc.util;

/**
 * 常量sql
 *
 * @author pc
 * @Date 2020/11/26
 **/
public class ConstantSql {

	/**
	 * 获取表字段详情
	 *
	 */
	public static final String TABLE_COLUMN_SQL = "SELECT TABLE_NAME tableName, COLUMN_NAME columnName, COLUMN_DEFAULT columnDefault, COLUMN_TYPE columnType, COLUMN_COMMENT columnComment FROM information_schema.`COLUMNS` " +
			"WHERE TABLE_SCHEMA = 'baseinfo' ORDER BY TABLE_NAME, ORDINAL_POSITION;";

}
