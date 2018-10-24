package com.fx.secondbar.ui.home;

import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:收益提醒对话框
 * author: frj
 * modify date: 2018/10/24
 */
public class AcIncomeDialog extends ActivitySupport
{

    private TextView tv_count;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_income_dialog;
    }

    @Override
    protected void initView()
    {
        tv_count = findView(R.id.tv_count);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        tv_count.setText("收益+" + getIntent().getStringExtra(KEY_STR));
        tv_count.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                finish();
            }
        }, 2 * 1000);
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
