package cn.libery.knots.ui.my;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.R;
import cn.libery.knots.api.Api;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.MySubscriber;
import cn.libery.knots.api.subscribers.SubscriberListener;
import cn.libery.knots.api.websource.WebApiProvider;
import cn.libery.knots.db.UserRecord;
import cn.libery.knots.model.User;
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
    @BindView(R.id.my_follows)
    TextView mMyFollows;
    @BindView(R.id.my_starred)
    TextView mMyStarred;
    @BindView(R.id.my_following)
    TextView mMyFollowing;
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
            getData();
        }
    }

    private void getData() {
        UserRecord record = UserRecord.getUserRecord();
        if (record != null) {
            refreshUserProfile(record.login);
            showContentView();
            refreshUI(record);
            refreshStarred(record);
        } else {
            showErrorView();
        }
    }

    private void refreshStarred(final UserRecord record) {
        new StarredTask(this, record).execute();
    }

    private void refreshUI(UserRecord record) {
        Logger.e("id = %s", record.id);
        mMyAvatar.setImageUrl(record.avatar_url, 15);
        mMyLogin.setText(record.login);
        mMyName.setText(record.name);
        mMyLocation.setText(record.location);
        mMyEmail.setText(record.email);
        mMyBlog.setText(record.blog);
        mMyFollows.setText(String.valueOf(record.followers));
        mMyStarred.setText(String.valueOf(record.starred));
        mMyFollowing.setText(String.valueOf(record.following));
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
        getData();
    }

    private void refreshUserProfile(String name) {
        SubscriberListener<User> listener = new SubscriberListener<User>() {
            @Override
            public void onNext(final User user) {
                UserRecord record = UserRecord.saveUser(user, false);
                refreshUI(record);
            }

            @Override
            public void onError(final Throwable e) {

            }
        };
        MySubscriber<User> subscriber = new MySubscriber<>(listener);
        Api2.getInstance().userProfile(subscriber, name);
        mSubscription.add(subscriber);
    }

    private static class StarredTask extends AsyncTask<Void, Void, User> {
        private MyFragment mFragment;
        private UserRecord mRecord;

        public StarredTask(MyFragment fragment, UserRecord record) {
            mFragment = fragment;
            mRecord = record;
        }

        @Override
        protected User doInBackground(final Void... params) {
            WebApiProvider provider = new WebApiProvider(Api.BASE_DAILY_URL, WebSettings.getDefaultUserAgent(mFragment
                    .getActivity()));
            try {
                return provider.getUser(mRecord.login);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final User user) {
            super.onPostExecute(user);
            if (user != null) {
                mRecord = UserRecord.updateStarred(user);
                mFragment.mMyStarred.setText(String.valueOf(mRecord.starred));
            }
        }
    }

}
