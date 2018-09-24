package com.fx.secondbar.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.FragmentSupport;
import com.flyco.tablayout.SlidingTabLayout;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.DialogSign;
import com.fx.secondbar.ui.home.item.FragmentInfomation;
import com.fx.secondbar.ui.home.item.FragmentTime;
import com.fx.secondbar.ui.home.item.FragmentTutorial;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.ui.home.item.FragmentWb;
import com.fx.secondbar.ui.home.item.adapter.AdHomeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * function:首页
 * author: frj
 * modify date: 2018/9/6
 */
public class FragmentHome extends FragmentSupport
{

    private ImageView img_toolbar_left;
    private ImageView img_toolbar_right;
    private FrameLayout fl_search;
    private SlidingTabLayout tabs;
    private ViewPager viewPager;

    private AdHomeItem adapter;

    public static FragmentHome newInstance()
    {
        return new FragmentHome();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home, container, false);
    }

    @Override
    protected void initView()
    {
        img_toolbar_left = findView(R.id.img_toolbar_left);
        img_toolbar_right = findView(R.id.img_toolbar_right);
        fl_search = findView(R.id.fl_search);
        tabs = findView(R.id.tabs);
        viewPager = findView(R.id.viewPager);
        Toolbar toolbar = findView(R.id.toolbar);
        ((ActivitySupport) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        img_toolbar_left.setOnClickListener(this);
        img_toolbar_right.setOnClickListener(this);
        fl_search.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        String[] tabTitles = getResources().getStringArray(R.array.home_tabs_arrays);
        final List<FragmentViewPagerBase> fragmengs = new ArrayList<>();
        fragmengs.add(new FragmentInfomation());
        fragmengs.add(new FragmentTime());
        fragmengs.add(new FragmentWb());
        fragmengs.add(new FragmentTutorial());
        adapter = new AdHomeItem(getChildFragmentManager(), fragmengs, tabTitles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(tabTitles.length);
        tabs.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                fragmengs.get(position).onStarShow();
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.img_toolbar_left:
                new DialogSign(getContext()).show();
                break;
            case R.id.img_toolbar_right:
                break;
            case R.id.fl_search:
                break;
        }
    }
}
