package com.fx.secondbar.ui.person;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CustomerBean;
import com.fx.secondbar.http.HttpManager;

import java.util.List;

import rx.Subscriber;

/**
 * function:在线客服
 * author: frj
 * modify date: 2018/10/11
 */
public class AcOnlineCustomer extends ActivitySupport implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdOnlineCustomer adapter;


    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_online_customer;
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
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 5), false, false, true));
        adapter = new AdOnlineCustomer();
        adapter.addFooterView(createFootView());
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                try
                {
                    //可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + AcOnlineCustomer.this.adapter.getItem(position).getQq();//uin是发送过去的qq号码
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e)
                {
                    e.printStackTrace();
                    ShowToast.showToast("请检查是否安装QQ");
                }
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    /**
     * 获取客服数据
     */
    private void getData()
    {
        HttpManager.getCustomerInfo(new Subscriber<List<CustomerBean>>()
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
                ShowToast.showToast(HttpManager.checkLoadError(e));
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onNext(List<CustomerBean> customerBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (VerificationUtil.getSize(customerBeans) > 0)
                {
                    adapter.setNewData(customerBeans);
                }
            }
        });
    }

    /**
     * 创建底部View
     *
     * @return
     */
    private View createFootView()
    {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_online_customer_foot, recyclerView, false);
        return view;
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
        getData();
    }
}
