package com.example.demo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 自定义用户身份认证器
 *
 * @author pc
 * @Date 2020/11/16
 **/
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

	public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
		//塞入具体获取用户信息对象
		setUserDetailsService(userDetailsService);
		//设置密码组件，使用什么方式比对密码,注意  这里没有默认使用 BCryptPasswordEncoder,因为我们这边是自定义实现
		setPasswordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//在此处自定义认证逻辑,
		System.out.println("进行身份认证");
		//我们如果自定义实现认证身份逻辑,则不需要调用父类的authenticate方法,直接返回authentication会话信息即可
//		return authentication;
		return super.authenticate(authentication);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		//在此处自定义密码验证逻辑
		super.additionalAuthenticationChecks(userDetails, authentication);
	}
}
