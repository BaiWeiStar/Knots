package cn.libery.knots.utils;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by Libery on 2016/8/2.
 * Email:libery.szq@qq.com
 */
public class CheckUtil {

    public static boolean isNotNullByList(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isNotNull(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static boolean isNull(String str) {
        return TextUtils.isEmpty(str);
    }

}
