package com.fx.secondbar.ui.home.item;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.item.adapter.AdWb;

import java.util.ArrayList;
import java.util.List;

/**
 * function:微博页
 * author: frj
 * modify date: 2018/9/7
 */
public class FragmentWb extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    private TextView tv_wb;
    private TextView tv_live;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdWb adapter;

    @Override
    public void onStarShow()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home_wb, container, false);
    }


    @Override
    protected void initView()
    {
        swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
        recyclerView = findView(R.id.recyclerView);
        tv_wb = findView(R.id.tv_wb);
        tv_live = findView(R.id.tv_live);
    }

    @Override
    protected void initListener()
    {
        tv_wb.setOnClickListener(this);
        tv_live.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData()
    {
        swipeRefreshLayout.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdWb();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setNewData(getDatas());
    }

    private List<String> getDatas()
    {
        List<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        return datas;
    }

    @Override
    public void onRefresh()
    {

    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_wb:
                break;
            case R.id.tv_live:
                ShowToast.showToast("敬请期待");
                break;
        }
    }
}
