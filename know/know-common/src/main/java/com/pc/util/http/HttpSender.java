package com.pc.util.http;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * http请求工具类
 * @author pc
 * @Date 2020/9/4
 **/
public class HttpSender {

    /**
     * 字符串对象转化工具
     */
    private static ObjectMapper objectMapper = null;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * http请求客户端对象
     */
    private CloseableHttpClient httpClient = null;

    /**
     * http响应数据接收对象
     */
    private CloseableHttpResponse httpResponse = null;

    /**
     * cookie缓存对象
     */
    private CookieStore cookieStore = new BasicCookieStore();

    /**
     * 请求数据配置
     */
    private RequestConfig requestConfig = null;

    /**
     * 请求方法
     */
    private HttpRequestBase httpMethod;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 状态码
     */
    private int code;

    /**
     * post请求需要的请求参数对象
     */
    private List<NameValuePair> postParams = new ArrayList<>();

    /**
     * post请求参数为json字符串
     */
    private String requestJson;

    /**
     * get请求需要的请求参数对象
     */
    private List<String> getParams = new ArrayList<>();

    /**
     * get请求的url也可以使用这种方式进行拼接
     */
//    private URIBuilder getUri = new URIBuilder();

    /**
     * 此次请求返回的结果
     */
    private String result;

    /**
     * 默认解析结果字符集
     */
    private String resultCharset = "utf-8";

    private ResponseInfo responseInfo = null;

    private HttpSender(String url) {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setMaxRedirects(10);
        requestConfigBuilder.setCircularRedirectsAllowed(true);
        //这个时间定义的是从ConnectionManager管理的连接池中取出连接的超时时间， 如果连接池中没有可用的连接，
        //则request会被阻塞，最长等待connectionRequestTimeout的时间，如果还没有被服务，则抛出ConnectionPoolTimeoutException异常，不继续等待
        requestConfigBuilder.setConnectionRequestTimeout(5 * 1000);
        //这个时间定义了通过网络与服务器建立连接的超时时间，也就是取得了连接池中的某个连接之后到接通目标url的连接等待时间
        //发生超时，会抛出ConnectionTimeoutException异常
        requestConfigBuilder.setConnectTimeout(5 * 1000);
        //这个时间定义了socket读数据的超时时间，也就是连接到服务器之后到从服务器获取响应数据需要等待的时间，
        //或者说是连接上一个url之后到获取response的返回等待时间。发生超时，会抛出SocketTimeoutException异常
        requestConfigBuilder.setSocketTimeout(30 * 1000);
        httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        requestConfig = requestConfigBuilder.build();
        this.url = url;
    }

    /**
     * 获得一个 post请求实例
     *
     * @param url
     * @return
     */
    public static HttpSender getInstancePost(String url) {
        HttpSender httpSender = null;
        try {
            httpSender = new HttpSender(url);
            httpSender.httpMethod = new HttpPost(httpSender.url);
            httpSender.httpMethod.setHeader(HTTP.CONN_DIRECTIVE, "close");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpSender;
    }

    /**
     * 获得一个 get请求实例
     *
     * @param url
     * @return
     */
    public static HttpSender getInstanceGet(String url) {
        HttpSender httpSender = null;
        try {
            httpSender = new HttpSender(url);
            httpSender.httpMethod = new HttpGet(httpSender.url);
            httpSender.httpMethod.setHeader(HTTP.CONN_DIRECTIVE, "close");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpSender;
    }

    /**
     * 设置否否允许重定向
     *
     * @param allowed
     */
    public void setAllowedRedirect(boolean allowed) {
        this.requestConfig = RequestConfig.copy(requestConfig).setRedirectsEnabled(allowed).build();
    }

    /**
     * 给post实例添加参数
     *
     * @param key
     * @param value
     */
    public void addParam(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            try {
                if (httpMethod instanceof HttpPost) {
                    postParams.add(new BasicNameValuePair(key, value));
                } else if (httpMethod instanceof HttpGet) {
                    getParams.add(key + "=" + URLEncoder.encode(value, resultCharset));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置请求报文
     *
     * @param httpEntity
     */
    public void setRequestBody(HttpEntity httpEntity) {
        ((HttpPost) httpMethod).setEntity(httpEntity);
    }

    /**
     * 设置连接超时时间
     *
     * @param sec 秒
     */
    public void setConnectTimeOut(int sec) {
        this.requestConfig = RequestConfig.copy(requestConfig).setConnectTimeout(sec * 1000).build();
    }

    /**
     * 设置读取超时时间
     *
     * @param sec 秒
     */
    public void setReadTimeOut(int sec) {
        this.requestConfig = RequestConfig.copy(requestConfig).setSocketTimeout(sec * 1000).build();
    }

    /**
     * 执行
     */
    public boolean execute() {
        boolean success = false;
        try {
            if (httpMethod instanceof HttpPost) {
                StringEntity stringEntity = null;
                HttpPost httpPost = (HttpPost) this.httpMethod;
                //json字符串请求优先
                if (StringUtils.isNotEmpty(requestJson)) {
                    //application\json默认编码是utf-8，不需要设置
                    stringEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
                } else if (httpPost.getEntity() == null) {
                    stringEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
                }
                httpPost.setEntity(stringEntity);
            } else if (httpMethod instanceof HttpGet) {
                if (getParams.size() > 0) {
                    httpMethod.setURI(URI.create(this.url + "?" + com.sun.deploy.util.StringUtils.join(getParams, "&")));
                }
            }
            httpMethod.setConfig(requestConfig);
            httpResponse = httpClient.execute(httpMethod);
            code = httpResponse.getStatusLine().getStatusCode();
            if (code == 200) {
                success = true;
                this.responseInfo = getResponseInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 请求返回值
     *
     * @return
     */
    public String getResult() {
        InputStream inputStream = null;
        try {
            if (StringUtils.equalsIgnoreCase("gzip", responseInfo.getEncoding())) {
                inputStream = getInputStream();
                GZIPInputStream gzin = new GZIPInputStream(inputStream);
                InputStreamReader isr = new InputStreamReader(gzin, StandardCharsets.ISO_8859_1);
                result = IOUtils.toString(isr);
            } else {
                result = EntityUtils.toString(httpResponse.getEntity(), resultCharset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return result;
    }

    /**
     * 返回流
     *
     * @return
     */
    public InputStream getInputStream() {
        InputStream inputStream = null;
        try {
            //每次请求此方法只能被执行一次，因为是普通的io流，读出来就没了
            inputStream = httpResponse.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return inputStream;
    }

    /**
     * 返回字节数组
     *
     * @return
     */
    public byte[] getByte() {
        byte[] bytes = null;
        try {
            bytes = EntityUtils.toByteArray(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 设置结果字符集默认为utf-8
     *
     * @param charset
     */
    public void setResultCharset(String charset) {
        this.resultCharset = charset;
    }

    /**
     * 请求结果状态码
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 添加cookie
     *
     * @param key
     * @param value
     */
    public void addCookie(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            BasicClientCookie cookie = new BasicClientCookie(key, value);
            cookieStore.addCookie(cookie);
        }
    }

    /**
     * 添加cookie
     *
     * @param cookie
     */
    public void addCookie(BasicClientCookie cookie) {
        cookieStore.addCookie(cookie);
    }

    /**
     * 获得cookie
     *
     * @return
     */
    public Cookie[] getCookies() {
        Cookie[] cookieArray = null;
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies != null) {
            cookieArray = cookies.toArray(new Cookie[cookies.size()]);
        }
        return cookieArray;
    }

    /**
     * 添加header
     *
     * @param key
     * @param value
     */
    public void addHeader(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            httpMethod.addHeader(key, value);
        }
    }

    /**
     * 设置post请求json字符串
     * @param requestJson
     */
    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    /**
     * 获得一个head的value
     *
     * @param key
     * @return
     */
    public String getHeaderValue(String key) {
        String value = null;
        if (StringUtils.isNotEmpty(key)) {
            Header header = httpResponse.getFirstHeader(key);
            if (header != null) {
                value = header.getValue();
            }
        }
        return value;
    }

    /**
     * 获得所有header
     *
     * @return
     */
    public Map<String, String> getHeaders() {
        Header[] headers = httpResponse.getAllHeaders();
        Map<String, String> headerMap = null;
        if (ArrayUtils.isNotEmpty(headers)) {
            headerMap = new HashMap<>();
            for (Header header : headers) {
                headerMap.put(header.getName(), header.getValue());
            }
        }
        return headerMap;
    }

    /**
     * 获得服务器返回的信息
     *
     * @return
     */
    public ResponseInfo getResponseInfo() {
        if (code == 200 && responseInfo == null) {
            responseInfo = new ResponseInfo();
            String lm = getHeaderValue("last-modified");
            if (lm != null) {
                responseInfo.setLastModify(lm);
            }
            String contentType = getHeaderValue("content-type");
            if (contentType != null) {
                String[] types = contentType.split(";");
                for (String string : types) {
                    string = string.toLowerCase();
                    if (string.contains("/")) {
                        responseInfo.setContentType(string.trim());
                    }
                    if (string.contains("charset")) {
                        string = StringUtils.deleteWhitespace(string);
                        responseInfo.setCharset(StringUtils.remove(string, "charset="));
                    }
                }
            }
            responseInfo.setEncoding(getHeaderValue("content-encoding"));
            responseInfo.setSize(NumberUtils.toLong(getHeaderValue("content-length")));
        }
        return responseInfo;
    }

    /**
     * 释放连接
     */
    public void close() {
        try {
            if (httpMethod != null) {
                httpMethod.abort();
                httpMethod.releaseConnection();
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得url的ResponseInfo，默认超时时间为3秒
     *
     * @param url
     * @return
     */
    public static ResponseInfo getResponseInfo(String url) {
        return getResponseInfo(url, true);
    }

    /**
     * 获得url的ResponseInfo，默认超时时间为3秒
     *
     * @param url
     * @param redirect 是否允许redirect
     * @return
     */
    public static ResponseInfo getResponseInfo(String url, boolean redirect) {
        return getResponseInfo(url, redirect, 5);
    }

    /**
     * 获得url的ResponseInfo
     *
     * @param url
     * @param timeout
     * @return
     */
    public static ResponseInfo getResponseInfo(String url, boolean redirect, int timeout) {
        HttpSender httpSender = HttpSender.getInstanceGet(url);
        if (timeout > 0) {
            httpSender.setConnectTimeOut(timeout);
        }
        httpSender.setAllowedRedirect(redirect);
        httpSender.execute();
        ResponseInfo responseInfo = httpSender.getResponseInfo();
        httpSender.close();
        return responseInfo;
    }

    /**
     * @param requestCharset the requestCharset to set
     */
    public void setRequestCharset(String requestCharset) {
        if (StringUtils.isNotEmpty(requestCharset)) {
            setHttpElementCharset(requestCharset);
        }
    }

    /**
     * 设置其他字符集<br>
     * 有时候，当我们传递的参数中含有中文的时候，使用httpclient发送，web服务器无法识别，如果返回的http响应头中含有中文字符，也会出现乱码。这时候用它解决
     *
     * @param charset
     */
    public void setHttpElementCharset(String charset) {
        addHeader("http.protocol.element-charset", charset);
    }

    /**
     * 获取指定格式结果数据
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T resultToObject(Class<T> clazz) {
        if (StringUtils.isEmpty(result)) {
            result = getResult();
        }
        return stringToObject(result, clazz);
    }

    /**
     * 获取指定格式结果数据
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> resultToList(Class<T> clazz) {
        if (StringUtils.isEmpty(result)) {
            result = getResult();
        }
        return stringToList(result, clazz);
    }

    /**
     * String转obj
     *
     * @param <T>
     *            obj类型
     * @param json
     *            jsonstring
     * @param clazz
     *            obj的class
     * @return
     */
    private static <T> T stringToObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * String转List<T>
     *
     * @param <T>
     *            obj类型
     * @param json
     *            jsonstring
     * @param clazz
     *            需要映射的class
     * @return
     */
    private static <T> List<T> stringToList(String json, Class<?> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        List<T> list = null;
        try {
            list = objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 响应信息
     */
    public static class ResponseInfo {
        /**
         * contentType
         */
        private String contentType = "";

        /**
         * 最后修改时间
         */
        private String lastModify = null;

        /**
         * 内容大小
         */
        private long size = 0;

        /**
         * HTTP协议的响应报文头 content-encoding
         */
        private String encoding = null;

        /**
         * 网页内容编码，只在页面声明后才有
         */
        private String charset = null;

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getLastModify() {
            return lastModify;
        }

        public void setLastModify(String lastModify) {
            this.lastModify = lastModify;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }
    }

}
