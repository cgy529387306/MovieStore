package com.android.mb.movie.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgy on 19/1/19.
 */

public class ChannelFragment extends BaseFragment{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] mTitles = {"专栏推荐","标签筛选"};
    @Override
    protected int getLayoutId() {
        return R.layout.frg_channel;
    }

    @Override
    protected void bindViews(View view) {
        mTabLayout = mRootView.findViewById(R.id.tab_layout);
        mViewPager = mRootView.findViewById(R.id.view_pager);
        initTabPager();
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
    }

    private void initTabPager(){
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new SpecialFragment());
        mFragmentList.add(new TagFragment());
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
