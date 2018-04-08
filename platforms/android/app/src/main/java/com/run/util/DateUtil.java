package com.run.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 */
public class DateUtil {

    public static String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化
     *
     * @param date
     * @param formater
     * @return
     */
    public static String formatDate(Date date, String formater){
        SimpleDateFormat sdf = new SimpleDateFormat(formater);

        return sdf.format(date);
    }


}
