package cn.libery.knots.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    @BindView(R.id.my_login)
    TextView mMyLogin;
    @BindView(R.id.my_name)
    TextView mMyName;
    @BindView(R.id.my_location)
    TextView mMyLocation;
    @BindView(R.id.my_email)
    TextView mMyEmail;
    @BindView(R.id.my_blog)
    TextView mMyBlog;
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
            showContentView();
            if (record != null) {
                Logger.e("id = %s", record.id);
                mMyAvatar.setImageUrl(record.avatar_url);
                mMyLogin.setText(record.login);
                mMyName.setText(record.name);
                mMyLocation.setText(record.location);
                mMyEmail.setText(record.email);
                mMyBlog.setText(record.blog);
            }

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
