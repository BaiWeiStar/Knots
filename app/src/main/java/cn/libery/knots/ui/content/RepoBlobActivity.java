package cn.libery.knots.ui.content;

import android.content.Context;
import android.content.Intent;
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
import cn.libery.knots.ui.BaseLoadingActivity;
import cn.libery.knots.utils.ConvertUtil;

/**
 * Created by Libery on 2016/8/16.
 * Email:libery.szq@qq.com
 */
public class RepoBlobActivity extends BaseLoadingActivity {

    @BindView(R.id.code)
    TextView mCode;
    private String mOwner, mRepo, mSha;

    public static Intent intent(Context context, String owner, String repo, String sha) {
        return new Intents.Builder().setClass(context, RepoBlobActivity.class)
                .add(Constants.EXTRA_REPO_OWNER, owner)
                .add(Constants.EXTRA_REPO_NAME, repo)
                .add(Constants.EXTRA_REPO_SHA, sha)
                .toIntent();
    }

    @Override
    protected void loadData() {
        getBlob(mSha);
    }

    @Override
    protected void obtainParam(final Intent intent) {
        mOwner = intent.getStringExtra(Constants.EXTRA_REPO_OWNER);
        mRepo = intent.getStringExtra(Constants.EXTRA_REPO_NAME);
        mSha = intent.getStringExtra(Constants.EXTRA_REPO_SHA);
    }

    @Override
    protected void initData() {
        setLoadingContentView(R.layout.activity_repo_blob);
        ButterKnife.bind(this);
        getBlob(mSha);
    }

    private void getBlob(String sha) {
        final ResultListener<Blob> listener = new ResultListener<Blob>() {
            @Override
            public void onNext(final Blob tree) {
                showContentView();
                RichText.fromMarkdown(ConvertUtil.decodeByBase64(tree.getContent())).into(mCode);
                // mCode.setText();
            }

            @Override
            public void onError(final Throwable e) {
                super.onError(e);
                showErrorView();
            }
        };
        MySubscriber<Blob> subscriber = new MySubscriber<>(listener);
        Api2.getInstance().getRepoBlob(subscriber, mOwner, mRepo, sha);
        mSubscription.add(subscriber);
    }
}
