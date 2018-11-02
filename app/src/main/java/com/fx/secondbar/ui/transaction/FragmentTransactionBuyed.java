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
import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.TradingBuyedBean;
import com.fx.secondbar.http.HttpManager;

import rx.Subscriber;

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
    private TextView tv_money;
    private TextView tv_price;
    private AdBuyed adapter;
    private int currPage = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_transaction_buyed, container, false);
    }

    @Override
    public void onStarShow()
    {
        if (swipeRefreshLayout != null)
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdBuyed();
        adapter.bindToRecyclerView(recyclerView);
        adapter.addHeaderView(createHeadView());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1);
            }
        }, recyclerView);
        //是否将要刷新数据
//        if (isPrepareRefresh)
//        {
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
        isPrepareRefresh = false;
//        }
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
        tv_money = view.findViewById(R.id.tv_money);
        tv_price = view.findViewById(R.id.tv_price);
        view.findViewById(R.id.v_price).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                jump(AcMarketValue.class);
            }
        });
        return view;
    }

    /**
     * 获取已购数据
     *
     * @param page
     */
    private void getData(final int page)
    {
        HttpManager.getBuyed(page, PAGE_NUM, new Subscriber<TradingBuyedBean>()
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
            }

            @Override
            public void onNext(TradingBuyedBean tradingBuyedBean)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                currPage = page;
                VerificationUtil.setViewValue(tv_money, tradingBuyedBean.getBalance().toString());
                VerificationUtil.setViewValue(tv_price, tradingBuyedBean.getMarketcapamount().toString());
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (PAGE_START == page)
                {
                    adapter.setNewData(tradingBuyedBean.getListTransactionInfo());
                } else
                {
                    adapter.addData(tradingBuyedBean.getListTransactionInfo());
                }
                if (VerificationUtil.getSize(tradingBuyedBean.getListTransactionInfo()) >= PAGE_NUM)
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
        getData(PAGE_START);
    }
}
