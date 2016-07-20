package cn.libery.knots;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Libery on 2016/7/20.
 * Email:libery.szq@qq.com
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }

}
