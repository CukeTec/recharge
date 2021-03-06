package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.receiver.ActionReceiver;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;

import static org.apache.cordova.whitelist.HttpRequestPlugin.relInfo;

/**
 * Created by Xiao on 2018/4/3.
 */

public class RechargePreExecutor extends CommandExecutor {

    @Override
    public boolean execute() throws JSONException {
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        if (relInfo == null){
            callbackContext.error("查询不到卡号");
            return true;
        }
        String cardId = relInfo.getCardId(); //卡号
        callbackContext.success(cardId);
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
