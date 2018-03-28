package org.apache.cordova.whitelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;

import com.run.bean.CardInfo;
import com.run.bean.CardResult;
import com.run.bean.Question;
import com.run.bean.RelInfo;
import com.run.bean.Result;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.SecurityUtil;
import com.alipay.sdk.app.PayTask;
import com.run.zfbpay.OrderInfoUtil2_0;
import com.run.zfbpay.ZfbUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
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
    private static String LOGIN = "login"; //HTTP请求
    private static String CARDINFO = "cardInfo"; //获取卡片信息
    private static String CHANGEPWD = "changepwd"; //修改密码
    private static String SETQUESTION = "setQuestion"; //设置问题
    private static String VALQUESTION = "validateQuestion"; //验证问题
    private static String GETQUESTION = "getQuestion"; //查看问题
    private static String MSG = "msg"; //获取消息
    private static Integer userInnerId;
    public static String token = "";
    public static Result relData = null; //登录返回消息
    public static RelInfo relInfo = null; //登录消息详情

    private static String RECHARGE = "rechargeaction"; //充值请求
    public final static String BASEURL = "http://sireyun.com:8081/PSMGABService/";

    private Handler mHandler = null;



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
        if (LOGIN.equals(action)) {
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
        }  else if(RECHARGE.equals(action)){
            return zfbRecharge(args, callbackContext);
        }else if(MSG.equals(action)){
            return getMessage(args, callbackContext);
        }else   { //默认第一个参数就是数据
            String url = args.getString(0);
            String data = args.getString(1);
            String rel = HttpUtil.sendRequest(url, data);
            if (null != rel) {
                callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 登陆处理
     *
     * @param args
     * @param callbackContext
     * @return
     */
    private boolean login(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        String userId = args.getString(1);
        String userPassword = args.getString(2);
        Map<String, String> data = new HashMap<>();
        data.put("userId", userId);
        data.put("userPassword", SecurityUtil.getMd5(userPassword));//MD5处理
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

    /**
     * 获取卡片信息
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
    private boolean getCardInfo(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);

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

    /**
     * 修改密码
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
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
            callbackContext.success(JsonParser.toObj(rel, Map.class).get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改密码
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
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
            callbackContext.success(JsonParser.toObj(rel, Map.class).get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证问题
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
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
            callbackContext.success(JsonParser.toObj(rel, Map.class).get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查看问题
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
    private boolean getQuestion(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(JsonParser.toObj(rel, Map.class).get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    /**
     * 支付宝充值请求
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
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

        /**
         userInnerId	Int	用户id
         cardId	String	卡号
         userId	String	用户id
         token	String	token验证
         **/

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
        }

        Object result = relData.get("result");
        //Object 转 数组 再取数组里面的 json字符串  再取value值  object = "result":[{"orderCode":"201803270250251"}]
        //TODO 拼接充值参数
        String orderNum = "";


        if(type.equals("1")){ //支付宝充值

        }else{

        }
        try {
            //下面两句最关键，利用intent启动新的Activity
            Intent intent = new Intent().setClass(cordova.getActivity(), Class.forName("ZHIFUBAO"));
            this.cordova.startActivityForResult(this, intent, 1);

            //获取新的Activity
            Activity myActivity = this.cordova.getActivity();

            mHandler = new Handler();

            Handler uiHandler = new Handler();

            //下面三句为cordova插件回调页面的逻辑代码
            PluginResult mPlugin = new PluginResult(PluginResult.Status.NO_RESULT);
            mPlugin.setKeepCallback(true);

            if(type.equals("0")){ //支付宝支付

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
            }

            callbackContext.sendPluginResult(mPlugin);
            callbackContext.success("success");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
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



    /**
     * 获取消息
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
    private boolean getMessage(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", relData.getResult().get(0).getCardId());
        data.put("token", token);//MD5处理
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(JsonParser.toObj(rel, Map.class).get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }
}
