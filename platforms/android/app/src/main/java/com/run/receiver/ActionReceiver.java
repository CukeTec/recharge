package com.run.receiver;

import org.apache.cordova.CallbackContext;
import com.run.enumeration.ActionCommand;
import org.json.JSONArray;

/**
 * Created by zengxiao on 2018/4/2.
 */

public class ActionReceiver {
    private String url;
    private JSONArray params;
    private CallbackContext callbackContext;

    public ActionReceiver(String action, JSONArray params, CallbackContext callbackContext) {
        this.url = ActionCommand.valueOf(action).getUrl();
        this.params = params;
        this.callbackContext = callbackContext;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONArray getParams() {
        return params;
    }

    public void setParams(JSONArray params) {
        this.params = params;
    }

    public CallbackContext getCallbackContext() {
        return callbackContext;
    }

    public void setCallbackContext(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }
}
