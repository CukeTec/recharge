package com.run.collections;

import com.run.action.CommandExecutor;
import com.run.bean.HttpRequestBean;

import java.util.HashMap;

public class CommandExecutorCollection extends HashMap<String, HttpRequestBean> {
    private static CommandExecutorCollection collection;
    private String packageName;
    private CommandExecutorCollection(){}

    public static CommandExecutorCollection getInstance(){
        if (null == collection){
            synchronized (CommandExecutorCollection.class){
                if (null == collection){
                    collection = new CommandExecutorCollection();
                }
            }
        }
        return collection;
    }

    public CommandExecutor put(String key, String value, HttpRequestBean requestBean) {
        CommandExecutor executor = null;
        try {
            Class<?> aClass = Class.forName(packageName + value);
            executor = (CommandExecutor) aClass.newInstance();
            requestBean.setCommandExecutor(executor);
            this.put(key, requestBean);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return executor;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
