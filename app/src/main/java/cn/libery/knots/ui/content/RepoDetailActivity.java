package cn.libery.knots.ui.content;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.Constants;
import cn.libery.knots.Intents;
import cn.libery.knots.R;
import cn.libery.knots.adapter.CodeTreeAdapter;
import cn.libery.knots.adapter.SuperAdapter;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.MySubscriber;
import cn.libery.knots.model.code.GitTree;
import cn.libery.knots.model.code.Reference;
import cn.libery.knots.ui.BaseLoadingActivity;
import cn.libery.knots.utils.AppUtil;
import cn.libery.knots.utils.ConvertUtil;

/**
 * Created by Libery on 2016/8/8.
 * Email:libery.szq@qq.com
 */
public class RepoDetailActivity extends BaseLoadingActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.code_branch)
    TextView mCodeBranch;
    @BindView(R.id.code_tree)
    RecyclerView mCodeTree;
    private String mOwner, mRepo;
    private CodeTreeAdapter mAdapter;
    private RepoTreeFragment mFragment;
    private int mPosition = 0;
    private boolean isClearAdapter;

    public String getCodeBranch() {
        return mCodeBranch.getText().toString();
    }

    public static Intent intent(Context context, String owner, String repo) {
        return new Intents.Builder().setClass(context, RepoDetailActivity.class)
                .add(Constants.EXTRA_REPO_OWNER, owner)
                .add(Constants.EXTRA_REPO_NAME, repo)
                .toIntent();
    }

    @Override
    protected void loadData() {
        getRepoData();
    }

    @Override
    protected void obtainParam(final Intent intent) {
        mOwner = intent.getStringExtra(Constants.EXTRA_REPO_OWNER);
        mRepo = intent.getStringExtra(Constants.EXTRA_REPO_NAME);
    }

    @Override
    protected void initView() {
        super.initView();
        setLoadingContentView(R.layout.activity_repo_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        final LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager
                .HORIZONTAL, false);
        mCodeTree.setLayoutManager(manager);
        mAdapter = new CodeTreeAdapter(new ArrayList<GitTree>(), R.layout.list_item_code_tree);
        mCodeTree.setAdapter(mAdapter);
        mCodeTree.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                mPosition = position;
                mFragment.getTree(mAdapter.getItem(position).getSha(), false);
            }
        });
        getRepoData();
    }

    public void removeItemAll() {
        for (int i = mPosition, size = mAdapter.getItemCount(); i < size; i++) {
            mAdapter.remove(mPosition);
        }
    }

    private void getRepoData() {
        MySubscriber<List<Reference>> subscriber = new MySubscriber<>(new ResultListener<List<Reference>>() {
            @Override
            public void onNext(final List<Reference> references) {
                updateData(references);
            }

            @Override
            public void onError(final Throwable e) {
                super.onError(e);
                showErrorView();
            }
        });
        Api2.getInstance().getReposReference(subscriber, mOwner, mRepo, 1);
        mSubscription.add(subscriber);
    }

    private void updateData(final List<Reference> references) {
        showContentView();
        initToolbar(mRepo, getResources().getString(R.string.repo_share), new OnToolbarMenuClickListener() {
            @Override
            public void onItemClick() {
                AppUtil.shareRep(RepoDetailActivity.this, mOwner, mRepo);
            }
        });
        setSubtitle(mOwner);
        final List<Reference> branchs = new ArrayList<>();
        for (final Reference reference : references) {
            if (reference.getRef().contains("refs/heads/")) {
                branchs.add(reference);
            }
        }
        String branch = ConvertUtil.headsBranch(branchs.get(0).getRef());
        mCodeBranch.setText(branch);
        mCodeBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RepoDetailActivity.this);
                int size = branchs.size();
                String[] b = new String[size];
                for (int i = 0; i < size; i++) {
                    b[i] = ConvertUtil.headsBranch(branchs.get(i).getRef());
                }
                builder.setItems(b, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        isClearAdapter = true;
                        mFragment.getTree(branchs.get(which).getObject().getSha(), false);
                        mCodeBranch.setText(ConvertUtil.headsBranch(branchs.get(which).getRef()));
                    }
                });
                builder.show();
            }
        });
        String sha = branchs.get(0).getObject().getSha();
        mFragment = RepoTreeFragment.newInstance(mOwner, mRepo, sha);
        getSupportFragmentManager().beginTransaction().add(R.id.code_view, mFragment).commit();
    }

    public void setCodeTree(GitTree tree) {
        if (!mAdapter.getList().contains(tree)) {
            mAdapter.add(tree);
        }
        if (isClearAdapter) {
            mAdapter.clear();
            isClearAdapter = false;
        }
    }

}
