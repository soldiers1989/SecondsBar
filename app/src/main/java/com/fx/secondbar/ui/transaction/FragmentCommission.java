package com.fx.secondbar.ui.transaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;

/**
 * function:委托界面
 * author: frj
 * modify date: 2018/9/25
 */
public class FragmentCommission extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{
    /**
     * 当前委托
     */
    public static final int TYPE_CURR = 1;
    /**
     * 历史委托
     */
    public static final int TYPE_HISTORY = 2;


    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdCommission adapter;
    //当前类型
    private int type;

    public static FragmentCommission newInstance(int type)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, type);
        FragmentCommission fragment = new FragmentCommission();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_commission, container, false);
    }

    @Override
    public void onStarShow()
    {


    }

    @Override
    protected void initView()
    {
        swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
        recyclerView = findView(R.id.recyclerView);
    }

    @Override
    protected void initListener()
    {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData()
    {
        type = getArguments().getInt(KEY);
        adapter = new AdCommission();
        adapter.bindToRecyclerView(recyclerView);
    }

    @Override
    public void onRefresh()
    {

    }
}
