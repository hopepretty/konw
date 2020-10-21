package com.pc.bean;

/**
 * @author pc
 * @Date 2020/9/4
 **/
public class HttpResult<T> {

    private boolean status;

    private String message;

    private Integer errorType;

    private T data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public HttpResult() {

    }

    public HttpResult(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public HttpResult(boolean status, String message, Integer errorType, T data) {
        this.status = status;
        this.message = message;
        this.errorType = errorType;
        this.data = data;
    }

    public static <T> HttpResult<T> success() {
        return new HttpResult<>(true, null, null);
    }

    public static <T> HttpResult<T> success(T data) {
        return new HttpResult<>(true, null, data);
    }

    public static <T> HttpResult<T> success(T data, String message) {
        return new HttpResult<>(true, message, data);
    }

    public static <T> HttpResult<T> success(T data, String message, Object... args) {
        return new HttpResult<>(true, String.format(message, args), data);
    }

    public static <T> HttpResult<T> error(int errorType, String message) {
        return new HttpResult<>(false, message, errorType, null);
    }

    public static <T> HttpResult<T> error(int errorType, String message, Object... args) {
        return new HttpResult<>(false, String.format(message, args), errorType, null);
    }

}
