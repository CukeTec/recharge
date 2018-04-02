package com.run.bean;

/**
 * 账单
 *
 * Created by liudong on 2018-3-29.
 */

public class Bill {

    //人员id
    private String userInnerId;

    //卡号
    private String StringcardId;

    //交易地点
    private String conType;

    //交易金额
    private String money;

    //交易时间
    private String  conDate;

    //交易流水号
    private String conNumber;

    public Bill() {
    }

    public Bill(String userInnerId, String stringcardId, String conType, String money, String conDate, String conNumber) {
        this.userInnerId = userInnerId;
        StringcardId = stringcardId;
        this.conType = conType;
        this.money = money;
        this.conDate = conDate;
        this.conNumber = conNumber;
    }

    public String getUserInnerId() {
        return userInnerId;
    }

    public void setUserInnerId(String userInnerId) {
        this.userInnerId = userInnerId;
    }

    public String getStringcardId() {
        return StringcardId;
    }

    public void setStringcardId(String stringcardId) {
        StringcardId = stringcardId;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getConDate() {
        return conDate;
    }

    public void setConDate(String conDate) {
        this.conDate = conDate;
    }

    public String getConNumber() {
        return conNumber;
    }

    public void setConNumber(String conNumber) {
        this.conNumber = conNumber;
    }
}
