package com.example.demo.security;

import com.example.demo.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份验证过滤器
 *
 * @author pc
 * @Date 2020/11/16
 **/
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	@Autowired
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	/**
	 *
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 获取token, 并检查登录状态，从token中获取用户信息组装成用户信息对象放入上下文环境
		SecurityUtils.checkAuthentication(request);
		chain.doFilter(request, response);
	}

}
