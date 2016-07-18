package cn.libery.knots.ui;

import android.os.Bundle;
import android.view.View;

import cn.libery.knots.R;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public class RepFragment extends BaseLoadingFragment {

    private boolean isPrepared;

    public static RepFragment newInstance() {
        Bundle args = new Bundle();
        RepFragment fragment = new RepFragment();
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
    protected int getContentLayout() {
        return R.layout.fragment_rep;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(final View view) {
        isPrepared = true;
    }

    @Override
    protected void initData() {

    }

}
