package com.fx.secondbar.ui.person.assets;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * function:收益记录
 * author: frj
 * modify date: 2018/9/21
 */
public class AcIncomeRecord extends ActivitySupport implements SwipeRefreshLayout.OnRefreshListener
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdIncomeRecord adapter;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_income_record;
    }

    @Override
    protected void initView()
    {
        swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
        recyclerView = findView(R.id.recyclerView);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdIncomeRecord();
        adapter.setNewData(getDatas());
        adapter.bindToRecyclerView(recyclerView);
    }

    private List<String> getDatas()
    {
        List<String> data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        return data;
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

    @Override
    public void onRefresh()
    {

    }
}
