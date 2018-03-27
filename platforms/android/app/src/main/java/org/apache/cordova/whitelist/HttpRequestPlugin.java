package org.apache.cordova.whitelist;

import android.util.Log;

import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.SecurityUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 前端JS调用http请求
 */
public class HttpRequestPlugin extends CordovaPlugin {
    private static String LOGIN = "login"; //HTTP请求
    private static String CARDINFO = "cardInfo"; //获取卡片信息
    private static String CHANGEPWD = "changepwd"; //修改密码
    private static Double userInnerId = 0d;
    public static String token = "";

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
        } else { //默认第一个参数就是数据
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
        Map<String, Object> relData = JsonParser.toObj(rel, Map.class);
        token = relData.get("token").toString();
        ArrayList<Map<String,Object>> result = (ArrayList<Map<String, Object>>) relData.get("result");
        Map<String, Object> map = result.get(0);
        userInnerId = ((Double) map.get("userInnerId"));
        Log.e("token", relData.get("token").toString());
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
        String userId = args.getString(1);

        Map<String, String> data = new HashMap<>();
        data.put("userInnerId", userId);
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
}
