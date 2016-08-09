package cn.libery.knots.ui.content;

import android.content.Context;
import android.content.Intent;

import cn.libery.knots.Intents;
import cn.libery.knots.R;
import cn.libery.knots.ui.BaseLoadingActivity;

/**
 * Created by Libery on 2016/8/8.
 * Email:libery.szq@qq.com
 */
public class RepoDetailActivity extends BaseLoadingActivity {

    public static Intent intent(Context context) {
        return new Intents.Builder().setClass(context, RepoDetailActivity.class)
                .toIntent();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void obtainParam(final Intent intent) {

    }

    @Override
    protected void initView() {
        super.initView();
        setLoadingContentView(R.layout.activity_repo_detail);
    }

    @Override
    protected void initData() {
        showContentView();
        initToolbar("仓库");
    }

}
