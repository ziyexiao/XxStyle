package com.xx.style.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xx.style.base.BaseFragment;

import java.util.List;

/**
 * Created by XX on 2018/9/3.
 */

public class WeigetVpAdapter extends FragmentPagerAdapter{
    private String[] mTitles;
    private List<BaseFragment> mFragments;

    public WeigetVpAdapter(FragmentManager fm, String[] titles, List<BaseFragment> fragments) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
