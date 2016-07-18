package cn.libery.knots.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public abstract class BaseLazyFragment extends BaseFragment {
    protected boolean mIsVisibleToUser;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyLoad();
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisibleToUser = true;
            onVisible();
        } else {
            mIsVisibleToUser = false;
            onInvisible();
        }
    }

    protected abstract void lazyLoad();

    public void onVisible() {
        lazyLoad();
    }

    public void onInvisible() {

    }

    protected abstract void initData();
}
