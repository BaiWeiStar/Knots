package cn.libery.knots.ui.star;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.Constants;
import cn.libery.knots.R;
import cn.libery.knots.adapter.RecStarredAdapter;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.MySubscriber;
import cn.libery.knots.api.subscribers.SubscriberListener;
import cn.libery.knots.db.UserRecord;
import cn.libery.knots.model.Repository;
import cn.libery.knots.ui.BaseLoadingFragment;
import cn.libery.knots.utils.Logger;
import cn.libery.knots.widget.SwipeRecyclerView;

/**
 * Created by Libery on 2016/7/14.
 * Email:libery.szq@qq.com
 */
public class StarTypeFragment extends BaseLoadingFragment implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRecyclerView.OnLoadMoreListener {

    private boolean isPrepared;

    @BindView(R.id.starred_recycle)
    SwipeRecyclerView mStarredRecycle;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private String mType;
    private boolean recyclerViewIsRefresh;
    private RecStarredAdapter adapter;
    private List<Repository> mRepositories;

    public static StarTypeFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_TYPE, type);
        StarTypeFragment fragment = new StarTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_star_type;
    }

    @Override
    protected void loadData() {
        Logger.e("loadData");
        refreshData();
    }

    @Override
    protected void initView(final View view) {
        isPrepared = true;
        ButterKnife.bind(this, view);
    }

    @Override
    protected void lazyLoad() {
        if (isPrepared && mIsVisibleToUser) {
            isPrepared = false;
            Logger.e("lazyLoad");
            mType = getArguments().getString(Constants.FRAGMENT_TYPE);
            recyclerViewIsRefresh = true;
            mRepositories = new ArrayList<>();
            mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
            mRefreshLayout.setOnRefreshListener(this);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                    .VERTICAL, false);
            mStarredRecycle.setLayoutManager(manager);
            adapter = new RecStarredAdapter(mRepositories, R.layout.list_item_starred);
            mStarredRecycle.setAdapter(adapter);
            mStarredRecycle.setMoreListener(this);
            mStarredRecycle.setItemAnimator(new DefaultItemAnimator());
            refreshData();
        }
    }

    @Override
    protected void initData() {
        Logger.e("initData");
    }

    @Override
    public void onRefresh() {
        recyclerViewIsRefresh = true;
        refreshData();
    }

    @Override
    public void onLoadMore() {
        recyclerViewIsRefresh = false;
        refreshData();
    }

    private void refreshData() {
        if (isTag()) {

        } else {
            MySubscriber<List<Repository>> subscriber = new MySubscriber<>(new SubscriberListener<List<Repository>>() {
                @Override
                public void onNext(final List<Repository> repository) {
                    showContentView();
                    if (recyclerViewIsRefresh) {
                        finishRefresh();
                        mRepositories.clear();
                        mRepositories.addAll(repository);
                        adapter.notifyDataSetChanged();
                    } else {
                        mRepositories.addAll(repository);
                        adapter.notifyItemRangeChanged(mRepositories.size() - 1, repository.size());
                    }
                    mStarredRecycle.notifyMoreFinish(true);
                }

                @Override
                public void onError(final Throwable e) {
                    if (recyclerViewIsRefresh) {
                        finishRefresh();
                        showErrorView();
                    }
                    mStarredRecycle.notifyMoreFinish(true);
                }
            });
            UserRecord record = UserRecord.getUserRecord();
            if (record != null) {
                Api2.getInstance().getUserStarred(subscriber, record.login, 1, 5);
            }
            mSubscription.add(subscriber);
        }
    }

    private boolean isTag() {
        return mType.equals(Constants.FRAGMENT_TAG);
    }

    public void finishRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

}
