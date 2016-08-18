package cn.libery.knots.utils;

import android.content.Context;
import android.content.Intent;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import cn.libery.knots.R;
import cn.libery.knots.api.Api;

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

    public static void shareBlob(Context context, String owner, String repo, String branch, String path) {
        StringBuilder builder = new StringBuilder(Api.BASE_DAILY_URL);
        builder.append(owner);
        builder.append("/");
        builder.append(repo);
        builder.append("/blob/");
        builder.append(branch);
        builder.append("/");
        builder.append(path);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, builder.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

    public static void shareRep(Context context, String owner, String repo) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, Api.BASE_DAILY_URL + owner + "/" + repo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

}
