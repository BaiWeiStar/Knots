package cn.libery.knots;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Libery on 2016/7/15.
 * Email:libery.szq@qq.com
 */
public class RepFragment extends Fragment {

    public static RepFragment newInstance() {
        Bundle args = new Bundle();
        RepFragment fragment = new RepFragment();
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
        View view = inflater.inflate(R.layout.fragment_rep, container, false);
        return view;
    }
}
