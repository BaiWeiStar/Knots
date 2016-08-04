package cn.libery.knots.ui.repository;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.R;
import cn.libery.knots.adapter.RecStarredAdapter;
import cn.libery.knots.adapter.SuperAdapter;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.MySubscriber;
import cn.libery.knots.db.UserRecord;
import cn.libery.knots.model.Repository;
import cn.libery.knots.ui.BaseLoadingFragment;
import cn.libery.knots.utils.CheckUtil;
import cn.libery.knots.utils.Logger;
import cn.libery.knots.utils.ToastUtil;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public class RepFragment extends BaseLoadingFragment implements XRecyclerView.LoadingListener {

    @BindView(R.id.rep_recycle)
    XRecyclerView mRepRecycle;

    private boolean isPrepared;

    private boolean recyclerViewIsRefresh;
    private boolean isFirstStart;//判断第一次加载 为真则加载失败时显示ErrorView
    private RecStarredAdapter adapter;
    private static final int PAGE_SIZE = 20;
    private int mPage = 1;

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
            Logger.e("lazyLoad");
            recyclerViewIsRefresh = true;
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                    .VERTICAL, false);
            mRepRecycle.setLayoutManager(manager);
            adapter = new RecStarredAdapter(null, R.layout.list_item_starred);
            mRepRecycle.setAdapter(adapter);
            mRepRecycle.setItemAnimator(new DefaultItemAnimator());
            mRepRecycle.setLoadingListener(this);
            mRepRecycle.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
            mRepRecycle.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
            adapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
                @Override
                public void onClick(final View view, int position) {
                    position--;
                    Repository rep = adapter.getItem(position);
                    ToastUtil.showAtUI(rep.getName());
                }
            });
            refreshData();
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_rep;
    }

    @Override
    protected void loadData() {
        refreshData();
    }

    @Override
    protected void initView(final View view) {
        isPrepared = true;
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {
    }

    private void refreshData() {
        MySubscriber<List<Repository>> subscriber = new MySubscriber<>(new ResultListener<List<Repository>>() {
            @Override
            public void onNext(final List<Repository> repository) {
                showContentView();
                if (CheckUtil.isNotNullByList(repository)) {
                    mPage++;
                    if (recyclerViewIsRefresh) {
                        mRepRecycle.refreshComplete();
                        adapter.update(repository);
                    } else {
                        mRepRecycle.loadMoreComplete();
                        adapter.addAll(repository);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showAtUI("无更多数据");
                    mRepRecycle.loadMoreComplete();
                }
            }

            @Override
            public void onError(final Throwable e) {
                if (recyclerViewIsRefresh && isFirstStart) {
                    showErrorView();
                    isFirstStart = false;
                    mRepRecycle.refreshComplete();
                } else if (recyclerViewIsRefresh) {
                    mRepRecycle.refreshComplete();
                } else {
                    mRepRecycle.loadMoreComplete();
                }
            }
        });
        UserRecord record = UserRecord.getUserRecord();
        if (record != null) {
            Api2.getInstance().getUserRepos(subscriber, record.login, mPage, PAGE_SIZE);
        }
        mSubscription.add(subscriber);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        recyclerViewIsRefresh = true;
        refreshData();
    }

    @Override
    public void onLoadMore() {
        recyclerViewIsRefresh = false;
        refreshData();
    }

}
