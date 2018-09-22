package com.fx.secondbar.ui.person.assets;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.BankBean;

import java.util.ArrayList;
import java.util.List;

/**
 * function:我的银行卡
 * author: frj
 * modify date: 2018/9/22
 */
public class AcMyBankCard extends ActivitySupport
{

    private Button btn_add;
    private RecyclerView recyclerView;
    private AdMyBankCard adapter;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_my_bank_card;
    }

    @Override
    protected void initView()
    {
        btn_add = findView(R.id.btn_add);
        recyclerView = findView(R.id.recyclerView);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_add.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 1), false, false, false));
        adapter = new AdMyBankCard();
        adapter.setNewData(getDatas());
        adapter.bindToRecyclerView(recyclerView);
    }

    private List<BankBean> getDatas()
    {
        List<BankBean> datas = new ArrayList<>();
        datas.add(new BankBean("建设银行", R.mipmap.ic_bank_ccb));
        datas.add(new BankBean("招商银行", R.mipmap.ic_bank_cmb));
        datas.add(new BankBean("工商银行", R.mipmap.ic_bank_icbc));
        datas.add(new BankBean("农业银行", R.mipmap.ic_bank_abc));
        return datas;
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
            case R.id.btn_add:
                jump(AcAddBankCard.class);
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
