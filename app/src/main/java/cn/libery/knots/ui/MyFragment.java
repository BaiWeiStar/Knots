package cn.libery.knots.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.libery.knots.R;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public class MyFragment extends Fragment {

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }
}
