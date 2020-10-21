package com.pc.util.http;

import com.pc.bean.ChartDataObj;
import com.pc.bean.HttpResult;
import com.pc.util.JsonUtil;
import com.pc.util.http.temp.HttpParamers;
import com.pc.util.http.temp.HttpService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * http测试
 * @author pc
 * @Date 2020/9/4
 **/
public class HttpSenderTest {

    //免费的在线REST服务, 提供测试用的HTTP请求假数据
    //接口信息说明可见：http://www.hangge.com/blog/cache/detail_2020.html
    String uri = "http://www.baidu.com";

    //get方式请求数据
    //请求地址：http://jsonplaceholder.typicode.com/posts
    @Test
    public void test1() {
        System.out.print("\n" + "test1---------------------------"+ "\n");
        HttpParamers paramers = HttpParamers.httpGetParamers();
        String response = "";
        try {
            HttpService httpService = new HttpService(uri);
            response = httpService.service("", paramers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(response);
    }

    //get方式请求数据
    //请求地址：http://jsonplaceholder.typicode.com/posts?userId=5
    @Test
    public void test2() {
        System.out.print("\n" + "test2---------------------------"+ "\n");
        HttpParamers paramers = HttpParamers.httpGetParamers();
        paramers.addParam("userId", "5");
        String response = "";
        try {
            HttpService httpService = new HttpService(uri);
            response = httpService.service("/posts", paramers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(response);
    }

    //post方式请求数据
    //请求地址：http://jsonplaceholder.typicode.com/posts
    @Test
    public void test3() {
        System.out.print("\n" + "test3---------------------------"+ "\n");
        HttpParamers paramers = HttpParamers.httpPostParamers();
        paramers.addParam("time", String.valueOf(System.currentTimeMillis()));
        String response = "";
        try {
            HttpService httpService = new HttpService(uri);
            response = httpService.service("/posts", paramers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(response);
    }

    @Test
    public void test4() {
        HttpSender httpSender = HttpSender.getInstanceGet("http://127.0.0.1:8080/service/httpSender/get");
        httpSender.addParam("name", "&pc");
        httpSender.addParam("age", "20");
        if (httpSender.execute()) {
//            System.out.println(httpSender.getResult());
//            HttpResult formatResult = httpSender.getFormatResult(HttpResult.class);
            List<HttpResult> formatListResult = httpSender.resultToList(HttpResult.class);
            System.out.println(formatListResult);
            System.out.println(httpSender.getResponseInfo());
        }
    }

    @Test
    public void test6() {
        HttpSender httpSender = HttpSender.getInstancePost("http://127.0.0.1:8080/service/httpSender/post");
        httpSender.addParam("name", "pc");
        httpSender.addParam("age", "20");

        httpSender.addCookie("min", "2");
        httpSender.addCookie("expire", "30");
        httpSender.setReadTimeOut(60);
        if (httpSender.execute()) {
//            List<Object> formatResult = httpSender.getFormatResult(HttpResult.class);
//            System.out.println(formatResult);
            System.out.println(httpSender.getResponseInfo());
        } else {
            System.out.println("失败");
        }
    }

    @Test
    public void test7() {
        HttpSender httpSender = HttpSender.getInstancePost("http://127.0.0.1:8080/service/httpSender/post/json");
        httpSender.setRequestJson("{\"name\":\"pc\", \"age\":20}");
        if (httpSender.execute()) {
            System.out.println(httpSender.getResult());
        } else {
            System.out.println("失败");
        }
    }

    @Test
    public void test5() {
        List<ChartDataObj> result = new ArrayList<>();
        ChartDataObj chartDataObj = new ChartDataObj();
        chartDataObj.setGroup("sss");
        ChartDataObj chartDataObj1 = new ChartDataObj();
        chartDataObj1.setGroup("222");

        result.add(chartDataObj);
        result.add(chartDataObj1);
        String ss = JsonUtil.objectToString(result);
        System.out.println(ss);

        List<ChartDataObj> chartDataObjs = JsonUtil.stringToList(ss, ChartDataObj.class);
        System.out.println(chartDataObjs);
    }

}
