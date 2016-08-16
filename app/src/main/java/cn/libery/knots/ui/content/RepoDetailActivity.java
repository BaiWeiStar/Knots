package cn.libery.knots.ui.content;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.Constants;
import cn.libery.knots.Intents;
import cn.libery.knots.R;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.MySubscriber;
import cn.libery.knots.model.code.Reference;
import cn.libery.knots.ui.BaseLoadingActivity;

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
    TextView mCodeTree;
    private String mOwner, mRepo;

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
        getRepoData();
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
    }

    private void updateData(final List<Reference> references) {
        showContentView();
        initToolbar(mRepo);
        setSubtitle(mOwner);
        List<Reference> branchs = new ArrayList<>();
        for (final Reference reference : references) {
            if (reference.getRef().contains("refs/heads/")) {
                branchs.add(reference);
            }
        }
        String branch = branchs.get(0).getRef().replaceAll("refs/heads/", "");
        mCodeBranch.setText(branch);

        String sha = branchs.get(0).getObject().getSha();
        getSupportFragmentManager().beginTransaction().add(R.id.code_view, RepoTreeFragment.newInstance(mOwner,
                mRepo, sha)).commit();
    }

    public void setCodeTree(String codeTree) {
        mCodeTree.append(codeTree);
    }

}
