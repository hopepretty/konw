package com.pc.util;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库操作工具类
 *
 * @author pc
 * @Date 2020/11/26
 **/
public class DbClient {

	/**
	 * 数据库操作对象
	 *
	 */
	private QueryRunner queryRunner = new QueryRunner();

	/**
	 * 驱动
	 *
	 */
	private String driveClassName = "com.mysql.cj.jdbc.Driver";

	/**
	 * 数据库连接地址
	 *
	 */
	private String url;

	/**
	 * 用户名
	 *
	 */
	private String user;

	/**
	 * 密码
	 *
	 */
	private String password;

	/**
	 *
	 * 当前连接对象
	 */
	private Connection conn = null;

	public DbClient(String driveClassName, String url, String user, String password) {
		this.driveClassName = driveClassName;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public DbClient(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	/**
	 * 初始化连接
	 *
	 */
	public void connect() {
		try {
			Class.forName(driveClassName);
		} catch (ClassNotFoundException e) {
			System.out.println("load driver failed!");
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("connect failed!");
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getDriveClassName() {
		return driveClassName;
	}

	public void setDriveClassName(String driveClassName) {
		this.driveClassName = driveClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
