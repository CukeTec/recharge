package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.bean.CardInfo;
import com.run.bean.CardResult;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;

import org.apache.cordova.CallbackContext;
import com.run.receiver.ActionReceiver;
import org.apache.cordova.whitelist.HttpRequestPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengxiao on 2018/4/3.
 */

public class CardInfoExecutor extends CommandExecutor {

    @Override
    public boolean execute() throws JSONException {
        String url = actionReceiver.getUrl();
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", HttpRequestPlugin.userInnerId);
        data.put("token", HttpRequestPlugin.token);
        String rel = HttpUtil.sendRequest(url, data);
        CardResult relData = JsonParser.toObj(rel, CardResult.class);
        CardInfo cardInfo = relData.getResult().get(0);
        cardInfo.setCardId(HttpRequestPlugin.relInfo.getCardId());
        if (null != rel) {
            JSONObject obj = new JSONObject(JsonParser.toJson(relData.getResult().get(0)));
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
