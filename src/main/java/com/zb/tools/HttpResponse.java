package com.zb.tools;

import java.io.Serializable;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class HttpResponse<T> implements Serializable {
    private static final long serialVersionUID = 1725159680599612404L;
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
