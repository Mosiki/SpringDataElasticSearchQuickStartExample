package io.github.mosiki.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 类名称：DateUtils
 * 类描述：
 * 创建人：WeJan
 * 创建时间：2018年09月04日 14:39
 */
public class DateUtils {

    public static String format(Date date, String style) {
        SimpleDateFormat df = new SimpleDateFormat(style);
        return df.format(date);
    }

    public static String getDate(Date d) {
        return format(d, "yyyy-MM-dd HH:mm:ss");
    }
}
