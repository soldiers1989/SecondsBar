package com.fx.secondbar.ui.home;

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
import com.fx.secondbar.ui.home.item.adapter.AdHomeItem;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.ui.transaction.FragmentTransactionBuy;
import com.fx.secondbar.ui.transaction.FragmentTransactionOrder;
import com.fx.secondbar.ui.transaction.FragmentTransactionSales;
import com.fx.secondbar.ui.search.AcSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * function:交易页
 * author: frj
 * modify date: 2018/9/6
 */
public class FragmentTransaction extends FragmentSupport
{

    private ImageView img_toolbar_right;
    private SlidingTabLayout tabs;
    private ViewPager viewPager;

    private AdHomeItem adapter;


    public static FragmentTransaction newInstance()
    {
        return new FragmentTransaction();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_transaction, container, false);
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
        String[] tabTitles = getResources().getStringArray(R.array.transaction_tabs);
        List<FragmentViewPagerBase> fragmengs = new ArrayList<>();
        fragmengs.add(FragmentTransactionBuy.newInstance());
        fragmengs.add(FragmentTransactionSales.newInstance());
        fragmengs.add(FragmentTransactionOrder.newInstance());
        fragmengs.add(FragmentTransactionOrder.newInstance());
        adapter = new AdHomeItem(getChildFragmentManager(), fragmengs, tabTitles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(tabTitles.length);
        tabs.setViewPager(viewPager);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.img_toolbar_right:
                jump(AcSearch.class, AcSearch.TYPE_COMMODITY, false);
                break;
        }
    }
}
