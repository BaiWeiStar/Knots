package cn.libery.knots.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.libery.knots.R;
import cn.libery.knots.api.subscribers.SubscriberListener;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    public CompositeSubscription mSubscription;

    protected abstract static class ResultListener<T> implements SubscriberListener<T> {
        @Override
        public void onError(final Throwable e) {

        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        if (mSubscription == null) {
            mSubscription = new CompositeSubscription();
        }

        if (getIntent() != null) {
            obtainParam(getIntent());
        }

        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSubscription != null) {
            mSubscription.clear();
        }
    }

    protected abstract int getContentView();

    protected abstract void obtainParam(Intent intent);

    protected abstract void initView();

    protected abstract void initData();

    public void initToolbar(int titleRes) {
        String title = getString(titleRes);
        initToolbar(title);
    }

    public void initToolbar(int titleRes, int backIconRes) {
        String title = getString(titleRes);
        initToolbar(title, backIconRes);
    }

    public void initToolbar(String title) {
        initToolbar(title, 0);
    }

    public void initToolbar(String title, int backIconRes) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleText = (TextView) toolbar.findViewById(R.id.title);
        titleText.setText(title);
        setSupportActionBar(toolbar);
        setActionBar(backIconRes);
    }

    public void initToolbar(String title, String rightMenu, final OnToolbarMenuClickListener listener) {
        initToolbar(title, 0, rightMenu, listener);
    }

    public void initToolbar(String title, int backIconRes, String rightMenu, final OnToolbarMenuClickListener
            listener) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleText = (TextView) toolbar.findViewById(R.id.title);
        titleText.setText(title);
        TextView menuText = (TextView) toolbar.findViewById(R.id.toolbar_right_text_menu);
        menuText.setVisibility(View.VISIBLE);
        menuText.setText(rightMenu);
        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick();
            }
        });
        setSupportActionBar(toolbar);
        setActionBar(backIconRes);
    }

    public void initToolbar(int titleRes, int rightMenuRes, final OnToolbarMenuClickListener listener) {
        initToolbar(getString(titleRes), getString(rightMenuRes), listener);
    }

    public void initToolbar(String title, int rightMenuRes, final OnToolbarMenuClickListener listener) {
        initToolbar(title, getString(rightMenuRes), listener);
    }

    private void setActionBar(int backIconRes) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
            if (backIconRes != 0) {
                ab.setHomeAsUpIndicator(backIconRes);
            } else {
                ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
            }
        }
    }

    public void setTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleText = (TextView) toolbar.findViewById(R.id.title);
        titleText.setText(title);
    }

    public void setSubtitle(String subtitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView subtitleText = (TextView) toolbar.findViewById(R.id.subtitle);
        if (subtitleText != null) {
            subtitleText.setVisibility(View.VISIBLE);
            subtitleText.setText(subtitle);
        }
    }

    public interface OnToolbarMenuClickListener {
        void onItemClick();
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    public void onToolbarBackPressed() {
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onToolbarBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
