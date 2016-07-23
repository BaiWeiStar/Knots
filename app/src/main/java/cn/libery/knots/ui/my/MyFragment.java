package cn.libery.knots.ui.my;

import android.os.Bundle;
import android.view.View;

import cn.libery.knots.R;
import cn.libery.knots.db.UserRecord;
import cn.libery.knots.ui.BaseLoadingFragment;
import cn.libery.knots.utils.Logger;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public class MyFragment extends BaseLoadingFragment {

    private boolean isPrepared;
    private UserRecord mRecord;

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
            mRecord = UserRecord.getUserRecord(getActivity());
            Logger.e("id = %s", mRecord.id);
            showContentView();
        }
    }

    @Override
    protected void initView(final View view) {
        isPrepared = true;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void loadData() {

    }
}
