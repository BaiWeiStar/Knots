package cn.libery.knots.ui.star;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.Constants;
import cn.libery.knots.R;
import cn.libery.knots.adapter.RecStarredAdapter;
import cn.libery.knots.adapter.SuperAdapter;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.MySubscriber;
import cn.libery.knots.db.UserRecord;
import cn.libery.knots.model.Repository;
import cn.libery.knots.ui.BaseLoadingFragment;
import cn.libery.knots.utils.CheckUtil;
import cn.libery.knots.utils.ToastUtil;

/**
 * Created by Libery on 2016/7/14.
 * Email:libery.szq@qq.com
 */
public class StarTypeFragment extends BaseLoadingFragment implements XRecyclerView.LoadingListener {

    private boolean isPrepared;

    @BindView(R.id.starred_recycle)
    XRecyclerView mStarredRecycle;

    private String mType;
    private boolean recyclerViewIsRefresh;
    private boolean isFirstStart;//判断第一次加载 为真则加载失败时显示ErrorView
    private RecStarredAdapter adapter;
    private static final int PAGE_SIZE = 20;
    private int mPage = 1;

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
            isFirstStart = true;
            mType = getArguments().getString(Constants.FRAGMENT_TYPE);
            recyclerViewIsRefresh = true;
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                    .VERTICAL, false);
            mStarredRecycle.setLayoutManager(manager);
            adapter = new RecStarredAdapter(null, R.layout.list_item_starred);
            mStarredRecycle.setAdapter(adapter);
            mStarredRecycle.setItemAnimator(new DefaultItemAnimator());
            mStarredRecycle.setLoadingListener(this);
            mStarredRecycle.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
            mStarredRecycle.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
            refreshData();
            adapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
                @Override
                public void onClick(final View view, int position) {
                    position--;
                    Repository rep = adapter.getItem(position);
                    ToastUtil.showAtUI(rep.getName());
                }
            });
        }
    }

    @Override
    protected void initData() {
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

    private void refreshData() {
        if (isTag()) {
            refreshTagStarred();
        } else {
            refreshCurrentStarred();
        }
    }

    private void refreshTagStarred() {

    }

    private void refreshCurrentStarred() {
        MySubscriber<List<Repository>> subscriber = new MySubscriber<>(new ResultListener<List<Repository>>() {
            @Override
            public void onNext(final List<Repository> repository) {
                showContentView();
                if (CheckUtil.isNotNullByList(repository)) {
                    mPage++;
                    if (recyclerViewIsRefresh) {
                        adapter.update(repository);
                        mStarredRecycle.refreshComplete();
                    } else {
                        mStarredRecycle.loadMoreComplete();
                        adapter.addAll(repository);
                    }
                } else {
                    ToastUtil.showAtUI("无更多数据");
                    mStarredRecycle.loadMoreComplete();
                }
            }

            @Override
            public void onError(final Throwable e) {
                if (recyclerViewIsRefresh && isFirstStart) {
                    showErrorView();
                    isFirstStart = false;
                    mStarredRecycle.refreshComplete();
                } else if (recyclerViewIsRefresh) {
                    mStarredRecycle.refreshComplete();
                } else {
                    mStarredRecycle.loadMoreComplete();
                }
            }
        });
        UserRecord record = UserRecord.getUserRecord();
        if (record != null) {
            Api2.getInstance().getUserStarred(subscriber, record.login, mPage, PAGE_SIZE);
        }
        mSubscription.add(subscriber);
    }

    private boolean isTag() {
        return mType.equals(Constants.FRAGMENT_TAG);
    }

}
