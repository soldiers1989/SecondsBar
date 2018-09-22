package com.fx.secondbar.ui.person.assets;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:实名认证界面
 * author: frj
 * modify date: 2018/9/22
 */
public class AcVerified extends ActivitySupport
{
    private EditText ed_name;
    private EditText ed_card_num;
    private Button btn_next;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_verified;
    }

    @Override
    protected void initView()
    {
        ed_name = findView(R.id.ed_name);
        ed_card_num = findView(R.id.ed_card_num);
        btn_next = findView(R.id.btn_next);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_next.setOnClickListener(this);
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
            case R.id.btn_next:
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
