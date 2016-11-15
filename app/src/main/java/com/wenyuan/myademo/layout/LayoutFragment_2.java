package com.wenyuan.myademo.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;
import com.wenyuan.myademo.BaseFragment;
import com.wenyuan.myademo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LayoutFragment_2 extends BaseFragment {


    private TabPageIndicator mTabPageIndicator;
    private ViewPager mViewPager;

    public LayoutFragment_2() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mFragRootView;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_layout_fragment_2;
    }

    @Override
    protected void initView(View view) {
        mTabPageIndicator = (TabPageIndicator) mFragRootView.findViewById(R.id.tab_page_indicator);
        mViewPager = (ViewPager) mFragRootView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        //设置viewpager
        mTabPageIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initObject() {

    }

    /**
     * FragmentStatePagerAdapter:只保留当前状态的fragment
     */
    private class MyAdapter extends FragmentPagerAdapter {

        public String[] TITLES = new String[]
                {"课程", "问答", "求课", "学习", "计划"};

        public MyAdapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new DemoFragment();
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }
}
