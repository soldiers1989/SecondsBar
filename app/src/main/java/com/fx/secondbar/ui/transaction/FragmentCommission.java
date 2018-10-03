package com.fx.secondbar.ui.transaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommissionBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.ProgressDialogUtil;

import java.util.List;

import rx.Subscriber;

/**
 * function:委托界面
 * author: frj
 * modify date: 2018/9/25
 */
public class FragmentCommission extends FragmentTransactionItem implements SwipeRefreshLayout.OnRefreshListener
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

    private ProgressDialog dialog;

    private int currPage = -1;

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

        if (VerificationUtil.getSize(adapter.getData()) == 0)
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
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
        dialog = ProgressDialogUtil.getProgressDialog(getActivity(), getString(R.string.progress_tips), true);
        type = getArguments().getInt(KEY);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdCommission();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                refreshData(currPage + 1, String.valueOf(type));
            }
        }, recyclerView);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
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
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
                isPrepareRefresh = false;
            }
        }
    }

    /**
     * 刷新数据
     */
    private void refreshData(final int page, final String type)
    {
        HttpManager.getTradingCommission(page, PAGE_NUM, type, new Subscriber<List<CommissionBean>>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isNetworkCanReturn())
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
            public void onNext(List<CommissionBean> list)
            {
                if (isNetworkCanReturn())
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
                    adapter.setNewData(list);
                } else
                {
                    adapter.addData(list);
                }
                if (VerificationUtil.getSize(list) >= PAGE_NUM)
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
    public void onRefresh()
    {
        refreshData(PAGE_START, String.valueOf(type));
    }
}
