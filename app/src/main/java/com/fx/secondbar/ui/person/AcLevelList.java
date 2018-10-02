package com.fx.secondbar.ui.person;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.LevelBean;
import com.fx.secondbar.http.HttpManager;

import java.util.List;

import rx.Subscriber;

/**
 * function:等级列表
 * author: frj
 * modify date: 2018/10/2
 */
public class AcLevelList extends ActivitySupport
{

    private RecyclerView recyclerView;
    private AdLevelItem adapter;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_level_list;
    }

    @Override
    protected void initView()
    {
        recyclerView = findView(R.id.recyclerView);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 15), false, false, true));
        adapter = new AdLevelItem();
        adapter.bindToRecyclerView(recyclerView);
        getData();
    }

    /**
     * 获取数据
     */
    private void getData()
    {
        HttpManager.getLevelList(new Subscriber<List<LevelBean>>()
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
            }

            @Override
            public void onNext(List<LevelBean> levelBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                adapter.setNewData(levelBeans);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
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
}
