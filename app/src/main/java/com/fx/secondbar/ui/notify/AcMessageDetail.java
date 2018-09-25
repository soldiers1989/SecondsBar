package com.fx.secondbar.ui.notify;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:消息详情
 * author: frj
 * modify date: 2018/9/26
 */
public class AcMessageDetail extends ActivitySupport
{

    private TextView tv_time;
    private TextView tv_content;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_message_detail;
    }

    @Override
    protected void initView()
    {
        tv_time = findView(R.id.tv_time);
        tv_content = findView(R.id.tv_content);
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
