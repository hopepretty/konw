package com.pc;

import com.alibaba.excel.EasyExcel;
import com.pc.bean.IndexBean;
import com.pc.util.DbClient;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 写测试
 *
 * @author pc
 * @Date 2021/1/18
 **/
public class WriteTest {

	/**
	 * 最简单的写
	 */
	@Test
	public void simpleWrite() {
		String fileName = "D:\\驾驶舱数据.xlsx";
		// 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		EasyExcel.write(fileName, IndexBean.class).sheet("指标数据").doWrite(data());
	}

	private List<IndexBean> data() {
		List<IndexBean> indexBeans = new ArrayList<>();
		QueryRunner queryRunner = new QueryRunner();
		DbClient dbClient = new DbClient("jdbc:mysql://21.15.18.97:3306/baseinfo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true",
				"root", "supconit");
		dbClient.connect();
		Connection conn = dbClient.getConn();
		try {
			indexBeans = (List<IndexBean>) queryRunner.query(conn, "SELECT\n" +
					"\tindex_name indexName,\n" +
					"\tindex_key indexKey,\n" +
					"\tmax_value maxValueT,\n" +
					"\tindex_value indexValue,\n" +
					"\torder_id orderId,\n" +
					"\tunit,\n" +
					"\treport_date reportDate \n" +
					"FROM\n" +
					"\topt_cockpit_index \n" +
					"WHERE\n" +
					"\tis_deleted = 0", new BeanListHandler(IndexBean.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return indexBeans;
	}

}
