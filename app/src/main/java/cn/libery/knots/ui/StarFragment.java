package cn.libery.knots.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.Constants;
import cn.libery.knots.R;

/**
 * Created by Libery on 2016/7/14.
 * Email:libery.szq@qq.com
 */
public class StarFragment extends Fragment {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    public static StarFragment newInstance() {
        return new StarFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, container, false);
        ButterKnife.bind(this, view);

        setupViewPager(mViewpager);
        mTabs.setupWithViewPager(mViewpager);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(StarTypeFragment.newInstance(Constants.FRAGMENT_TAG), "Tag");
        adapter.addFragment(StarTypeFragment.newInstance(Constants.FRAGMENT_DICT), "Dict");
        viewPager.setAdapter(adapter);
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
