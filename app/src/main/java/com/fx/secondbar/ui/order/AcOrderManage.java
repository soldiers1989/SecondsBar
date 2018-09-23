package com.fx.secondbar.ui.order;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.flyco.tablayout.SlidingTabLayout;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.ui.home.item.adapter.AdHomeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * function:订单管理
 * author: frj
 * modify date: 2018/9/21
 */
public class AcOrderManage extends ActivitySupport
{

    private SlidingTabLayout tabs;
    private ViewPager viewPager;
    private AdHomeItem adapter;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_order_manage;
    }

    @Override
    protected void initView()
    {
        tabs = findView(R.id.tabs);
        viewPager = findView(R.id.viewPager);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        final List<FragmentViewPagerBase> list = new ArrayList<>();
        list.add(FOrderItem.newInstance(FOrderItem.TYPE_ALL));
        list.add(FOrderItem.newInstance(FOrderItem.TYPE_WAIT_PAY));
        list.add(FOrderItem.newInstance(FOrderItem.TYPE_PERFORMANCE));
        list.add(FOrderItem.newInstance(FOrderItem.TYPE_PERFORMANCING));
        list.add(FOrderItem.newInstance(FOrderItem.TYPE_REFUND));
        adapter = new AdHomeItem(getSupportFragmentManager(), list, getResources().getStringArray(R.array.order_manage_tabs));
        viewPager.setAdapter(adapter);
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
                list.get(position).onStarShow();
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
            case R.id.ib_back:
                finish();
                break;
        }
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[0];
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[0];
    }
}
