package com.fx.secondbar.ui.mall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;

/**
 * function:商城页各分类的子页
 * author: frj
 * modify date: 2018/9/9
 */
public class FragmentMallItem extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    public static FragmentMallItem newInstance()
    {
        return new FragmentMallItem();
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private AdMall adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home_time, container, false);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getContext().getResources().getDimensionPixelSize(R.dimen.mall_item_space), false, false, false));
        adapter = new AdMall();
        adapter.bindToRecyclerView(recyclerView);
//        adapter.setNewData(getDatas());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                jump(AcMallDetail.class);
            }
        });
    }

//    private List<CommodityBean> getDatas()
//    {
//        List<CommodityBean> list = new ArrayList<>();
//        list.add(new CommodityBean(R.mipmap.test_mall_1, "周杰伦粉丝见面演唱会", "26000.00STE", "", ""));
//        list.add(new CommodityBean(R.mipmap.test_mall_2, "张勇新零售讲座", "8800.00STE", "", ""));
//        list.add(new CommodityBean(R.mipmap.test_mall_3, "网红陈一发儿歌友会", "1820.00STE", "", ""));
//        list.add(new CommodityBean(R.mipmap.test_mall_4, "郭德纲专场演出", "360.00STE", "", ""));
//        return list;
//    }

    @Override
    public void onRefresh()
    {

    }

    @Override
    public void onStarShow()
    {

    }
}
