package cn.libery.knots.ui;

import android.os.Bundle;
import android.view.View;

import cn.libery.knots.R;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public class MyFragment extends BaseLazyFragment {

    private boolean isPrepared;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        if (isPrepared && mIsVisibleToUser) {
            isPrepared = false;

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(final View view) {
        isPrepared = true;
    }

    @Override
    protected void initData() {

    }
}
