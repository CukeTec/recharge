package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.bean.CardInfo;
import com.run.bean.CardResult;
import com.run.receiver.ActionReceiver;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.whitelist.Constans;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static org.apache.cordova.whitelist.HttpRequestPlugin.relInfo;
import static org.apache.cordova.whitelist.HttpRequestPlugin.token;
import static org.apache.cordova.whitelist.HttpRequestPlugin.userInnerId;

/**
 * Created by zengxiao on 2018/4/4.
 */

public class MyApplicationExecutor extends CommandExecutor {

    @Override
    public boolean execute() throws JSONException {
        JSONObject message = new JSONObject();
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        String url = actionReceiver.getUrl();
        Integer userTypeInnerId = 1;
        if(relInfo == null){
            message.put("code",Constans.NO_LOGIN);
            message.put("msg","未登录或登录失效");
            callbackContext.error(message);
            return true;
        }

        userTypeInnerId = relInfo.getUserTypeInnerId();

        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relMapData = JsonParser.toObj(rel, Map.class);
        if(rel == null || relMapData.isEmpty()){
            message.put("code","-1");
            message.put("msg","服务器返回为空");
            callbackContext.error(message);
            return true;
        }
        String state = (String) relMapData.get(Constans.STATE);
        String msg = state +":" +relMapData.get("msg");
        if(!state.equals(Constans.STATE_200)){
            if(state.equals(Constans.STATE_404)){
                message.put("code","404");
                message.put("msg",msg);
                callbackContext.error(message);
                return true;
            }
            else if(state.equals(Constans.STATE_405)){
                message.put("code","405");
                message.put("msg",msg);
                callbackContext.error(message);
                return true;
            }
            else {
                message.put("code","-1");
                message.put("msg",msg);
                callbackContext.error(message);
                return true;
            }
        }

        CardResult relData = JsonParser.toObj(rel, CardResult.class);
        CardInfo cardInfo = relData.getResult().get(0);
        cardInfo.setCardId(relInfo.getCardId());
        if (null != rel) {
            JSONObject obj = new JSONObject(JsonParser.toJson(relData.getResult().get(0)));
            obj.put("userTypeInnerId", userTypeInnerId);
            callbackContext.success(obj);//如果不调用success回调，则js中successCallback不会执行
            return true;
        }

        return true;
    }

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
