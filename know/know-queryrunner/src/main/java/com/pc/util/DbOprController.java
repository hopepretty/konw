package com.pc.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作类
 *
 * @author pc
 * @Date 2020/11/26
 **/
public class DbOprController {

	public static void main(String[] args) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		DbClient dbClient = new DbClient("jdbc:mysql://10.75.25.253:30906/baseinfo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true",
				"baseinfo", "Eyd6H7muFZ");
		dbClient.connect();
		Connection conn = dbClient.getConn();
//		List<TableColumnBean> data = queryRunner.query(conn, ConstantSql.TABLE_COLUMN_SQL, new BeanListHandler<>(TableColumnBean.class));

//		String content = ClassPathUtil.getContentAsString("/db_doc.xml");
//
//		Map<String, Object> params = new HashMap<>();
//		params.put("tables", data);
//		params.put("dbType", 1);
//		String result = TemplateParser.parserTemplate(content, params);
//		System.out.println(result);
		List<Map<String, Object>> result = queryRunner.query(conn, "select * from rn_rmm_infr_capcon_bas_tunnel", new MapListHandler());
		for (Map<String, Object> map : result) {
			int id = NumberUtil.getInt(map.get("id"));
			double lng = NumberUtil.getDouble(map.get("lng"));
			double lat = NumberUtil.getDouble(map.get("lat"));
			double[] doubles = GPSUtil.gps84_To_Gcj02(lat, lng);
			queryRunner.update(conn, "update rn_rmm_infr_capcon_bas_tunnel set mars_lng=" + doubles[1] + ", mars_lat=" + doubles[0] + " where id='" + id + "'");
		}

	}

}
