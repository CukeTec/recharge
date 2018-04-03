package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.receiver.ActionReceiver;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.whitelist.Constans;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static org.apache.cordova.whitelist.HttpRequestPlugin.relInfo;
import static org.apache.cordova.whitelist.HttpRequestPlugin.token;
import static org.apache.cordova.whitelist.HttpRequestPlugin.userInnerId;

/**
 * Created by Xiao on 2018/4/3.
 */

public class MessageDealExecutor implements CommandExecutor {
    private ActionReceiver actionReceiver;

    public MessageDealExecutor(ActionReceiver actionReceiver) {
        this.actionReceiver = actionReceiver;
    }

    @Override
    public boolean execute() throws JSONException {
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        JSONArray args = actionReceiver.getParams();
        String url = actionReceiver.getUrl();
        JSONObject message = new JSONObject();
        if(relInfo == null){
            message.put("code", "404");
            message.put("msg","过期");
            callbackContext.error(message);
            return true;
        }
        userInnerId = relInfo.getUserInnerId();	// 用户id
        String applyInnerId = ""; //申请id
        String auditState = ""; //	审核状态  1 - 同意   2- 拒绝
        String auditDetail = ""; //理由
        if(args.length() < 1 && args != null){
            message.put("code", "-1");
            message.put("msg","请选择需要操作的解冻申请");
            callbackContext.error(message);
            return true;
        }
        applyInnerId = args.getString(0);
        auditState = args.getString(1);
        auditDetail = args.getString(2);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("applyInnerId", applyInnerId);
        data.put("auditState", auditState);
        data.put("auditDetail",auditDetail);
        data.put("token", token);
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
        callbackContext.success("操作成功");
        return true;
    }
}
