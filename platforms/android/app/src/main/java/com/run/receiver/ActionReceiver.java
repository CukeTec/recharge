package com.run.receiver;

import org.apache.cordova.CallbackContext;

import com.run.action.CommandExecutor;
import com.run.bean.HttpRequestBean;
import com.run.collections.CommandExecutorCollection;

import org.json.JSONArray;

/**
 * Created by zengxiao on 2018/4/2.
 */

public class ActionReceiver {
    private String action;
    private String url;
    private JSONArray params;
    private CallbackContext callbackContext;

    public ActionReceiver(String action, JSONArray params, CallbackContext callbackContext) {
        this.action = action;
        this.params = params;
        this.callbackContext = callbackContext;
    }

    public String getUrl() {
        return url;
    }

    public JSONArray getParams() {
        return params;
    }

    public CallbackContext getCallbackContext() {
        return callbackContext;
    }

    public CommandExecutor getCommandExecutor() {
        HttpRequestBean requestBean = CommandExecutorCollection.getInstance().get(action);
        this.url = requestBean.getUrl();
        CommandExecutor commandExecutor = requestBean.getCommandExecutor();
        commandExecutor.setActionReceiver(this);
        return commandExecutor;
    }

}
