package com.fx.secondbar.ui.person;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.SystemIntroBean;
import com.fx.secondbar.http.HttpManager;

import java.util.List;

import rx.Subscriber;

/**
 * function:帮助列表
 * author: frj
 * modify date: 2018/10/11
 */
public class AcHelpList extends ActivitySupport implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    //    private AdHelpList adapter;
    private AdTransactionRule adapter;

    private int currPage = -1;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_help_list;
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
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 9), false, false, false));
        adapter = new AdTransactionRule();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                Bundle bundle = new Bundle();
                bundle.putString(KEY_STR, AcHelpList.this.adapter.getItem(position).getTitle());
                bundle.putString(KEY, AcHelpList.this.adapter.getItem(position).getContent());
                jump(AcHtmlTextDetail.class, bundle, false);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1);
            }
        }, recyclerView);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    /**
     * 获取数据
     *
     * @param page
     */
    private void getData(final int page)
    {
        HttpManager.getSystemIntro("2", page, PAGE_NUM, new Subscriber<List<SystemIntroBean>>()
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
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(List<SystemIntroBean> systemIntroBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                currPage = page;
                if (PAGE_START == page)
                {
                    adapter.setNewData(systemIntroBeans);
                } else
                {
                    adapter.addData(systemIntroBeans);
                }
                if (VerificationUtil.getSize(systemIntroBeans) >= PAGE_NUM)
                {
                    adapter.loadMoreComplete();
                } else
                {
                    adapter.loadMoreEnd();
                }
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
        getData(PAGE_START);
    }
}
