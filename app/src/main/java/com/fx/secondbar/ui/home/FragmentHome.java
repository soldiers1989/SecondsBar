package com.fx.secondbar.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.FragmentSupport;
import com.btten.bttenlibrary.util.SharePreferenceUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.DialogSign;
import com.fx.secondbar.ui.MainActivity;
import com.fx.secondbar.ui.home.item.FragmentInfomation;
import com.fx.secondbar.ui.home.item.FragmentTime;
import com.fx.secondbar.ui.home.item.FragmentTutorial;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.ui.home.item.FragmentWb;
import com.fx.secondbar.ui.home.item.adapter.AdHomeItem;
import com.fx.secondbar.ui.notify.AcNotifyManager;
import com.fx.secondbar.ui.search.AcSearch;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.RequestCode;

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
    private View v_notify;
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
        v_notify = findView(R.id.v_notify);
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
        IntentFilter filter = new IntentFilter(Constants.ACTION_REFRESH_NOTIFY_TIPS);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent == null)
            {
                return;
            }
            if (Constants.ACTION_REFRESH_NOTIFY_TIPS.equals(intent.getAction()))
            {
                updateStatus();
            }
        }
    };

    /**
     * 显示微博的界面
     */
    public void showWb()
    {
        if (viewPager != null)
        {
            viewPager.setCurrentItem(2);
        }
    }

    /**
     * 显示资讯的页面
     */
    public void showInformation()
    {
        if (viewPager != null)
        {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateStatus();
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!hidden)
        {
            updateStatus();
        }
    }

    /**
     * 更新小红点状态
     */
    private void updateStatus()
    {
        if (v_notify == null)
        {
            return;
        }
        if (SharePreferenceUtils.getSystemMsg() || SharePreferenceUtils.getAnnoMsg())
        {
            v_notify.setVisibility(View.VISIBLE);
        } else
        {
            v_notify.setVisibility(View.GONE);
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
            case R.id.img_toolbar_left:
                new DialogSign(getContext()).show();
                break;
            case R.id.img_toolbar_right:
                jump(AcNotifyManager.class);
                break;
            case R.id.fl_search:
                jump(AcSearch.class, AcSearch.TYPE_INFORMATION, false);
                break;
        }
    }

    @Override
    public void onDestroy()
    {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onDestroy();
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
