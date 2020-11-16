package com.example.demo.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.HttpUtils;
import com.example.demo.util.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 自定义jwt认证过滤器
 *
 * 通过过滤器的方式认证登录信息
 *
 * @author pc
 * @Date 2020/11/16
 **/
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * 配置
	 *
	 * @param authenticationManager
	 */
	public JwtLoginFilter(AuthenticationManager authenticationManager) {
		setAuthenticationManager(authenticationManager);
	}

	/**
	 * 登录认证过滤链执行
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// POST 请求 /login 登录时拦截， 由此方法触发执行登录认证流程，可以在此覆写整个登录认证逻辑
		super.doFilter(request, response, chain);
	}

	/**
	 * 认证
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		// 可以在此覆写尝试进行登录认证的逻辑，登录成功之后等操作不再此方法内
		// 如果使用此过滤器来触发登录认证流程，注意登录请求数据格式的问题
		// 此过滤器的用户名密码默认从request.getParameter()获取，但是这种
		// 读取方式不能读取到如 application/json 等 post 请求数据，需要把
		// 用户名密码的读取逻辑修改为到流中读取request.getInputStream()

		String body = getBody(request);
		JSONObject jsonObject = JSON.parseObject(body);
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		//将验证信息封装一下
		JwtAuthenticatioToken authRequest = new JwtAuthenticatioToken(username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		//进行身份认证
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * 认证成功之后
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @param authResult  用户身份信息
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		// 存储登录认证信息到上下文
		SecurityContextHolder.getContext().setAuthentication(authResult);
		// 记住我服务
		getRememberMeServices().loginSuccess(request, response, authResult);
		// 触发事件监听器
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}
		// 生成并返回token给客户端，后续访问携带此token
		JwtAuthenticatioToken token = new JwtAuthenticatioToken(null, null, JwtTokenUtils.generateToken(authResult));
		HttpUtils.write(response, token);
	}

	/**
	 * 获取请求Body
	 * @param request
	 * @return
	 */
	public String getBody(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
