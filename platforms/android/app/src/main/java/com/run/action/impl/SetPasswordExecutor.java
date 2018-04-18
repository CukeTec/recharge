package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.bean.Result;
import com.run.receiver.ActionReceiver;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.SecurityUtil;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static org.apache.cordova.whitelist.HttpRequestPlugin.relData;

/**
 * Created by zengxiao on 2018/4/4.
 */

public class SetPasswordExecutor extends CommandExecutor {
    @Override
    public boolean execute() throws JSONException {
        String url = actionReceiver.getUrl();
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        JSONArray params = actionReceiver.getParams();
        String account = params.getString(0);
        String password = params.getString(1);
        Map<String, String> data = new HashMap<>();
        data.put("userId", account);
        data.put("userPassword", SecurityUtil.getMd5(password));
        String rel = HttpUtil.sendRequest(url, data);
        relData = JsonParser.toObj(rel, Result.class);
        if (null != rel && "200".equals(relData.getState())) {
            callbackContext.success(relData.getMsg());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            HashMap<String, String> result = new HashMap<>();
            result.put("msg", relData.getMsg());
            result.put("code", relData.getState());
            callbackContext.error(JsonParser.toJson(result));
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
