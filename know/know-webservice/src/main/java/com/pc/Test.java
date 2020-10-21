package com.pc;

import com.pc.util.http.HttpSender;

/**
 * @author pc
 * @Date 2020/10/14
 **/
public class Test {

    public static void main(String[] args) {
        HttpSender instanceGet = HttpSender.getInstanceGet(
                "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo?mobileCode=17683907825&userID=");
        if (instanceGet.execute()) {
            System.out.println(instanceGet.getResult());
        }
    }

}
