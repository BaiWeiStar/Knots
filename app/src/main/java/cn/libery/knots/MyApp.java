package cn.libery.knots;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Libery on 2016/7/20.
 * Email:libery.szq@qq.com
 */
public class MyApp extends Application {

    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        LeakCanary.install(this);
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    public static MyApp getInstance() {
        return mInstance;
    }
}
