package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.SecurityUtil;

import org.apache.cordova.CallbackContext;
import com.run.receiver.ActionReceiver;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static org.apache.cordova.whitelist.HttpRequestPlugin.token;
import static org.apache.cordova.whitelist.HttpRequestPlugin.userInnerId;

/**
 * Created by zengxiao on 2018/4/3.
 */

public class ChangePasswordExecutor implements CommandExecutor {
    private ActionReceiver actionReceiver;

    public ChangePasswordExecutor(ActionReceiver actionReceiver) {
        this.actionReceiver = actionReceiver;
    }
    @Override
    public boolean execute() throws JSONException {
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        JSONArray args = actionReceiver.getParams();
        String url = actionReceiver.getUrl();
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);
        data.put("oldpassword", SecurityUtil.getMd5(args.getString(0)));
        data.put("newpassword", SecurityUtil.getMd5(args.getString(1)));
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(relData.get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }
}
