package com.fx.secondbar.ui.home.item;

import android.content.Intent;
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

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.WBBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.AcShareDialog;
import com.fx.secondbar.ui.home.item.adapter.AdWb;

import java.util.List;

import rx.Subscriber;

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

    private int currPage = -1;

    @Override
    public void onStarShow()
    {
        if (currPage == -1)
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdWb((ActivitySupport) getActivity());
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1);
            }
        }, recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener()
        {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position)
            {
                if (isFastDoubleClick(view))
                {
                    return;
                }
                WBBean item = FragmentWb.this.adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(AcShareDialog.KEY_TYPE, AcShareDialog.TYPE_POSTER_CONTENT);
                bundle.putString(AcShareDialog.KEY_TITLE, item.getTitle());
                bundle.putString(AcShareDialog.KEY_CONTENT, item.getContent());
                bundle.putString(AcShareDialog.KEY_URL, "");
                jump(AcShareDialog.class, bundle, false);
            }
        });
    }

    /**
     * 获取数据
     *
     * @param page
     */
    private void getData(final int page)
    {
        HttpManager.getWbs(page, PAGE_NUM, new Subscriber<List<WBBean>>()
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
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(List<WBBean> wbBeans)
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
                    adapter.setNewData(wbBeans);
                } else
                {
                    adapter.addData(wbBeans);
                }
                if (wbBeans.size() >= PAGE_NUM)
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
