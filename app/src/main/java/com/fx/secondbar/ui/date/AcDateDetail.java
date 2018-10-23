package com.fx.secondbar.ui.date;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:约Ta详情
 * author: frj
 * modify date: 2018/10/23
 */
public class AcDateDetail extends ActivitySupport
{

    private ImageView img_avatar;
    private TextView tv_name;
    private TextView tv_account;
    private TextView tv_date_title;
    private TextView tv_location;
    private TextView tv_time;
    private TextView tv_timelength;
    private TextView tv_content;
    private TextView tv_notice;
    private Button btn_buy;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_date_detail;
    }

    @Override
    protected void initView()
    {
        img_avatar = findView(R.id.img_avatar);
        tv_name = findView(R.id.tv_name);
        tv_account = findView(R.id.tv_account);
        tv_date_title = findView(R.id.tv_date_title);
        tv_location = findView(R.id.tv_location);
        tv_time = findView(R.id.tv_time);
        tv_timelength = findView(R.id.tv_timelength);
        tv_content = findView(R.id.tv_content);
        tv_notice = findView(R.id.tv_notice);
        btn_buy = findView(R.id.btn_buy);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_buy.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {

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
            case R.id.btn_buy:
                break;
        }
    }
}
