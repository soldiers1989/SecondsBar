package com.fx.secondbar.ui.person.assets;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.http.HttpManager;

import rx.Subscriber;

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
        adapter.bindToRecyclerView(recyclerView);
        adapter.setNewData(FxApplication.getInstance().getUserInfoBean().getListQcoin());
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

    /**
     * 登录(Q币收益记录在该接口中返回)
     */
    private void login()
    {
        HttpManager.login(new Subscriber<UserInfoBean>()
        {
            @Override
            public void onCompleted()
            {
            }

            @Override
            public void onError(Throwable e)
            {
                if (isDestroy())
                {
                    return;
                }
                e.printStackTrace();
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onNext(UserInfoBean userInfoBean)
            {
                if (isDestroy())
                {
                    return;
                }
                FxApplication.getInstance().setUserInfoBean(userInfoBean);
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                adapter.setNewData(FxApplication.getInstance().getUserInfoBean().getListQcoin());
            }
        });
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
        login();
    }
}
