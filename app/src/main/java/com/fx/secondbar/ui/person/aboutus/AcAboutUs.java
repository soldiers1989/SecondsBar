package com.fx.secondbar.ui.person.aboutus;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:关于我们
 * author: frj
 * modify date: 2018/9/21
 */
public class AcAboutUs extends ActivitySupport
{
    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_about_us;
    }

    @Override
    protected void initView()
    {
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.tv_protocol).setOnClickListener(this);
        findView(R.id.tv_privacy).setOnClickListener(this);
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
            case R.id.tv_protocol:
                break;
            case R.id.tv_privacy:
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
