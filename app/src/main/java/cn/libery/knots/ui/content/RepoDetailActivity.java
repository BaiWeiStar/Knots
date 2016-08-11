package cn.libery.knots.ui.content;

import android.content.Context;
import android.content.Intent;

import java.util.List;

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
        });
        Api2.getInstance().getReposReference(subscriber, mOwner, mRepo, 1);
    }

    private void updateData(final List<Reference> references) {
        showContentView();
        initToolbar(mRepo);
        setSubtitle(mOwner);
    }

}
