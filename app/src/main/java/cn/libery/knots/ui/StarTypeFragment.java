package cn.libery.knots.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.libery.knots.Constants;
import cn.libery.knots.R;

/**
 * Created by Libery on 2016/7/14.
 * Email:libery.szq@qq.com
 */
public class StarTypeFragment extends BaseLoadingFragment {

    private String mType;

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
        mType = getArguments().getString(Constants.FRAGMENT_TYPE);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_star_type;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(final View view) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {

    }
}
