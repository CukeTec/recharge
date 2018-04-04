package com.run.action;

import com.run.action.impl.GetMessageExecutor;
import com.run.receiver.ActionReceiver;

import org.json.JSONException;

/**
 * Created by zengxiao on 2018/4/2.
 */

public abstract class CommandExecutor {
    public abstract boolean execute() throws JSONException;
    public abstract ActionReceiver getActionReceiver();
    public abstract void setActionReceiver(ActionReceiver actionReceiver) ;
}
