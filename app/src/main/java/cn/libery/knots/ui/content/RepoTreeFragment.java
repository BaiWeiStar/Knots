package cn.libery.knots.ui.content;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.Constants;
import cn.libery.knots.R;
import cn.libery.knots.adapter.CodeAdapter;
import cn.libery.knots.adapter.SuperAdapter;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.MySubscriber;
import cn.libery.knots.model.code.GitTree;
import cn.libery.knots.model.code.Tree;
import cn.libery.knots.model.code.TreeCompare;
import cn.libery.knots.ui.BaseFragment;
import cn.libery.knots.ui.BaseLoadingFragment;

/**
 * Created by Libery on 2016/8/16.
 * Email:libery.szq@qq.com
 */
public class RepoTreeFragment extends BaseLoadingFragment {

    private CodeAdapter mAdapter;
    private String mSha;
    private String mOwner;
    private String mRepo;
    private RepoDetailActivity mActivity;

    @BindView(R.id.tree_recycle)
    RecyclerView mTreeRecycle;


    public static RepoTreeFragment newInstance(String owner, String repo, String sha) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_REPO_SHA, sha);
        args.putString(Constants.EXTRA_REPO_OWNER, owner);
        args.putString(Constants.EXTRA_REPO_NAME, repo);
        RepoTreeFragment fragment = new RepoTreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mActivity = ((RepoDetailActivity) context);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_repo_tree;
    }

    @Override
    protected void loadData() {
        getTree(mSha, true);
    }

    @Override
    protected void initView(final View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {
        mSha = getArguments().getString(Constants.EXTRA_REPO_SHA);
        mOwner = getArguments().getString(Constants.EXTRA_REPO_OWNER);
        mRepo = getArguments().getString(Constants.EXTRA_REPO_NAME);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .VERTICAL, false);
        mTreeRecycle.setLayoutManager(manager);
        mAdapter = new CodeAdapter(null, R.layout.list_item_repo_code);
        mTreeRecycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                GitTree tree = mAdapter.getItem(position);
                if (tree.isBlob()) {
                    startActivity(RepoBlobActivity.intent(getActivity(), mOwner, mRepo, tree.getSha()));
                } else {
                    getTree(tree.getSha(), true);
                    mActivity.setCodeTree(tree);
                }
            }
        });
        getTree(mSha, true);
    }

    public void getTree(String sha, final boolean isFragment) {
        final BaseFragment.ResultListener<Tree> listener = new BaseFragment.ResultListener<Tree>() {
            @Override
            public void onNext(final Tree tree) {
                if (isFragment) {
                    showContentView();
                } else {
                    mActivity.removeItemAll();
                }
                List<GitTree> trees = tree.getTree();
                Collections.sort(trees, new TreeCompare());
                mAdapter.update(trees);
            }

            @Override
            public void onError(final Throwable e) {
                super.onError(e);
                if (isFragment) {
                    showErrorView();
                }
            }
        };
        MySubscriber<Tree> subscriber = new MySubscriber<>(listener);
        Api2.getInstance().getReposTree(subscriber, mOwner, mRepo, sha);
        mSubscription.add(subscriber);
    }

}
