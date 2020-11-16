package com.example.demo.controller;

import com.example.demo.vo.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pc
 * @Date 2020/11/16
 **/
@Api(tags = "用户权限操作")
@RestController
@RequestMapping("user")
public class UserController {

	@ApiOperation(value = "findAll")
	@PreAuthorize("hasAuthority('sys:user:view')")
	@GetMapping(value="/findAll")
	public HttpResult findAll() {
		return HttpResult.ok("the findAll service is called success.");
	}

	@ApiOperation(value = "edit")
	@PreAuthorize("hasAuthority('sys:user:edit')")
	@GetMapping(value="/edit")
	public HttpResult edit() {
		return HttpResult.ok("the edit service is called success.");
	}

	@ApiOperation(value = "delete")
	@PreAuthorize("hasAuthority('sys:user:delete')")
	@GetMapping(value="/delete")
	public HttpResult delete() {
		return HttpResult.ok("the delete service is called success.");
	}

}
