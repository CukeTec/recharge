package com.run.enumeration;

/**
 * Created by zengxiao on 2018/4/2.
 */

public enum ActionCommand {
    LOGIN("http://sireyun.com:8081/PSMGABService/loginAuth"), //HTTP请求
    CARDINFO("http://sireyun.com:8081/PSMGABService/cardInfo"), //获取卡片信息
    CHANGEPASSWORD("http://sireyun.com:8081/PSMGABService/changeUserPW"),//修改密码
    SETQUESTION("http://sireyun.com:8081/PSMGABService/setCodeSure "),//设置问题
    VALIDATEQUESTION("http://sireyun.com:8081/PSMGABService/isCodeSure"),//验证问题
    GETQUESTION("http://sireyun.com:8081/PSMGABService/searchCodeSure"), //查看问题
    GETMESSAGE("http://sireyun.com:8081/PSMGABService/messageRecord"), //获取消息
    DELETEMESSAGE("http://sireyun.com:8081/PSMGABService/clearMessage"), //删除消息
    MESSAGEDETAIL("http://sireyun.com:8081/PSMGABService/messageDetail"), //获取消息
    RECHARGEPRE("http://sireyun.com:8081/PSMGABService/"),//进入充值请求
    RECHARGE("http://sireyun.com:8081/PSMGABService/orderNum"), //充值请求
    FREEZECARD("http://sireyun.com:8081/PSMGABService/freezeCard"), //冻结接口
    UNFREEZEINFO("http://sireyun.com:8081/PSMGABService/unfreezeCard"), //解冻申请
    COMSUMTIONACTION("http://sireyun.com:8081/PSMGABService/getConsumptionInfo"), //消费记录接口
    APPLYRECORD("http://sireyun.com:8081/PSMGABService/unfreezeInfo"), //申请记录接口
    MESSAGEDEAL("http://sireyun.com:8081/PSMGABService/messageDeal");  //审核处理接口

    ActionCommand(String url) {
        this.url = url;
    }
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
