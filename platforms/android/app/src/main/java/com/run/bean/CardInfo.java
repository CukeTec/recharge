package com.run.bean;

/**
 * 卡信息
 */
public class CardInfo {

    private String cardId; //卡id
    private Integer accountState; //账号状态
    private String endTime; //有效期
    private Integer grantsMoney; //补助账户金额
    private String rechargeRatio; //充值比例
    private Integer cashMoney; //现金账户金额
    private String cardType; //卡类型
    private Integer accountMoney; //主要账户金额

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public Integer getGrantsMoney() {
        return grantsMoney;
    }

    public void setGrantsMoney(Integer grantsMoney) {
        this.grantsMoney = grantsMoney;
    }

    public String getRechargeRatio() {
        return rechargeRatio;
    }

    public void setRechargeRatio(String rechargeRatio) {
        this.rechargeRatio = rechargeRatio;
    }

    public Integer getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(Integer cashMoney) {
        this.cashMoney = cashMoney;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Integer accountMoney) {
        this.accountMoney = accountMoney;
    }
}
