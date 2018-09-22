package com.fx.secondbar.ui.home.item;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.item.adapter.AdTutorial;

import java.util.ArrayList;
import java.util.List;

/**
 * function:教程
 * author: frj
 * modify date: 2018/9/7
 */
public class FragmentTutorial extends FragmentViewPagerBase
{

    private RecyclerView recyclerView;
    private AdTutorial adapter;

    @Override
    public void onStarShow()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home_tutorial, container, false);
    }

    @Override
    protected void initView()
    {
        recyclerView = findView(R.id.recyclerView);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        adapter = new AdTutorial(R.layout.ad_tutorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getResources().getDimensionPixelSize(R.dimen.home_tutorial_item_space), false, true, true));
        adapter.bindToRecyclerView(recyclerView);
        adapter.setNewData(getDatas());
    }

    /**
     * 获取图片链接
     *
     * @return
     */
    private List<Integer> getDatas()
    {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.test_turial_1);
        list.add(R.mipmap.test_turial_2);
        list.add(R.mipmap.test_turial_3);
        list.add(R.mipmap.test_turial_4);
        return list;
    }

}
