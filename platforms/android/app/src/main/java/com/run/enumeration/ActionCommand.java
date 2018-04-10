package com.run.enumeration;

/**
 * Created by zengxiao on 2018/4/2.
 */

public enum ActionCommand {
    LOGIN("http://sireyun.com:8081/PSMGABService/loginAuth", "LoginExecutor"), //HTTP请求
    CARDINFO("http://sireyun.com:8081/PSMGABService/cardInfo", "CardInfoExecutor"), //获取卡片信息
    CHANGEPASSWORD("http://sireyun.com:8081/PSMGABService/changeUserPW", "ChangePasswordExecutor"),//修改密码
    SETQUESTION("http://sireyun.com:8081/PSMGABService/setCodeSure ", "SetQuestionExector"),//设置问题
    VALIDATEQUESTION("http://sireyun.com:8081/PSMGABService/isCodeSure", "ValidateQuestionExecutor"),//验证问题
    GETQUESTION("http://sireyun.com:8081/PSMGABService/searchCodeSure", "GetQuestionExecutor"), //查看问题
    GETMESSAGE("http://sireyun.com:8081/PSMGABService/messageRecord", "GetMessageExecutor"), //获取消息
    DELETEMESSAGE("http://sireyun.com:8081/PSMGABService/clearMessage", "DeleteMessageExecutor"), //删除消息
    MESSAGEDETAIL("http://sireyun.com:8081/PSMGABService/messageDetail", "MessageDetailExecutor"), //获取消息
    RECHARGEPRE("http://sireyun.com:8081/PSMGABService/", "RechargePreExecutor"),//进入充值请求
    RECHARGE("http://sireyun.com:8081/PSMGABService/orderNum", "ZfbRechargeExecutor"), //充值请求
    FREEZECARD("http://sireyun.com:8081/PSMGABService/freezeCard", "FreezeCardExecutor"), //冻结接口
    UNFREEZEINFO("http://sireyun.com:8081/PSMGABService/unfreezeInfo", "UnfreezeCardExecutor"), //解冻申请
    COMSUMTIONACTION("http://sireyun.com:8081/PSMGABService/getConsumptionInfo", "ConsumtionExecutor"), //消费记录接口
    APPLYRECORD("http://sireyun.com:8081/PSMGABService/applyRecord", "ApplyRecordExecutor"), //申请记录接口
    MESSAGEDEAL("http://sireyun.com:8081/PSMGABService/messageDeal", "MessageDealExecutor"),  //审核处理接口
    MYAPPACTION("http://sireyun.com:8081/PSMGABService/cardInfo", "MyApplicationExecutor"),
    FORGETPASSWORD("http://sireyun.com:8081/PSMGABService/forgetPassword","ForgetPasswordExecutor"), //忘记密码接口
	RECHARGERECORD("http://sireyun.com:8081/PSMGABService/rechargeRecord", "RechargeRecordExecutor"),  //充值记录接口
	SETPASSWORD("http://sireyun.com:8081/PSMGABService/setPassWord", "SetPasswordExecutor"),  //重新设置密码接口
    SENDMESSAGE("http://sireyun.com:8081/PSMGABService/messageSend", "SendMessageExecutor"),// 发送消息接口
    LOGOUT("http://sireyun.com:8081/PSMGABService/exitLogon", "LogOutExecutor"),  //退出登录
    MESSAGEGROUP("http://sireyun.com:8081/PSMGABService/messageGroup", "MessageGroupExecutor"), // 获取消息组接口
    WAITCHECK("http://sireyun.com:8081/PSMGABService/waitCheck","WaitCheckExecutor"), //待审核列表
    
	;
    ActionCommand(String url, String className) {
        this.url = url;
        this.className = className;
    }
    private String url;
    private String className;
    private final static String packageName = "com.run.action.impl.";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return packageName + className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
