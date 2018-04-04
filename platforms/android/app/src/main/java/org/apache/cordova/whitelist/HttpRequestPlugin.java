package org.apache.cordova.whitelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.run.action.CommandExecutor;
import com.run.bean.CardInfo;
import com.run.bean.CardResult;
import com.run.bean.Question;
import com.run.bean.RelInfo;
import com.run.bean.Result;
import com.run.invoker.ActionInvoker;
import com.run.receiver.ActionReceiver;
import com.run.util.Encript;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.SecurityUtil;
import com.run.zfbpay.OrderInfoUtil2_0;
import com.run.zfbpay.ZfbUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前端JS调用http请求
 */
public class HttpRequestPlugin extends CordovaPlugin {
/*    private static String LOGIN = "login"; //HTTP请求
    private static String CARDINFO = "cardInfo"; //获取卡片信息
    private static String CHANGEPWD = "changepwd"; //修改密码
    private static String SETQUESTION = "setQuestion"; //设置问题
    private static String VALQUESTION = "validateQuestion"; //验证问题
    private static String GETQUESTION = "getQuestion"; //查看问题
    private static String MSG = "msg"; //获取消息
    private static String MSGDEL = "msgdel"; //删除消息
    private static String MSGDETAIL = "msgDetail"; //获取消息*/
    public static Integer userInnerId;
    public static String token = "";
    public static Result relData = null; //登录返回消息
    public static RelInfo relInfo = null; //登录消息详情

/*    private static String RECHARGEPRE = "rechargepre";//进入充值请求
    private static String RECHARGE = "rechargeaction"; //充值请求
    public final static String BASEURL = "http://sireyun.com:8081/PSMGABService/";
    private static String FREEZECARD = "freezeCard"; //冻结接口
    private static String UNFREEZEINFO = "unfreezeInfo"; //解冻申请
    private static String COMSUMTIONACTION = "consumtionaction"; //消费记录接口
    private static String APPLYRECORD = "applyrecordaction"; //申请记录接口
    private static String MESSAGEDEAL = "messagedealaction"; //审核处理接口
    private static String MYAPPACTION = "myappaction"; //

    private Handler mHandler = null;*/




    /**
     * 执行http请求
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return
     * @throws JSONException
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        ActionReceiver actionReceiver = new ActionReceiver(action, args, callbackContext);
        ActionInvoker actionInvoker = new ActionInvoker(actionReceiver.getCommandExecutor());
        return actionInvoker.invoke();
/*        if (LOGIN.equals(action)) {
            return login(args, callbackContext);
        } else if (CARDINFO.equals(action)) {
            return getCardInfo(args, callbackContext);
        } else if (CHANGEPWD.equals(action)) {
            return changePassword(args, callbackContext);
        } else if (SETQUESTION.equals(action)) {
            return setQuestion(args, callbackContext);
        } else if (VALQUESTION.equals(action)) {
            return validateQuestion(args, callbackContext);
        } else if (GETQUESTION.equals(action)) {
            return getQuestion(args, callbackContext);
        } else if (RECHARGEPRE.equals(action)){ //进入充值请求
            return  rechargePre(args, callbackContext);
        } else if(RECHARGE.equals(action)){
            return zfbRecharge(args, callbackContext);
        }else if(MSG.equals(action)){
            return getMessage(args, callbackContext);
        }else if(MSGDEL.equals(action)){
            return delMessage(args, callbackContext);
        }else if(MSGDETAIL.equals(action)){
            return mesDetail(args, callbackContext);
        }else if(FREEZECARD.equals(action)){ //冻结接口

            return freezeCard(args, callbackContext);
        }
        else if(UNFREEZEINFO.equals(action)){ //申请解冻

            return unFreeze(args, callbackContext);
        }
        else if(COMSUMTIONACTION.equals(action)){ //账单接口

            return consumtionAction(args, callbackContext);
        }
        else if(APPLYRECORD.equals(action)){ //申请记录

            return applyrecordAction(args, callbackContext);
        }
        else if(MESSAGEDEAL.equals(action)){ //审核处理

            return messageDealAction(args, callbackContext);
        }
        else{ //默认第一个参数就是数据
            String url = args.getString(0);
            String data = args.getString(1);
            String rel = HttpUtil.sendRequest(url, data);
            if (null != rel) {
                callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
                return true;
            } else {
                return false;
            }
        }*/
    }

   /* *//**
     * 登陆处理
     *
     * @param args
     * @param callbackContext
     * @return
     *//*
    private boolean login(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        String userId = args.getString(1);
        String userPassword = args.getString(2);
        Map<String, String> data = new HashMap<>();
        data.put("userId", userId);
        data.put("userPassword", Encript.md5(userPassword));//MD5处理
        data.put("macid", SecurityUtil.getMac()); //获取mac地址
        String rel = HttpUtil.sendRequest(url, data);

        relData = JsonParser.toObj(rel, Result.class);
        token = relData.getToken();
        relInfo = relData.getResult().get(0);
        userInnerId = relInfo.getUserInnerId();

        if (null != rel) {
            callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    *//**
     * 获取卡片信息
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean getCardInfo(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        if(url == null || url.length() < 1 || url == ""){
            url = BASEURL + "cardInfo";
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理

        String rel = HttpUtil.sendRequest(url, data);
        CardResult relData = JsonParser.toObj(rel, CardResult.class);
        CardInfo cardInfo = relData.getResult().get(0);
        cardInfo.setCardId(relInfo.getCardId());
        if (null != rel) {
            JSONObject obj = new JSONObject(JsonParser.toJson(relData.getResult().get(0)));
            callbackContext.success(obj);//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    *//**
     * 修改密码
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean changePassword(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理
        data.put("oldpassword", SecurityUtil.getMd5(args.getString(1)));
        data.put("newpassword", SecurityUtil.getMd5(args.getString(2)));
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(relData.get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    *//**
     * 修改密码
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean setQuestion(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        JSONArray questions = args.getJSONArray(1);
        List<Question> result = new ArrayList<>();
        String questionJson = null;
        Question question = null;
        for(int i=0;i<questions.length();i++){
            questionJson = questions.get(i).toString();
            question = JsonParser.toObj(questionJson, Question.class);
            result.add(question);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理
        data.put("question", result);
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(relData.get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    *//**
     * 验证问题
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean validateQuestion(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        JSONArray questions = args.getJSONArray(1);
        List<Question> result = new ArrayList<>();
        String questionJson = null;
        Question question = null;
        for(int i=0;i<questions.length();i++){
            questionJson = questions.get(i).toString();
            question = JsonParser.toObj(questionJson, Question.class);
            result.add(question);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理
        data.put("question", result);
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(relData.get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    *//**
     * 查看问题
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean getQuestion(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(relData.get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    *//**
     * 支付宝充值请求
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    public boolean rechargePre(JSONArray args, CallbackContext callbackContext) throws JSONException{
        if (relInfo == null){

            callbackContext.error("查询不到卡号");
            return true;
        }

        String cardId = relInfo.getCardId(); //卡号
        callbackContext.success(cardId);

        return true;
    }

    *//**
     * 支付宝充值请求
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    public boolean zfbRecharge(JSONArray args, CallbackContext callbackContext) throws JSONException{
        JSONObject message = new JSONObject();
        if(args == null || args.length() < 1){
            message.put("code","-1");
            message.put("msg","请选择充值金额");

            callbackContext.error(message);
            return true;
        }

        String amount = args.getString(0); //充值金额
        String type = args.getString(1); //充值方式

        String url = BASEURL + "orderNum"; //订单号url
        String userInnerId = "1"; //获取用户id
        String cardId = "1272223438"; //获取卡号
        String userId = "469747"; //用户id

        //获取订单号
        Map<String,String>  data = new HashMap<>();
        data.put("userInnerId",userInnerId);
        data.put("cardId",cardId);
        data.put("userId",userId);
        data.put("token",token);
        String rel = HttpUtil.sendRequest(url,data);
        Map<String,Object> relData = JsonParser.toObj(rel,Map.class);
        if(rel == null || relData.isEmpty()){
            message.put("code","-1");
            message.put("msg","服务器返回为空");
            callbackContext.error(message);
            return true;
        }

        String state = (String) relData.get(Constans.STATE);
        if(!state.equals(Constans.STATE_200)){
            if(state.equals(Constans.STATE_404)){
                message.put("code","404");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else if(state.equals(Constans.STATE_405)){
                message.put("code","405");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else {
                message.put("code","-1");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }

        }


        List<Map<String,Object>> result = (List<Map<String,Object>>)relData.get("result");
        //Object 转 数组 再取数组里面的 json字符串  再取value值  object = "result":[{"orderCode":"201803270250251"}]
        //TODO 拼接充值参数
        if(result == null || result.size() < 1){
            message.put("code","-1");
            message.put("msg","服务器订单号为空");
            callbackContext.error(message);
            return true;
        }
        Map<String,Object> oerderMap = result.get(0);
        if(oerderMap == null || oerderMap.isEmpty()){
            message.put("code","-1");
            message.put("msg","服务器订单号为空");
            callbackContext.error(message);
            return true;
        }

        //订单号
        String orderNum = oerderMap.get("orderCode").toString();

        if(type.equals("1")){ //支付宝充值
            try {
                //获取新的Activity
                Activity myActivity = this.cordova.getActivity();
                mHandler = new Handler();

                boolean rsa2 = (ZfbUtil.ZFB_PRIVATE_RSA.length() > 0)?false:true;
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(rsa2);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                String privateKey = ZfbUtil.ZFB_PRIVATE_RSA;
                String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                final String orderInfo = orderParam + "&" + sign;

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(myActivity);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = ZfbUtil.SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();

            } catch (Exception e) {
                LOG.i("-----e-----",e.getMessage());
                e.printStackTrace();
                message.put("code","-1");
                message.put("msg","网络异常");
                callbackContext.error(message);
                return true;
            }
        }else{

        }


        return false;
    }

    //onActivityResult为第二个Activity执行完后的回调接收方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        switch (resultCode) { //resultCode为回传的标记，我在第二个Activity中回传的是RESULT_OK
            case Activity.RESULT_OK:
                Bundle b=intent.getExtras();  //data为第二个Activity中回传的Intent
                String str=b.getString("change01");//str即为回传的值
                break;
            default:
                break;
        }
    }



    *//**
     * 获取消息
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean getMessage(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", relData.getResult().get(0).getCardId());
        data.put("token", token);//MD5处理
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>) relData.get("result");
        if (null != rel) {
            callbackContext.success(JsonParser.toJson(result));//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }
    *//**
     * 删除消息
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean delMessage(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONArray messageId = args.getJSONArray(1);
        String url = args.getString(0);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", relData.getResult().get(0).getCardId());
        data.put("token", token);//MD5处理
        for(int i=0;i<messageId.length();i++){
            data.put("messageInnerId", messageId.get(i));
            String str = HttpUtil.sendRequest(url, data);
        }
        callbackContext.success("success");//如果不调用success回调，则js中successCallback不会执行
        return true;
    }
    *//**
     * 删除消息
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean mesDetail(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String messageId = args.getString(1);
        String url = args.getString(0);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", relData.getResult().get(0).getCardId());
        data.put("token", token);//MD5处理
        data.put("messageInnerId", messageId);
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>) relData.get("result");
        if (null != rel) {
            callbackContext.success(JsonParser.toJson(result));//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    *//**
     * 冻结卡片
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean freezeCard(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject message = new JSONObject();
        if(relInfo == null){
            message.put("code", "404");
            message.put("msg","过期");
            callbackContext.error(message);

            return true;
        }

        userInnerId = relInfo.getUserInnerId();	// 用户id
        String cardId = relInfo.getCardId(); //卡号
        String token = relInfo.getToken(); //验证

        String url = BASEURL + "freezeCard";
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", cardId);
        data.put("token", token);

        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);


        if(rel == null || relData.isEmpty()){
            message.put("code","-1");
            message.put("msg","服务器返回为空");
            callbackContext.error(message);
            return true;
        }

        String state = (String) relData.get(Constans.STATE);
        if(!state.equals(Constans.STATE_200)){
            if(state.equals(Constans.STATE_404)){
                message.put("code","404");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else if(state.equals(Constans.STATE_405)){
                message.put("code","405");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else {
                message.put("code","-1");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
        }

        callbackContext.success("冻结成功");
        return true;
    }


    *//**
     * 解冻申请
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean unFreeze(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject message = new JSONObject();
        if(relInfo == null){
            message.put("code", "404");
            message.put("msg","过期");
            callbackContext.error(message);

            return true;
        }

        userInnerId = relInfo.getUserInnerId();	// 用户id
        String cardId = relInfo.getCardId(); //卡号
        String applyType = "1"; //1 解冻申请
        String applyRemark = "申请解冻"; // 申请理由

        String url = BASEURL + "unfreezeInfo";
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", cardId);
        data.put("applyType", applyType);
        data.put("applyRemark", applyRemark);
        data.put("token", token);

        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);


        if(rel == null || relData.isEmpty()){
            message.put("code","-1");
            message.put("msg","服务器返回为空");
            callbackContext.error(message);
            return true;
        }

        String state = (String) relData.get(Constans.STATE);
        if(!state.equals(Constans.STATE_200)){
            if(state.equals(Constans.STATE_404)){
                message.put("code","404");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else if(state.equals(Constans.STATE_405)){
                message.put("code","405");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else {
                message.put("code","-1");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
        }

        callbackContext.success("申请成功");
        return true;
    }

    *//**
     * 账单
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean consumtionAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject message = new JSONObject();
        if(relInfo == null){
            message.put("code", "404");
            message.put("msg","过期");
            callbackContext.error(message);

            return true;
        }

        userInnerId = relInfo.getUserInnerId();	// 用户id
        String startDate = ""; //开始时间
        String endDate = ""; //结束时间

        if(args.length() > 1 && args != null && !args.equals("''")){
            startDate = args.getString(0);
            endDate = args.getString(1);
        }

        String url = BASEURL + "getConsumptionInfo";
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("startDate", startDate);
        data.put("endDate", endDate);
        data.put("token", token);

        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);

        if(rel == null || relData.isEmpty()){
            message.put("code","-1");
            message.put("msg","服务器返回为空");
            callbackContext.error(message);
            return true;
        }

        String state = (String) relData.get(Constans.STATE);
        if(!state.equals(Constans.STATE_200)){
            if(state.equals(Constans.STATE_404)){
                message.put("code","404");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else if(state.equals(Constans.STATE_405)){
                message.put("code","405");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else {
                message.put("code","-1");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
        }

        ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>)relData.get("result");
        callbackContext.success(JsonParser.toJson(result));//如果不调用success回调，则js中successCallback不会执行

        return true;
    }

    *//**
     * 申请记录
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean applyrecordAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject message = new JSONObject();
        if(relInfo == null){
            message.put("code", "404");
            message.put("msg","过期");
            callbackContext.error(message);

            return true;
        }

        userInnerId = relInfo.getUserInnerId();	// 用户id
        String cardId = relInfo.getCardId(); //卡号

        String url = BASEURL + "applyRecord";
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", cardId);
        data.put("token", token);

        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);

        if(rel == null || relData.isEmpty()){
            message.put("code","-1");
            message.put("msg","服务器返回为空");
            callbackContext.error(message);
            return true;
        }

        String state = (String) relData.get(Constans.STATE);
        if(!state.equals(Constans.STATE_200)){
            if(state.equals(Constans.STATE_404)){
                message.put("code","404");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else if(state.equals(Constans.STATE_405)){
                message.put("code","405");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else {
                message.put("code","-1");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
        }

        ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>)relData.get("result");
        callbackContext.success(JsonParser.toJson(result));//如果不调用success回调，则js中successCallback不会执行

        return true;
    }

    *//**
     * 解冻申请审核
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     *//*
    private boolean messageDealAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject message = new JSONObject();

        if(args.length() < 1 && args != null){
            message.put("code", "-1");
            message.put("msg","请选择需要操作的解冻申请");
            callbackContext.error(message);

            return true;
        }

        String  userid = args.getString(0); // 用户id
        String applyInnerId = args.getString(1); //申请id
        String auditState = args.getString(2); //	审核状态  1 - 同意   2- 拒绝
        String auditDetail = args.getString(3); //理由

        String url = BASEURL + "messageDeal";
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userid);
        data.put("applyInnerId", applyInnerId);
        data.put("auditState", auditState);
        data.put("auditDetail",auditDetail);
        data.put("token", token);

        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);

        if(rel == null || relData.isEmpty()){
            message.put("code","-1");
            message.put("msg","服务器返回为空");
            callbackContext.error(message);
            return true;
        }

        String state = (String) relData.get(Constans.STATE);
        if(!state.equals(Constans.STATE_200)){
            if(state.equals(Constans.STATE_404)){
                message.put("code","404");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else if(state.equals(Constans.STATE_405)){
                message.put("code","405");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
            else {
                message.put("code","-1");
                message.put("msg",relData.get("msg"));
                callbackContext.error(message);
                return true;
            }
        }

        callbackContext.success("操作成功");

        return true;
    }

    /**
     * 我的请求
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
/*    private boolean myAppAction(JSONArray args, CallbackContext callbackContext) throws JSONException {
        Integer userTypeInnerId = 1;
        if(relInfo != null){
            userTypeInnerId = relInfo.getUserTypeInnerId();
        }

        String url =  BASEURL + "cardInfo";
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理



        String rel = HttpUtil.sendRequest(url, data);
        CardResult relData = JsonParser.toObj(rel, CardResult.class);
        CardInfo cardInfo = relData.getResult().get(0);
        cardInfo.setCardId(relInfo.getCardId());
        if (null != rel) {
            JSONObject obj = new JSONObject(JsonParser.toJson(relData.getResult().get(0)));
            obj.put("userTypeInnerId", userTypeInnerId);

            callbackContext.success(obj);//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }*/

}