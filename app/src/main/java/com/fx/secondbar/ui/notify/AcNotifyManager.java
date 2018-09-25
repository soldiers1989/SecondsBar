package com.fx.secondbar.ui.notify;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:消息管理
 * author: frj
 * modify date: 2018/9/25
 */
public class AcNotifyManager extends ActivitySupport
{

    private TextView tv_unread_system;
    private TextView tv_unread_anno;


    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_notify_manager;
    }

    @Override
    protected void initView()
    {
        tv_unread_system = findView(R.id.tv_unread_system);
        tv_unread_anno = findView(R.id.tv_unread_anno);
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.v_system).setOnClickListener(this);
        findView(R.id.v_notify).setOnClickListener(this);
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
            case R.id.v_system:
                break;
            case R.id.v_notify:
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
