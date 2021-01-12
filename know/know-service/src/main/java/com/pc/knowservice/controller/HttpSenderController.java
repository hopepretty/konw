package com.pc.knowservice.controller;

import com.pc.bean.HttpResult;
import com.pc.bean.User;
import com.pc.redis.RedisClient;
import com.pc.redis.RedisManager;
import com.pc.util.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pc
 * @Date 2020/9/4
 **/
@RequestMapping("httpSender")
@RestController
public class HttpSenderController {

	private AtomicInteger count = new AtomicInteger(0);

	@GetMapping("jmeter")
	public String jmeter(String key) {
		System.out.println(key);
		System.out.println(count.incrementAndGet());
		return "成功";
	}

	@GetMapping("redisSet")
	public String redisSet(String key) {
		RedisClient redisClient = RedisManager.getInstance("test");
		System.out.println(redisClient.set(key, "你好"));
		System.out.println(redisClient.get(key, String.class));
		return "成功";
	}

    /**
     * post测试
     * @param response
     * @param request
     * @param name
     * @param age
     */
    @PostMapping("post")
    public String post(HttpServletResponse response, HttpServletRequest request, String name, String age) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + "=" + cookie.getValue());
            }
        }

        response.addHeader("name", name);
        response.addHeader("age", age);

        Cookie cookie = new Cookie("max", "20");
        response.addCookie(cookie);
        return "成功";
    }

    /**
     * 通过@RequestBody将json对象转为实体对象
     *
     * post的json测试
     * @param user
     */
    @PostMapping("post/json")
    public String postJson(@RequestBody User user) {
        System.out.println(JsonUtil.objectToString(user));
        return "成功";
    }

    /**
     * get测试
     * @param response
     * @param request
     * @param name
     * @param age
     */
    @GetMapping("get")
    public List<HttpResult> get(HttpServletResponse response, HttpServletRequest request, String name, String age) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + "=" + cookie.getValue());
            }
        }

        response.addHeader("name", name);
        response.addHeader("age", age);

        Cookie cookie = new Cookie("max", "20");
        response.addCookie(cookie);
        List<HttpResult> result = Arrays.asList(HttpResult.success("成功"), HttpResult.success("失败"));
        return result;
    }

}
