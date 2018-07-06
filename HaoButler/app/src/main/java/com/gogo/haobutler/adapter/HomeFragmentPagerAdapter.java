package com.gogo.haobutler.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gogo.haobutler.fragment.base.BaseFragment;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/4/15
 * @function: 主页ViewPager的Adapter
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<BaseFragment> fragmentList;

    public HomeFragmentPagerAdapter(FragmentManager fm, String[] titles, List<BaseFragment> fragmentList) {
        super(fm);
        this.titles = titles;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
