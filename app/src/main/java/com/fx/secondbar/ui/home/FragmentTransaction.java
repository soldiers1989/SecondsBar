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
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.FragmentSupport;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.ui.home.item.adapter.AdHomeItem;
import com.fx.secondbar.ui.search.AcSearch;
import com.fx.secondbar.ui.transaction.FragmentCommission;
import com.fx.secondbar.ui.transaction.FragmentTransactionBuy;
import com.fx.secondbar.ui.transaction.FragmentTransactionBuyed;
import com.fx.secondbar.ui.transaction.FragmentTransactionItem;
import com.fx.secondbar.ui.transaction.FragmentTransactionSales;

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
    private TextView tv_title;
    private SlidingTabLayout tabs;
    private ViewPager viewPager;
    private List<FragmentViewPagerBase> fragmengs;
    private AdHomeItem adapter;

    private String peopleId;
    private String title;
    //是否准备刷新数据
    private boolean isPrepareRefresh = false;

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
        tv_title = findView(R.id.tv_title);
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
        fragmengs = new ArrayList<>();
        fragmengs.add(FragmentTransactionBuy.newInstance());
        fragmengs.add(FragmentTransactionSales.newInstance());
        fragmengs.add(FragmentCommission.newInstance(FragmentCommission.TYPE_CURR));
        fragmengs.add(FragmentCommission.newInstance(FragmentCommission.TYPE_HISTORY));
        fragmengs.add(FragmentTransactionBuyed.newInstance());
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
                if (fragmengs != null)
                {
                    fragmengs.get(position).onStarShow();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        //刷新数据
        if (isPrepareRefresh)
        {
            for (int i = 0; i < fragmengs.size(); i++)
            {
                FragmentTransactionItem fragmentTransactionItem = (FragmentTransactionItem) fragmengs.get(i);
                if (fragmentTransactionItem != null)
                {
                    fragmentTransactionItem.setData(peopleId, title);
                }
            }
        }
    }

    /**
     * 设置数据
     *
     * @param peopleId
     * @param name
     */
    public void setData(String peopleId, String name)
    {
        this.peopleId = peopleId;
        this.title = name;
        if (fragmengs != null)
        {
            for (int i = 0; i < fragmengs.size(); i++)
            {
                FragmentTransactionItem fragmentTransactionItem = (FragmentTransactionItem) fragmengs.get(i);
                if (fragmentTransactionItem != null)
                {
                    fragmentTransactionItem.setData(peopleId, name);
                }
            }
        } else
        {
            isPrepareRefresh = true;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        VerificationUtil.setViewValue(tv_title, title, "请选择名人");
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!isHidden())
        {
            VerificationUtil.setViewValue(tv_title, title, "请选择名人");
        }
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
                jump(AcSearch.class, AcSearch.TYPE_QUOTES, false);
                break;
        }
    }
}
