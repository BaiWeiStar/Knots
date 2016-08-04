package cn.libery.knots.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Libery on 2016/7/27.
 * Email:libery.szq@qq.com
 */
public class TimeUtil {

    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static final SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

    public static String yyyyMMdd(Date date) {
        if (date != null) {
            return yyyyMMdd.format(date);
        } else {
            return "";
        }
    }

    public static String yyyyMMddHHmm(Date date) {
        if (date != null) {
            return yyyyMMddHHmm.format(date);
        } else {
            return "";
        }
    }
}
