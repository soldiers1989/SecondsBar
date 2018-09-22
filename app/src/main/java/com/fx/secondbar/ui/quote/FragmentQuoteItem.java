package com.fx.secondbar.ui.quote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.QuoteBean;
import com.fx.secondbar.ui.home.adapter.AdQuote;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.ui.mall.AcMallDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * function:行情页Item
 * author: frj
 * modify date: 2018/9/10
 */
public class FragmentQuoteItem extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private AdQuote adapter;

    public static FragmentQuoteItem newInstance()
    {
        return new FragmentQuoteItem();
    }

    @Override
    public void onStarShow()
    {

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
        swipeRefreshLayout.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getContext().getResources().getDimensionPixelSize(R.dimen.mall_item_space), false, false, false));
        adapter = new AdQuote();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setNewData(getDatas());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                jump(AcQuoteDetail.class);
            }
        });
    }

    private List<QuoteBean> getDatas()
    {
        List<QuoteBean> list = new ArrayList<>();
        list.add(new QuoteBean(R.mipmap.test_quoto_1, "李优生\nLYS", "2.363", "+0.08", "+2.53%", true));
        list.add(new QuoteBean(R.mipmap.test_quoto_2, "陈浩\nCHH", "1.260", "-0.26", "-2.53%", false));
        list.add(new QuoteBean(R.mipmap.test_quoto_3, "李小二\nLXE", "0.685", "-0.26", "-2.53%", false));
        list.add(new QuoteBean(R.mipmap.test_quoto_4, "于大宝\nYDB", "3.689", "+0.08", "+2.53%", true));
        list.add(new QuoteBean(R.mipmap.test_quoto_1, "王思明\nWSM", "2.363", "+0.08", "+2.53%", true));
        list.add(new QuoteBean(R.mipmap.test_quoto_2, "李小二\nLXE", "2.363", "+0.08", "+2.53%", true));
        list.add(new QuoteBean(R.mipmap.test_quoto_3, "陈浩\nCHH", "1.260", "-0.26", "-2.53%", false));
        list.add(new QuoteBean(R.mipmap.test_quoto_4, "于大宝\nYDB", "3.689", "+0.08", "+2.53%", true));
        return list;
    }

    @Override
    public void onRefresh()
    {

    }
}
