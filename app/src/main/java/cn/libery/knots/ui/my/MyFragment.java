package cn.libery.knots.ui.my;

import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.R;
import cn.libery.knots.db.UserRecord;
import cn.libery.knots.ui.BaseLoadingFragment;
import cn.libery.knots.utils.Logger;
import cn.libery.knots.widget.SmartImageView;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public class MyFragment extends BaseLoadingFragment {

    @BindView(R.id.my_avatar)
    SmartImageView mMyAvatar;
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
            final UserRecord record = UserRecord.getUserRecord(getActivity());
            Logger.e("id = %s", record.id);
            showContentView();
            mMyAvatar.setImageUrl(record.avatar_url);
        }
    }

    @Override
    protected void initView(final View view) {
        isPrepared = true;
        ButterKnife.bind(this, view);
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
