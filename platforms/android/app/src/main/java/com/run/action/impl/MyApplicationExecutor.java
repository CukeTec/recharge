package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.bean.CardInfo;
import com.run.bean.CardResult;
import com.run.receiver.ActionReceiver;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;

import org.apache.cordova.CallbackContext;
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
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        String url = actionReceiver.getUrl();
        Integer userTypeInnerId = 1;
        if(relInfo != null){
            userTypeInnerId = relInfo.getUserTypeInnerId();
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
            obj.put("userTypeInnerId", userTypeInnerId);
            callbackContext.success(obj);//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
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
