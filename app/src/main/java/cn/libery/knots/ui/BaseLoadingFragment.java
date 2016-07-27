package cn.libery.knots.ui;

import android.os.Bundle;
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
public abstract class BaseLoadingFragment extends BaseLazyFragment {

    private ViewGroup mContentView;
    private View mProgressBar;
    private View mErrorView;
    private View mEmptyView;
    private View[] mViews;
    private LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        View v = inflater.inflate(R.layout.fragment_base_loading_root, container, false);
        mContentView = (ViewGroup) v.findViewById(R.id.base_content);
        mProgressBar = v.findViewById(R.id.base_progressbar);
        mErrorView = v.findViewById(R.id.base_error);
        mEmptyView = v.findViewById(R.id.base_empty);

        inflater.inflate(getContentLayout(), mContentView);
        mViews = new View[]{mContentView, mProgressBar, mErrorView, mEmptyView};

        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                showLoadingView();
            }
        });
        initView(v);
        return v;
    }

    protected abstract int getContentLayout();

    protected abstract void loadData();

    public void showContentView() {
        showView(mContentView);
    }

    /**
     * 显示页面加载结果为空
     */
    public void showEmptyView(@StringRes int textRes1) {
        showEmptyView(0, textRes1, 0);
    }

    /**
     * 显示页面加载结果为空
     */
    public void showEmptyView(@DrawableRes int imageRes, @StringRes int textRes1) {
        showEmptyView(imageRes, textRes1, 0);
    }

    /**
     * 显示页面加载结果为空
     */
    public void showEmptyView(@DrawableRes int imageRes, @StringRes int textRes1, @StringRes int textRes2) {
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

    public void showErrorView() {
        showView(mErrorView);
    }

    public void showLoadingView() {
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

    public LayoutInflater getInflater() {
        return mInflater;
    }

    protected abstract void initView(View view);

}
