package com.fx.secondbar.ui.person.assets;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.WithdrawRecordBean;
import com.fx.secondbar.http.HttpManager;

import java.util.List;

import rx.Subscriber;

/**
 * function:提现记录
 * author: frj
 * modify date: 2018/11/3
 */
public class AcWithdrawRecord extends ActivitySupport implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdWithdrawRecord adapter;

    private int currPage = -1;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_withdraw_record;
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
        adapter = new AdWithdrawRecord();
        adapter.bindToRecyclerView(recyclerView);
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
     * @param page 页码
     */
    private void getData(final int page)
    {
        HttpManager.getWithdrawRecord(page, PAGE_NUM, new Subscriber<List<WithdrawRecordBean>>()
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
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                e.printStackTrace();
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(List<WithdrawRecordBean> withdrawRecordBeans)
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
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (PAGE_START == page)
                {
                    adapter.setNewData(withdrawRecordBeans);
                } else
                {
                    adapter.addData(withdrawRecordBeans);
                }
                if (VerificationUtil.getSize(withdrawRecordBeans) >= PAGE_NUM)
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

    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        if (R.id.ib_back == v.getId())
        {
            finish();
        }
    }
}
