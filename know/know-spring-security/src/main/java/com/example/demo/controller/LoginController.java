package com.example.demo.controller;

import com.example.demo.security.JwtAuthenticatioToken;
import com.example.demo.util.SecurityUtils;
import com.example.demo.vo.HttpResult;
import com.example.demo.vo.LoginBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author pc
 * @Date 2020/11/16
 **/
@Api(tags = "登录")
@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * 登录接口
	 */
	@ApiOperation(value = "登录接口")
	@PostMapping(value = "/login")
	public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
			String username = loginBean.getUsername();
		String password = loginBean.getPassword();

		// 系统登录认证
		JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);

		return HttpResult.ok(token);
	}

}
