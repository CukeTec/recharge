package com.run.action.impl;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.run.action.CommandExecutor;
import com.run.receiver.ActionReceiver;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.zfbpay.OrderInfoUtil2_0;
import com.run.zfbpay.ZfbUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;
import org.apache.cordova.whitelist.Constans;
import org.apache.cordova.whitelist.HttpRequestPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.cordova.whitelist.HttpRequestPlugin.token;

/**
 * Created by zengxiao on 2018/4/3.
 */

public class ZfbRechargeExecutor extends CommandExecutor {

    @Override
    public boolean execute() throws JSONException {
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        JSONArray args = actionReceiver.getParams();
        String url = actionReceiver.getUrl();
        JSONObject message = new JSONObject();
        if (args == null || args.length() < 1) {
            message.put("code", "-1");
            message.put("msg", "请选择充值金额");

            callbackContext.error(message);
            return true;
        }
        String amount = args.getString(0); //充值金额
        String type = args.getString(1); //充值方式
        String userInnerId = "1"; //获取用户id
        String cardId = "1272223438"; //获取卡号
        String userId = "469747"; //用户id
        //获取订单号
        Map<String, String> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", cardId);
        data.put("userId", userId);
        data.put("token", token);
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (rel == null || relData.isEmpty()) {
            message.put("code", "-1");
            message.put("msg", "服务器返回为空");
            callbackContext.error(message);
            return true;
        }
        String state = (String) relData.get(Constans.STATE);
        if (!state.equals(Constans.STATE_200)) {
            if (state.equals(Constans.STATE_404)) {
                message.put("code", "404");
                message.put("msg", relData.get("msg"));
                callbackContext.error(message);
                return true;
            } else if (state.equals(Constans.STATE_405)) {
                message.put("code", "405");
                message.put("msg", relData.get("msg"));
                callbackContext.error(message);
                return true;
            } else {
                message.put("code", "-1");
                message.put("msg", relData.get("msg"));
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
        String orderno = oerderMap.get("orderCode").toString();
        HttpRequestPlugin httpRequestPlugin = new HttpRequestPlugin();
        if(type.equals("1")){ //支付宝充值
            try {
                //获取新的Activity
                Activity myActivity = httpRequestPlugin.cordova.getActivity();
                mHandler = new Handler();
                boolean rsa2 = (ZfbUtil.ZFB_PRIVATE_RSA.length() > 0)?false:true;
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(rsa2, amount, orderno);
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
    private Handler mHandler = null;
    private ActionReceiver actionReceiver;

    @Override
    public ActionReceiver getActionReceiver() {
        return actionReceiver;
    }

    @Override
    public void setActionReceiver(ActionReceiver actionReceiver) {
        this.actionReceiver = actionReceiver;
    }
}
