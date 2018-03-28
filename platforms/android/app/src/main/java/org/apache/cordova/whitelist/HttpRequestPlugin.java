package org.apache.cordova.whitelist;

import com.run.bean.Question;
import com.run.bean.Result;
import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.SecurityUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前端JS调用http请求
 */
public class HttpRequestPlugin extends CordovaPlugin {
    private static String LOGIN = "login"; //HTTP请求
    private static String CARDINFO = "cardInfo"; //获取卡片信息
    private static String CHANGEPWD = "changepwd"; //修改密码
    private static String SETQUESTION = "setQuestion"; //设置问题
    private static Integer userInnerId;
    public static String token = "";
    public static Result relData = null; //登录返回消息


    /**
     * 执行http请求
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return
     * @throws JSONException
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (LOGIN.equals(action)) {
            return login(args, callbackContext);
        } else if (CARDINFO.equals(action)) {
            return getCardInfo(args, callbackContext);
        } else if (CHANGEPWD.equals(action)) {
            return changePassword(args, callbackContext);
        } else if (SETQUESTION.equals(action)) {
            return setQuestion(args, callbackContext);
        }  { //默认第一个参数就是数据
            String url = args.getString(0);
            String data = args.getString(1);
            String rel = HttpUtil.sendRequest(url, data);
            if (null != rel) {
                callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 登陆处理
     *
     * @param args
     * @param callbackContext
     * @return
     */
    private boolean login(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        String userId = args.getString(1);
        String userPassword = args.getString(2);
        Map<String, String> data = new HashMap<>();
        data.put("userId", userId);
        data.put("userPassword", SecurityUtil.getMd5(userPassword));//MD5处理
        data.put("macid", SecurityUtil.getMac()); //获取mac地址
        String rel = HttpUtil.sendRequest(url, data);
        relData = JsonParser.toObj(rel, Result.class);
        token = relData.getToken();
        userInnerId = relData.getResult().get(0).getUserInnerId();

        if (null != rel) {
            callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取卡片信息
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
    private boolean getCardInfo(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);

        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理

        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);

        if (null != rel) {
            callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改密码
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
    private boolean changePassword(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
        Map<String, Object> data = new HashMap<>();
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理
        data.put("oldpassword", SecurityUtil.getMd5(args.getString(1)));
        data.put("newpassword", SecurityUtil.getMd5(args.getString(2)));
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(JsonParser.toObj(rel, Map.class).get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改密码
     *
     * @param args
     * @param callbackContext
     * @return
     * @throws JSONException
     */
    private boolean setQuestion(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String url = args.getString(0);
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
        data.put("userInnerId", userInnerId);
        data.put("token", token);//MD5处理
        data.put("question", result);
        String rel = HttpUtil.sendRequest(url, data);
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        if (null != rel) {
            callbackContext.success(JsonParser.toObj(rel, Map.class).get("msg").toString());//如果不调用success回调，则js中successCallback不会执行
            return true;
        } else {
            return false;
        }
    }
}
