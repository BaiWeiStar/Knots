package cn.libery.knots.ui;

import android.content.Intent;
import android.os.Handler;

import cn.libery.knots.Constants;
import cn.libery.knots.R;
import cn.libery.knots.utils.SharedPreferUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void obtainParam(final Intent intent) {

    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpActivity();
            }
        }, 1200);
    }

    private void jumpActivity() {
        if (SharedPreferUtil.get(Constants.SHARE_FIRST_START, false)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, GuideActivity.class));
        }
        finish();
    }

    @Override
    protected void initData() {

    }
}
