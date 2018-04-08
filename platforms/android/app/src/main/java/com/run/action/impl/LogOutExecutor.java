package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.bean.CardInfo;
import com.run.bean.CardResult;
import com.run.receiver.ActionReceiver;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.whitelist.Constans;
import org.apache.cordova.whitelist.HttpRequestPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengxiao on 2018/4/3.
 */

public class LogOutExecutor extends CommandExecutor {

    @Override
    public boolean execute() throws JSONException {
        JSONObject message = new JSONObject();
        String url = actionReceiver.getUrl();
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", HttpRequestPlugin.userInnerId);
        data.put("token", HttpRequestPlugin.token);
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

        callbackContext.success("退出成功");
        return  true;
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
