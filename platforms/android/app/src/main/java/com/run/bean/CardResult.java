package com.run.bean;

import java.util.List;

/**
 *  卡信息
 *  @author lordtan
 */
public class CardResult {
    private String success;
    private String msg;
    private List<CardInfo> result;
    private String token;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CardInfo> getResult() {
        return result;
    }

    public void setResult(List<CardInfo> result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
