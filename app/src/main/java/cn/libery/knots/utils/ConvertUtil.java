package cn.libery.knots.utils;

import android.util.Base64;

import java.util.Locale;

/**
 * Created by Libery on 2016/8/11.
 * Email:libery.szq@qq.com
 */
public class ConvertUtil {

    public static final float KB = 1024.0f;
    public static final float MB = 1024 * KB;

    public static String getCodeSize(int bytes) {
        if (bytes < KB) {
            return String.valueOf(bytes);
        } else if (bytes > KB && bytes < MB) {
            return getFormatStr00(bytes / KB) + "KB";
        } else {
            return getFormatStr00(bytes / MB) + "MB";
        }
    }

    public static String getFormatStr00(float size) {
        return String.format(Locale.CHINA, "%.02f", size);
    }

    public static String decodeByBase64(String str) {
        if (str != null) {
            return new String(Base64.decode(str, Base64.DEFAULT));
        } else {
            return "";
        }

    }

}
