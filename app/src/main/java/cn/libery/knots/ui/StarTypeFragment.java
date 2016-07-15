package cn.libery.knots.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.libery.knots.Constants;

/**
 * Created by Libery on 2016/7/14.
 * Email:libery.szq@qq.com
 */
public class StarTypeFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final
    Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
