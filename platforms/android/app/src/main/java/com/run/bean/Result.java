package com.run.bean;

import java.util.List;

/**
 * http请求结果
 * @author lordtan
 * @date 2018-03-27
 */
public class Result {

    private String state;
    private String msg;
    private List<RelInfo> result;
    private String token;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RelInfo> getResult() {
        return result;
    }

    public void setResult(List<RelInfo> result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
