package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Set;

/**
 * 用户业务类
 *
 * @author pc
 * @Date 2020/11/16
 **/
public interface UserService {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * 查找用户的菜单权限标识集合
	 * @param username
	 * @return
	 */
	Set<String> findPermissions(String username);

}
