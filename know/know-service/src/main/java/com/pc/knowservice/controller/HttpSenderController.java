package com.pc.knowservice.controller;

import com.pc.bean.HttpResult;
import com.pc.bean.User;
import com.pc.util.JsonUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author pc
 * @Date 2020/9/4
 **/
@RequestMapping("httpSender")
@RestController
public class HttpSenderController {

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
