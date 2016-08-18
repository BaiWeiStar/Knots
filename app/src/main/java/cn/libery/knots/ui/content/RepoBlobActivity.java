package cn.libery.knots.ui.content;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.Constants;
import cn.libery.knots.Intents;
import cn.libery.knots.R;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.MySubscriber;
import cn.libery.knots.model.code.Blob;
import cn.libery.knots.model.code.GitTree;
import cn.libery.knots.ui.BaseLoadingActivity;
import cn.libery.knots.utils.AppUtil;
import cn.libery.knots.utils.ConvertUtil;

/**
 * Created by Libery on 2016/8/16.
 * Email:libery.szq@qq.com
 */
public class RepoBlobActivity extends BaseLoadingActivity {

    @BindView(R.id.code)
    TextView mCode;
    private String mOwner, mRepo, mBranch;
    private GitTree mGitTree;

    public static Intent intent(Context context, String owner, String repo, Parcelable gitTree, String
            branch) {
        return new Intents.Builder().setClass(context, RepoBlobActivity.class)
                .add(Constants.EXTRA_REPO_OWNER, owner)
                .add(Constants.EXTRA_REPO_NAME, repo)
                .add(Constants.EXTRA_REPO_GIT_TREE, gitTree)
                .add(Constants.EXTRA_REPO_BRANCH, branch)
                .toIntent();
    }

    @Override
    protected void loadData() {
        getBlob(mGitTree.getSha());
    }

    @Override
    protected void obtainParam(final Intent intent) {
        mOwner = intent.getStringExtra(Constants.EXTRA_REPO_OWNER);
        mRepo = intent.getStringExtra(Constants.EXTRA_REPO_NAME);
        mGitTree = intent.getParcelableExtra(Constants.EXTRA_REPO_GIT_TREE);
        mBranch = intent.getStringExtra(Constants.EXTRA_REPO_BRANCH);
    }

    @Override
    protected void initData() {
        setLoadingContentView(R.layout.activity_repo_blob);
        ButterKnife.bind(this);
        getBlob(mGitTree.getSha());
    }

    private void getBlob(String sha) {
        final ResultListener<Blob> listener = new ResultListener<Blob>() {
            @Override
            public void onNext(final Blob tree) {
                showContentView();
                showToolbar();
                RichText.fromMarkdown(ConvertUtil.decodeByBase64(tree.getContent())).into(mCode);
                // mCode.setText();
            }

            @Override
            public void onError(final Throwable e) {
                super.onError(e);
                showErrorView();
                showToolbar();
            }
        };
        MySubscriber<Blob> subscriber = new MySubscriber<>(listener);
        Api2.getInstance().getRepoBlob(subscriber, mOwner, mRepo, sha);
        mSubscription.add(subscriber);
    }

    private void showToolbar() {
        initToolbar(mGitTree.getPath(), getResources().getString(R.string.repo_share), new OnToolbarMenuClickListener
                () {
            @Override
            public void onItemClick() {
                AppUtil.shareBlob(RepoBlobActivity.this, mOwner, mRepo, mBranch, mGitTree.getPath());
            }
        });
        setSubtitle(mBranch);
    }
}
