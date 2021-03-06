package com.fx.secondbar.ui.person.assets;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.fx.secondbar.R;

/**
 * function:请选择开户行
 * author: frj
 * modify date: 2018/9/22
 */
public class AcSelectBank extends ActivitySupport
{

    private EditText ed_search;
    private Button btn_confirm;
    private RecyclerView recyclerView;
    private AdSelectBank adapter;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_select_bank;
    }

    @Override
    protected void initView()
    {
        btn_confirm = findView(R.id.btn_confirm);
        ed_search = findView(R.id.ed_search);
        recyclerView = findView(R.id.recyclerView);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_confirm.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 1), false, false, false));
        adapter = new AdSelectBank();
        adapter.bindToRecyclerView(recyclerView);
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
            case R.id.btn_confirm:
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
