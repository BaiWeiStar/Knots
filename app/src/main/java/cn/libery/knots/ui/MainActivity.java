package cn.libery.knots.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.R;
import cn.libery.knots.ui.my.MyFragment;
import cn.libery.knots.ui.repository.RepFragment;
import cn.libery.knots.ui.star.StarFragment;
import cn.libery.knots.utils.Logger;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void obtainParam(final Intent intent) {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Stars").setActiveColorResource(R.color
                        .colorAccent))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Rep").setActiveColorResource(R.color
                        .colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "My").setActiveColorResource(R.color
                        .colorPrimaryDark))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(mViewpager);
        mViewpager.setOffscreenPageLimit(3);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mBottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

    }

    @Override
    protected void initData() {
        Logger.e("initData");
    }

    @Override
    public void onTabSelected(final int position) {
        mViewpager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(final int position) {

    }

    @Override
    public void onTabReselected(final int position) {

    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(StarFragment.newInstance());
        adapter.addFragment(RepFragment.newInstance());
        adapter.addFragment(MyFragment.newInstance());
        viewPager.setAdapter(adapter);
    }

    private static class FragmentAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }

}
