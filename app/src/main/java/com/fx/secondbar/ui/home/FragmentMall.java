package com.fx.secondbar.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.FragmentSupport;
import com.flyco.tablayout.SlidingTabLayout;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.CategoryBean;
import com.fx.secondbar.ui.MainActivity;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.ui.home.item.adapter.AdHomeItem;
import com.fx.secondbar.ui.mall.FragmentMallItem;
import com.fx.secondbar.ui.search.AcSearch;
import com.fx.secondbar.util.RequestCode;

import java.util.ArrayList;
import java.util.List;

/**
 * function:商城页
 * author: frj
 * modify date: 2018/9/6
 */
public class FragmentMall extends FragmentSupport
{

    private ImageView img_toolbar_right;
    private SlidingTabLayout tabs;
    private ViewPager viewPager;

    private AdHomeItem adapter;


    public static FragmentMall newInstance()
    {
        return new FragmentMall();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_mall, container, false);
    }

    @Override
    protected void initView()
    {
        img_toolbar_right = findView(R.id.img_toolbar_right);
        tabs = findView(R.id.tabs);
        viewPager = findView(R.id.viewPager);
        Toolbar toolbar = findView(R.id.toolbar);
        ((ActivitySupport) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        img_toolbar_right.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
//        String[] tabTitles = getResources().getStringArray(R.array.mall_tabs);
        List<CategoryBean> categoryBeans = FxApplication.getInstance().getConfigInfo().getListCategoryStar();
        //第一项默认为全部，未在栏目集合中返回，此处单独处理
        int size = categoryBeans.size() + 1;
        String[] tabTitles = new String[size];
        final List<FragmentViewPagerBase> fragmengs = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            String id = "";
            if (i == 0)
            {
                tabTitles[i] = "全部";
            } else
            {
                tabTitles[i] = categoryBeans.get(i - 1).getName();
                id = categoryBeans.get(i - 1).getCategory_ID();
            }
            fragmengs.add(FragmentMallItem.newInstance(id));
        }
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
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.img_toolbar_right:
                Bundle bundle = new Bundle();
                bundle.putInt(KEY, AcSearch.TYPE_COMMODITY);
                jump(AcSearch.class, bundle, RequestCode.REQUEST_CODE_TO_MALL_DETAIL);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (RequestCode.REQUEST_CODE_TO_MALL_DETAIL == requestCode && ActivitySupport.RESULT_OK == resultCode)
        {
            ((MainActivity) getActivity()).jumpToPersonal();
        }
    }
}
