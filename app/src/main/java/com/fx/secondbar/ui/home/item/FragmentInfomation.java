package com.fx.secondbar.ui.home.item;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btten.bttenlibrary.util.LogUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.CategoryBean;
import com.fx.secondbar.ui.home.item.adapter.AdHomeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * function:资讯
 * author: frj
 * modify date: 2018/9/7
 */
public class FragmentInfomation extends FragmentViewPagerBase
{

    private SlidingTabLayout tabs;
    private ViewPager viewPager;
    private AdHomeItem adapter;
    private TextView tv_today_q;

    @Override
    public void onStarShow()
    {
        setTodayIncome();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home_infomation, container, false);
    }


    @Override
    protected void initView()
    {
        tabs = findView(R.id.tabs);
        viewPager = findView(R.id.viewPager);
        tv_today_q = findView(R.id.tv_today_q);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        List<CategoryBean> list = FxApplication.getInstance().getConfigInfo().getListCategoryNews();
        String[] tabTitles = new String[list.size()];
        final List<FragmentViewPagerBase> fragmengs = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
        {
            tabTitles[i] = list.get(i).getName();
            fragmengs.add(FragmentInformationItem.newInstance(list.get(i).getCategory_ID(), i == 0));
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
                LogUtil.e("FragmentInformation", "onPageSelected:" + position);
                fragmengs.get(position).onStarShow();
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        viewPager.setCurrentItem(0);

        setTodayIncome();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTodayIncome();
    }

    /**
     * 设置今日收益
     */
    private void setTodayIncome()
    {
        if (tv_today_q != null)
        {
            tv_today_q.setText(VerificationUtil.verifyDefault(String.valueOf(FxApplication.getInstance().getUserInfoBean().getTodayqcoin()), "0"));
        }
    }

}
