package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.receiver.ActionReceiver;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.Utils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.whitelist.Constans;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.apache.cordova.whitelist.HttpRequestPlugin.relInfo;
import static org.apache.cordova.whitelist.HttpRequestPlugin.token;
import static org.apache.cordova.whitelist.HttpRequestPlugin.userInnerId;

/**
 * 充值记录
 *
 * Created by liudong on 2018-4-4.
 */

public class RechargeRecordExecutor extends CommandExecutor {



    @Override
    public boolean execute() throws JSONException {
        JSONObject message = new JSONObject();

        JSONArray args = actionReceiver.getParams();
        String url = actionReceiver.getUrl();
        CallbackContext callbackContext = actionReceiver.getCallbackContext();

        String page = args.getString(0); //页数
        String rows = args.getString(1); //每页显示条数

        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("cardId", relInfo.getCardId());

        data.put("startDate", "");//MD5处理
        data.put("endDate", "");//MD5处理
        data.put("page", page);//MD5处理
        data.put("rows", rows);//MD5处理
        data.put("token", token);//MD5处理

        data.put("token", token);//MD5处理
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

        int total = Utils.strToInt(String.valueOf(relData.get("total"))); //总条数
        int currpage = Utils.strToInt(String.valueOf(relData.get("page"))); //当前页

        int ypage = total%(Integer.parseInt(rows));
        int totalpage = total/Integer.parseInt(rows);
        if(ypage > 0 && total < Integer.parseInt(rows)){
            totalpage = totalpage + 1;
        }

        ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>)relData.get("result");

        message.put("total",total);
        message.put("currpage",currpage);
        message.put("totalpage",totalpage);
        message.put("pagesize",rows);
        message.put("result",result);
        callbackContext.success(JsonParser.toJson(message));//如果不调用success回调，则js中successCallback不会执行
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
