package com.run.receiver;

import org.apache.cordova.CallbackContext;

import com.run.action.CommandExecutor;
import com.run.enumeration.ActionCommand;
import com.run.invoker.ActionInvoker;

import org.json.JSONArray;

/**
 * Created by zengxiao on 2018/4/2.
 */

public class ActionReceiver {
    private String url;
    private JSONArray params;
    private CallbackContext callbackContext;
    private CommandExecutor commandExecutor;

    public ActionReceiver(String action, JSONArray params, CallbackContext callbackContext) {
        ActionCommand actionCommand = ActionCommand.valueOf(action);
        this.url = actionCommand.getUrl();
        this.params = params;
        this.callbackContext = callbackContext;
        try {
            commandExecutor = (CommandExecutor) Class.forName(actionCommand.getClassName()).newInstance();
            commandExecutor.setActionReceiver(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
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
        return commandExecutor;
    }

}
