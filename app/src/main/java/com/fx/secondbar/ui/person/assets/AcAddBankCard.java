package com.fx.secondbar.ui.person.assets;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:新增银行卡界面
 * author: frj
 * modify date: 2018/9/22
 */
public class AcAddBankCard extends ActivitySupport
{
    private TextView tv_name;
    private TextView tv_card_num;
    private EditText ed_bank_card;
    private EditText ed_bank_name;
    private Button btn_submit;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_add_bank_card;
    }

    @Override
    protected void initView()
    {
        tv_name = findView(R.id.tv_name);
        tv_card_num = findView(R.id.tv_card_num);
        ed_bank_card = findView(R.id.ed_bank_card);
        ed_bank_name = findView(R.id.ed_bank_name);
        btn_submit = findView(R.id.btn_submit);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_submit.setOnClickListener(this);
        ed_bank_name.setOnClickListener(this);
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
            case R.id.btn_submit:
                break;
            case R.id.ed_bank_name:
                jump(AcSelectBank.class);
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
