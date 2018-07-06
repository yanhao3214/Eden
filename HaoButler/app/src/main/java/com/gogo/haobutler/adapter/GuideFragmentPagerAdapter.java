package com.gogo.haobutler.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gogo.haobutler.fragment.base.BaseFragment;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/4/16
 * @function:
 */

public class GuideFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;
    private List<Integer> pagerList;

    public GuideFragmentPagerAdapter(FragmentManager fm, Context context, List<Integer> pagerList) {
        super(fm);
        for (Integer pager : pagerList) {
        }

        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentList != null) {
            return fragmentList.size();
        }
        return 0;
    }
}
