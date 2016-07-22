package cn.libery.knots.utils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Libery on 2016/6/1.
 * Email:libery.szq@qq.com
 */
public class AppUtil {

    public static void universalException(final Throwable e) {
        if (e instanceof SocketTimeoutException) {
            ToastUtil.showAtUI("网络连接超时，请检查网络或稍后再试");
        } else if (e instanceof ConnectException) {
            ToastUtil.showAtUI("网络错误，请检查网络或稍后再试");
        } else {
            ToastUtil.showAtUI(e.getMessage());
        }
    }

    public static String decode(String s) {
        if (s == null) {
            return null;
        }
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (Exception e) {
            // utf-8 always available
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String s) {
        if (s == null) {
            return null;
        }
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            // utf-8 always available
            e.printStackTrace();
        }
        return null;
    }

    public static void startLoginActivity() {
       /* Intent intent = LoginActivity.intent(MyApplication.mContext);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.mContext.startActivity(intent);*/
    }
}
