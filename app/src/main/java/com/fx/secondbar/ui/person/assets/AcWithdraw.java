package com.fx.secondbar.ui.person.assets;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:提现
 * author: frj
 * modify date: 2018/9/22
 */
public class AcWithdraw extends ActivitySupport
{

    private LinearLayout ll_bank;
    private TextView tv_all;
    private TextView tv_conversion_tips;
    private TextView tv_select_card;    //选中的银行卡
    private Button btn_withdraw;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_withdraw;
    }

    @Override
    protected void initView()
    {
        ll_bank = findView(R.id.ll_bank);
        tv_all = findView(R.id.tv_all);
        tv_conversion_tips = findView(R.id.tv_conversion_tips);
        tv_select_card = findView(R.id.tv_select_card);
        btn_withdraw = findView(R.id.btn_withdraw);

        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_withdraw.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        ll_bank.setOnClickListener(this);
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
            case R.id.btn_withdraw:
                jump(AcVerified.class);
                break;
            case R.id.tv_all:
                break;
            case R.id.ll_bank:
                jump(AcMyBankCard.class);
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
