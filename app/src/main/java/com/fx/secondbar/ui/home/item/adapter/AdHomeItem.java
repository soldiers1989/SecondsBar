package com.fx.secondbar.ui.home.item.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;

import java.util.List;

/**
 * function:首页的ViewPage项的适配器
 * author: frj
 * modify date: 2018/9/6
 */
public class AdHomeItem extends FragmentPagerAdapter {

    private List<FragmentViewPagerBase> fragments;
    private String[] tabs;

    public AdHomeItem(FragmentManager fm, List<FragmentViewPagerBase> fragments, String[] tabs) {
        super(fm);
        this.fragments = fragments;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments != null ? fragments.get(i) : null;
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return PagerAdapter.POSITION_NONE;
//    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs != null ? tabs[position] : null;
    }
}
