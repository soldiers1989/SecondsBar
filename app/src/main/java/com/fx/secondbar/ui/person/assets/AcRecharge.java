package com.fx.secondbar.ui.person.assets;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

/**
 * function:充值
 * author: frj
 * modify date: 2018/9/22
 */
public class AcRecharge extends ActivitySupport
{

    private EditText ed_input;
    private TextView tv_wechat;
    private TextView tv_alipay;
    private Button btn_pay;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_recharge;
    }

    @Override
    protected void initView()
    {
        ed_input = findView(R.id.ed_input);
        tv_wechat = findView(R.id.tv_wechat);
        tv_alipay = findView(R.id.tv_alipay);
        btn_pay = findView(R.id.btn_pay);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        tv_wechat.setOnClickListener(this);
        tv_alipay.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
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
            case R.id.tv_wechat:
                if (tv_alipay.isSelected())
                {
                    tv_alipay.setSelected(false);
                }
                tv_wechat.setSelected(true);
                break;
            case R.id.tv_alipay:
                if (tv_wechat.isSelected())
                {
                    tv_wechat.setSelected(false);
                }
                tv_alipay.setSelected(true);
                break;
            case R.id.btn_pay:
                break;
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
