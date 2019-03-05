package com.wisdom.auth.common.pojo;

public class ResponseData<T> {

    /**
     * 返回码
     */
    private Integer status;

    private String error;
    /**
     * 返回描述
     */
    private String message;

    private T data;

    public ResponseData() {
    }

    public ResponseData(Integer code,String error, String message) {
        this.status = code;
        this.error = error;
        this.message = message;
    }

    public ResponseData(Integer code,String error, String message, T data) {
        this.status = code;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
