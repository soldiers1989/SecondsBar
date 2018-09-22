package com.fx.secondbar.ui.quote;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.order.AcOrderDetail;
import com.joooonho.SelectableRoundedImageView;

/**
 * function:申购购买信息确认
 * author: frj
 * modify date: 2018/9/21
 */
public class AcQuoteBuyConfirm extends ActivitySupport
{

    private SelectableRoundedImageView img_avatar;
    private TextView tv_name;
    private TextView tv_price;
    private TextView tv_available_tips;
    private EditText ed_input;
    private TextView tv_pay;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_quote_buy_confirm;
    }

    @Override
    protected void initView()
    {
        img_avatar = findView(R.id.img_avatar);
        tv_name = findView(R.id.tv_name);
        tv_price = findView(R.id.tv_price);
        tv_available_tips = findView(R.id.tv_available_tips);
        ed_input = findView(R.id.ed_input);
        tv_pay = findView(R.id.tv_pay);
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.btn_buy).setOnClickListener(this);
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
            case R.id.btn_buy:
                jump(AcOrderDetail.class);
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
