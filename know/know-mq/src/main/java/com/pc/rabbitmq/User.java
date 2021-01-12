package com.pc.rabbitmq;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 测试用户
 *
 * @author pc
 * @Date 2021/1/12
 **/
public class User implements Serializable {

	private String userName;

	private String address;

	private LocalDate birthday;

	public String getUserName() {
		return userName;
	}

	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public User setAddress(String address) {
		this.address = address;
		return this;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public User setBirthday(LocalDate birthday) {
		this.birthday = birthday;
		return this;
	}

}
