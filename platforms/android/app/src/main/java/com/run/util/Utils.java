package com.run.util;

import java.util.regex.Pattern;

public class Utils {

    /**
     * 字符 转 正整数 ，如 2.0 转 2
     *
     * @param str
     * @return
     */
    public static int strToInt(String str){
        if(isInteger(str)){

            return Integer.parseInt(str);
        }

        boolean flag = str.contains(".");
        if(flag){
            String[] value = str.split("\\.");

            return Integer.parseInt(value[0]);
        }

        return 0;
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
