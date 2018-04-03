package com.run.invoker;

import com.run.action.CommandExecutor;

import org.json.JSONException;

/**
 * Created by zengxiao on 2018/4/2.
 */

public class ActionInvoker {
    private CommandExecutor commandExecutor;

    public ActionInvoker(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public boolean invoke() throws JSONException {
        return commandExecutor.execute();
    }
}
