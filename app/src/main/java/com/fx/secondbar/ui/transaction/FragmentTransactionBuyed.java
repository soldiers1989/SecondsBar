package com.fx.secondbar.ui.transaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fx.secondbar.R;

/**
 * function:已购
 * author: frj
 * modify date: 2018/9/28
 */
public class FragmentTransactionBuyed extends FragmentTransactionItem implements SwipeRefreshLayout.OnRefreshListener
{

    public static FragmentTransactionBuyed newInstance()
    {
        FragmentTransactionBuyed fragment = new FragmentTransactionBuyed();
        return fragment;
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdBuyed adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_transaction_buyed, container, false);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdBuyed();
        adapter.bindToRecyclerView(recyclerView);
        adapter.addHeaderView(createHeadView());
        //是否将要刷新数据
        if (isPrepareRefresh)
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            isPrepareRefresh = false;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //是否将要刷新数据
        if (isPrepareRefresh)
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            isPrepareRefresh = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!isHidden())
        {
            //是否将要刷新数据
            if (isPrepareRefresh)
            {
//                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
                isPrepareRefresh = false;
            }
        }
    }

    /**
     * 创建头部布局
     *
     * @return
     */
    private View createHeadView()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_transaction_buyed_head, null);
        return view;
    }

    @Override
    public void onRefresh()
    {

    }
}
