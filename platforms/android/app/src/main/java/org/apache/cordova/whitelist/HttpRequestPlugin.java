package org.apache.cordova.whitelist;

import android.util.Log;

import com.run.util.HttpUtil;
import com.run.util.JsonParser;
import com.run.util.SecurityUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 前端JS调用http请求
 */
public class HttpRequestPlugin extends CordovaPlugin {
    private static String LOGIN = "login"; //HTTP请求

    /**
     * 执行http请求
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return
     * @throws JSONException
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(LOGIN.equals(action)){
            return login(args,callbackContext);
        }else{ //默认第一个参数就是数据
            String url = args.getString(0);
            String data = args.getString(1);
            String rel = HttpUtil.sendRequest(url,data);
            if(null!=rel){
                callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
                return true;
            }else{
                return false;
            }
        }

    }

    /**
     * 登陆处理
     * @param args
     * @param callbackContext
     * @return
     */
    private boolean login(JSONArray args, CallbackContext callbackContext) throws JSONException{
        String url = args.getString(0);
        String userId = args.getString(1);
        String userPassword = args.getString(2);
        Map<String,String>  data = new HashMap<>();
        data.put("userId",userId);
        data.put("userPassword",SecurityUtil.getMd5(userPassword));//MD5处理
        data.put("macid",SecurityUtil.getMac()); //获取mac地址
        String rel = HttpUtil.sendRequest(url,data);
        Map<String,Object> relData = JsonParser.toObj(rel,Map.class);
        Log.e("token",relData.get("token").toString());
        if(null!=rel){
            callbackContext.success(rel);//如果不调用success回调，则js中successCallback不会执行
            return true;
        }else{
            return false;
        }
    }
}
