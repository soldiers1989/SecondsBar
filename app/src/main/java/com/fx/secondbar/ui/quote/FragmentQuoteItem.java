package com.fx.secondbar.ui.quote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.ResQuote;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.MainActivity;
import com.fx.secondbar.ui.home.adapter.AdQuote;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;

import rx.Subscriber;

/**
 * function:行情页Item
 * author: frj
 * modify date: 2018/9/10
 */
public class FragmentQuoteItem extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{
    private static final int REQUEST_CODE_DETAIL = 1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private AdQuote adapter;

    private int currPage = -1;
    //栏目id
    private String categoryId = "";

    public static FragmentQuoteItem newInstance(String type)
    {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STR, type);
        FragmentQuoteItem fragment = new FragmentQuoteItem();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStarShow()
    {
        if (currPage == -1)
        {
            if (swipeRefreshLayout == null)
            {
                return;
            }
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_quote_item, container, false);
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
        categoryId = getArguments().getString(KEY_STR);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getContext().getResources().getDimensionPixelSize(R.dimen.mall_item_space), false, false, false));
        adapter = new AdQuote();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                if (isFastDoubleClick(view))
                {
                    return;
                }
                Bundle bundle = new Bundle();
                String title = FragmentQuoteItem.this.adapter.getItem(position).getName();
                if (!TextUtils.isEmpty(FragmentQuoteItem.this.adapter.getItem(position).getZjm()))
                {
                    title = title + "(" + FragmentQuoteItem.this.adapter.getItem(position).getZjm() + ")";
                }
                bundle.putString(KEY_STR, title);
                bundle.putInt(KEY, FragmentQuoteItem.this.adapter.getItem(position).getIscollection());
                bundle.putString("id", FragmentQuoteItem.this.adapter.getItem(position).getPeopleid());
                jump(AcQuoteDetail.class, bundle, REQUEST_CODE_DETAIL);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1, categoryId);
            }
        }, recyclerView);
        swipeRefreshLayout.setRefreshing(false);
        onRefresh();
    }

    /**
     * 获取数据
     *
     * @param page
     * @param categoryId 栏目id
     */
    private void getData(final int page, String categoryId)
    {
        HttpManager.getQuoteLis(page, PAGE_NUM, categoryId, new Subscriber<ResQuote>()
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
            public void onNext(ResQuote quoteBeans)
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
                    adapter.setNewData(quoteBeans.getListTradingMarket());
                } else
                {
                    adapter.addData(quoteBeans.getListTradingMarket());
                }
                if (VerificationUtil.getSize(quoteBeans.getListTradingMarket()) >= PAGE_NUM)
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
        getData(PAGE_START, categoryId);
    }

    /**
     * 刷新数据
     */
    public void refresh()
    {
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_DETAIL == requestCode)
        {
            if (ActivitySupport.RESULT_OK == resultCode)
            {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            } else if (AcQuoteDetail.RESULT_CODE_TRANSACTION == resultCode)
            {
                if (data == null)
                {
                    return;
                }
                String name = data.getStringExtra(KEY_STR);
                String peopleId = data.getStringExtra(KEY);
                boolean needRefresh = data.getBooleanExtra("needRefresh", false);
                if (needRefresh)
                {
                    swipeRefreshLayout.setRefreshing(true);
                    onRefresh();
                }
                ((MainActivity) getActivity()).jumpToTransaction(peopleId, name);
            }
        }
    }
}
