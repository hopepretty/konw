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
		DbClient dbClient = new DbClient("jdbc:mysql://21.15.18.97:3306/baseinfo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true",
				"root", "supconit");
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
//		List<Map<String, Object>> result = queryRunner.query(conn, "select * from rn_rmm_infr_capcon_bas_tunnel", new MapListHandler());
//		for (Map<String, Object> map : result) {
//			int id = NumberUtil.getInt(map.get("id"));
//			double lng = NumberUtil.getDouble(map.get("lng"));
//			double lat = NumberUtil.getDouble(map.get("lat"));
//			double[] doubles = GPSUtil.gps84_To_Gcj02(lat, lng);
//			queryRunner.update(conn, "update rn_rmm_infr_capcon_bas_tunnel set mars_lng=" + doubles[1] + ", mars_lat=" + doubles[0] + " where id='" + id + "'");
//		}

		List<Map<String, Object>> result = queryRunner.query(conn, "SELECT\n" +
				"        sta_name,\n" +
				"        ROUND(IFNULL( sum( entry_flow )*1.48, 0 )) entry_flow,\n" +
				"        ROUND(IFNULL( sum( exit_flow )*1.48, 0 )) exit_flow\n" +
				"        FROM\n" +
				"        sub_bas_station_flow\n" +
				"        WHERE\n" +
				"        zk_create_time > DATE_FORMAT( NOW(), '%Y-%m-%d 00:00:00' )\n" +
				"        AND is_deleted = 0\n" +
				"        GROUP BY\n" +
				"        sta_name", new MapListHandler());
		String indexExTemplate = "SUB_%s_EX";
		String indexEnTemplate = "SUB_%s_EN";
		for (Map<String, Object> map : result) {
			String name = String.valueOf(map.get("sta_name"));
			String exName = name + "出站";
			String enName = name + "入站";
			String unit = "人次";
			Object[] exParams = {String.format(indexExTemplate,
					PinyinUtil.getHeadByString(name.substring(0, name.length() -1), true, null)), exName, unit};
			Object[] enParams = {String.format(indexEnTemplate,
					PinyinUtil.getHeadByString(name.substring(0, name.length() -1), true, null)), enName, unit};
			try {
				queryRunner.update(conn,
						"INSERT INTO opt_screen_index_realtime (index_key, index_name, unit) VALUES (?, ?, ?)", exParams);
				queryRunner.update(conn,
						"INSERT INTO opt_screen_index_realtime (index_key, index_name, unit) VALUES (?, ?, ?)", enParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
