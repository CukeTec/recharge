package com.run.bean;

/**
 * 解冻申请记录
 *
 * Created by liudong on 2018-3-29.
 */

public class ApplyRecord {

    /** 用户id */
    private String userInnerId;

    /** 卡号 */
    private String cardId;

    /** 申请id */
    private String applyInnerId;

    /** 申请类型 */
    private String applyType;

    /** 申请日期 */
    private String applyDate;

    /** 申请状态 */
    private String applyState;

    /** 申请备注 */
    private String applyRemark;

    public ApplyRecord() {
    }

    public ApplyRecord(String userInnerId, String cardId, String applyInnerId, String applyType, String applyDate, String applyState, String applyRemark) {
        this.userInnerId = userInnerId;
        this.cardId = cardId;
        this.applyInnerId = applyInnerId;
        this.applyType = applyType;
        this.applyDate = applyDate;
        this.applyState = applyState;
        this.applyRemark = applyRemark;
    }

    public String getUserInnerId() {
        return userInnerId;
    }

    public void setUserInnerId(String userInnerId) {
        this.userInnerId = userInnerId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getApplyInnerId() {
        return applyInnerId;
    }

    public void setApplyInnerId(String applyInnerId) {
        this.applyInnerId = applyInnerId;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }
}
