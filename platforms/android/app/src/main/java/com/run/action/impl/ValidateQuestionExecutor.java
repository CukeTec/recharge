package com.run.action.impl;

import com.run.action.CommandExecutor;
import com.run.bean.Question;
import com.run.receiver.ActionReceiver;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zengxiao on 2018/4/3.
 */

public class ValidateQuestionExecutor extends CommandExecutor {

    @Override
    public boolean execute() throws JSONException {
        CallbackContext callbackContext = actionReceiver.getCallbackContext();
        JSONArray args = actionReceiver.getParams();
        String url = actionReceiver.getUrl();
        String id = args.getString(0);
        JSONArray questions = args.getJSONArray(1);
        List<Question> result = new ArrayList<>();
        String questionJson = null;
        Question question = null;
        for(int i=0;i<questions.length();i++){
            questionJson = questions.get(i).toString();
            question = JsonParser.toObj(questionJson, Question.class);
            result.add(question);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("userId", id);
        data.put("question", result);
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        String code =  relData.get("state").toString();
        if (null != rel && "200".equals(code)) {
            callbackContext.success();//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            callbackContext.error(relData.get("msg").toString());
            return false;
        }
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
