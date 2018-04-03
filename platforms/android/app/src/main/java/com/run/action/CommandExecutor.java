package com.run.action;

import org.json.JSONException;

/**
 * Created by Xiao on 2018/4/2.
 */

public interface CommandExecutor {
    boolean execute() throws JSONException;
}
