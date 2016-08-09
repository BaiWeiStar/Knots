package cn.libery.knots.ui;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.libery.knots.R;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public abstract class BaseLoadingActivity extends BaseActivity {

    ViewGroup mContentView;
    View mProgressBar;
    View mErrorView;
    View mEmptyView;
    private View[] mViews;


    @Override
    protected int getContentView() {
        return R.layout.activity_base_loading_root;
    }

    @Override
    protected void initView() {
        mContentView = (ViewGroup) findViewById(R.id.base_content);
        mProgressBar = findViewById(R.id.base_progressbar);
        mErrorView = findViewById(R.id.base_error);
        mEmptyView = findViewById(R.id.base_empty);
        mViews = new View[]{mContentView, mProgressBar, mErrorView, mEmptyView};

        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoad();
                loadData();
            }
        });
    }

    protected abstract void loadData();

    public void setLoadingContentView(int contentLayoutId) {
        LayoutInflater.from(this).inflate(contentLayoutId, mContentView, true);
    }

    public void setStatusLoading() {
        showView(mProgressBar);
    }

    public void showContentView() {
        showView(mContentView);
    }

    public void showErrorView() {
        showView(mErrorView);
    }

    /**
     * 显示页面加载结果为空
     */
    public void setStatusEmpty(@StringRes int textRes1) {
        setStatusEmpty(0, textRes1, 0);
    }

    /**
     * 显示页面加载结果为空
     */
    public void setStatusEmpty(@DrawableRes int imageRes, @StringRes int textRes1, @StringRes int textRes2) {
        showView(mEmptyView);
        if (imageRes != 0) {
            ImageView image = (ImageView) mEmptyView.findViewById(R.id.base_empty_image);
            image.setVisibility(View.VISIBLE);
            image.setImageResource(imageRes);
        }
        if (textRes1 != 0) {
            TextView text1 = (TextView) mEmptyView.findViewById(R.id.base_empty_text1);
            text1.setVisibility(View.VISIBLE);
            text1.setText(textRes1);
        }
        if (textRes2 != 0) {
            TextView text2 = (TextView) mEmptyView.findViewById(R.id.base_empty_text2);
            text2.setVisibility(View.VISIBLE);
            text2.setText(textRes2);
        }
    }

    /**
     * 显示加载中动画
     */
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 重新加载
     */
    public void reLoad() {
        showView(mProgressBar);
    }

    private void showView(View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        for (View v : mViews) {
            if (view != v) {
                v.setVisibility(View.GONE);
            }
        }
    }
}
