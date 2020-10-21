package com.pc.util.http.temp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pc
 * @Date 2020/9/4
 **/
public class HttpHeader {

    private Map<String, String> params = new HashMap<String, String>();

    public HttpHeader addParam(String name, String value) {
        this.params.put(name, value);
        return this;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

}
