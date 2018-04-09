package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.bean.Result;
import com.run.util.Encript;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.SecurityUtil;

import org.apache.cordova.CallbackContext;
import com.run.receiver.ActionReceiver;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static org.apache.cordova.whitelist.HttpRequestPlugin.userId;
import static org.apache.cordova.whitelist.HttpRequestPlugin.userInnerId;
import static org.apache.cordova.whitelist.HttpRequestPlugin.relData;
import static org.apache.cordova.whitelist.HttpRequestPlugin.token;
import static org.apache.cordova.whitelist.HttpRequestPlugin.relInfo;

/**
 * Created by zengxiao on 2018/4/2.
 */
public class LoginExecutor extends CommandExecutor {

    @Override
    public boolean execute() throws JSONException {
        JSONArray args = actionReceiver.getParams();
        String url = actionReceiver.getUrl();
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        Map<String, String> data = new HashMap<>();
        userId = args.getString(0);
        data.put("userId", args.getString(0));
        data.put("userPassword", Encript.md5(args.getString(1)));//MD5处理
        data.put("macid", SecurityUtil.getMac()); //获取mac地址
        String rel = HttpUtil.sendRequest(url, data);
        relData = JsonParser.toObj(rel, Result.class);
        if (null != rel && "200".equals(relData.getState())) {
            token = relData.getToken();
            relInfo = relData.getResult().get(0);
            userInnerId = relInfo.getUserInnerId();
            callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            callbackContext.error(relData.getMsg());
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
