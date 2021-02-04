package com.pc.util.test;

import com.pc.util.DbClient;
import com.pc.util.GPSUtil;
import com.pc.util.NumberUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author pc
 * @Date 2021/1/19
 **/
public class ProjectGps {

	public static void main(String[] args) throws SQLException {
		List<GpsBean> gpsBeans = new ArrayList<>();
		QueryRunner queryRunner = new QueryRunner();
		DbClient dbClient = new DbClient("jdbc:mysql://21.15.18.97:3306/baseinfo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true",
				"root", "supconit");
		dbClient.connect();
		Connection conn = dbClient.getConn();
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
