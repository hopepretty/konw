package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * spring-security配置
 *
 * @author pc
 * @Date 2020/11/16
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 配置自定义身份认证器
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//指定自定义认证器,当我们不适用指定的,默认使用 DaoAuthenticationProvider 进行认证登录信息
		auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
		//指定自定义的获取信息获取服务
		auth.userDetailsService(userDetailsService);
	}

	/**
	 * 核心配置类
	 * 配置拦截规则
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
		//如果在继承 WebSecurityConfigurerAdapter 的配置类中的 configure(HttpSecurity http) 方法中有配置 HttpSecurity 的 formLogin，
		// 则会返回一个 FormLoginConfigurer 对象。formLogin().x.x 就是配置使用内置的登录验证过滤器，
		// 默认实现为 UsernamePasswordAuthenticationFilter。
		http.cors().and().csrf().disable()
				.authorizeRequests()
				//放行跨域预检请求
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				//放行登录请求
				.antMatchers("/login").permitAll()
				//swagger
				.antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/v2/api-docs").permitAll()
				.antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
				//其他所有请求需要身份认证
				.anyRequest().authenticated();
		// 退出登录处理器
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
		// 开启登录认证流程过滤器  将自定义的过滤器放在  UsernamePasswordAuthenticationFilter前面
		// 当我们不在login方法中去认证信息时使用
//		http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
		// 从token中获取用户信息,并且塞入上下文环境,鉴权使用
		http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/**
	 * 当我们不使用自定义的认证器 即JwtAuthenticationProvider时,需要创建一个密码器
	 *
	 * 当前我们使用的是自定义认证器
	 *
	 * @return
	 * @throws Exception
	 */
//	@Bean
//	public PasswordEncoder passwordEncoder() throws Exception {
//		return new BCryptPasswordEncoder();
//	}
}
