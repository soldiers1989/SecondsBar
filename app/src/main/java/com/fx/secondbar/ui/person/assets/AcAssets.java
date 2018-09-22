package com.fx.secondbar.ui.person.assets;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.R;

/**
 * function:我的资产
 * author: frj
 * modify date: 2018/9/21
 */
public class AcAssets extends ActivitySupport
{
    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_assets;
    }

    @Override
    protected void initView()
    {
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.tv_detail).setOnClickListener(this);
        findView(R.id.tv_recharge).setOnClickListener(this);
        findView(R.id.tv_withdraw).setOnClickListener(this);
        findView(R.id.ll_conversion).setOnClickListener(this);
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
            case R.id.tv_detail:
                jump(AcAssetDetail.class);
                break;
            case R.id.tv_recharge:
                jump(AcRecharge.class);
                break;
            case R.id.tv_withdraw:
                jump(AcWithdraw.class);
                break;
            case R.id.ll_conversion:
                ShowToast.showToast("暂未开放");
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
