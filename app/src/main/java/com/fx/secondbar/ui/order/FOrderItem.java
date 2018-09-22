package com.fx.secondbar.ui.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;

import java.util.ArrayList;
import java.util.List;

/**
 * function:订单管理-订单项
 * author: frj
 * modify date: 2018/9/21
 */
public class FOrderItem extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{
    /**
     * 全部
     */
    public static final int TYPE_ALL = 1;
    /**
     * 待付款
     */
    public static final int TYPE_WAIT_PAY = 2;
    /**
     * 待履约
     */
    public static final int TYPE_PERFORMANCE = 3;
    /**
     * 履约中
     */
    public static final int TYPE_PERFORMANCING = 4;
    /**
     * 退款
     */
    public static final int TYPE_REFUND = 5;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdOrder adapter;

    public static FOrderItem newInstance(int type)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, type);
        FOrderItem fragment = new FOrderItem();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_order_item, container, false);
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
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(getContext(), 15), true, false, true));
        adapter = new AdOrder();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setNewData(getDatas());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                jump(AcOrderDetail.class);
            }
        });
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
    public void onRefresh()
    {

    }
}
