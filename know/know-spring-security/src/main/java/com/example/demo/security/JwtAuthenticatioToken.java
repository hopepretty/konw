package com.example.demo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * jwt认证token，身份信息
 *
 * @author pc
 * @Date 2020/11/16
 **/
public class JwtAuthenticatioToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private String token;

	/**
	 * 塞入凭证信息
	 *
	 * @param principal  用户名
	 * @param credentials  密码
	 */
	public JwtAuthenticatioToken(Object principal, Object credentials){
		super(principal, credentials);
	}

	public JwtAuthenticatioToken(Object principal, Object credentials, String token){
		super(principal, credentials);
		this.token = token;
	}

	/**
	 *
	 *
	 * @param principal
	 * @param credentials
	 * @param authorities  权限集合
	 * @param token
	 */
	public JwtAuthenticatioToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token) {
		super(principal, credentials, authorities);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
