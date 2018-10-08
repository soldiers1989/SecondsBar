package com.fx.secondbar.ui.notify;

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
 * function:消息列表
 * author: frj
 * modify date: 2018/9/26
 */
@Deprecated
public class AcMessageList extends ActivitySupport
{
    /**
     * 系统消息类型
     */
    public static final int TYPE_SYSTEM = 0;
    /**
     * 公告消息类型
     */
    public static final int TYPE_ANNO = 1;

    private SlidingTabLayout tabs;
    private ViewPager viewPager;

    private AdHomeItem adapter;


    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_message_list;
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
        String[] tabTitles = new String[]{"未读", "已读"};
        final List<FragmentViewPagerBase> fragmengs = new ArrayList<>();
        fragmengs.add(FragmentMessage.newInstance(getIntent().getIntExtra(KEY, TYPE_SYSTEM), FragmentMessage.TYPE_UNREAD));
        fragmengs.add(FragmentMessage.newInstance(getIntent().getIntExtra(KEY, TYPE_SYSTEM), FragmentMessage.TYPE_READED));
        adapter = new AdHomeItem(getSupportFragmentManager(), fragmengs, tabTitles);
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
        if (isFastDoubleClick(v))
        {
            return;
        }
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
