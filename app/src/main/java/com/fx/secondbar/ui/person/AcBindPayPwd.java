package com.fx.secondbar.ui.person;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;
import com.fx.secondbar.util.CountDownButtonHelper;

/**
 * function:绑定支付密码
 * author: frj
 * modify date: 2018/9/22
 */
public class AcBindPayPwd extends ActivitySupport
{

    private EditText ed_code;
    private TextView tv_get_code;
    private Button btn_next;
    private CountDownButtonHelper countDownButtonHelper;//倒计时帮助类

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_bind_pay_pwd;
    }

    @Override
    protected void initView()
    {
        ed_code = findView(R.id.ed_code);
        tv_get_code = findView(R.id.tv_get_code);
        btn_next = findView(R.id.btn_next);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_next.setOnClickListener(this);
        tv_get_code.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        countDownButtonHelper = new CountDownButtonHelper(tv_get_code, "获取短信验证码", 60, 1, this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_get_code:
                if (countDownButtonHelper != null)
                {
                    countDownButtonHelper.start();
                }
                break;
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_next:
                jump(AcInputPayPwd.class, AcInputPayPwd.TYPE_SET, true);
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        if (countDownButtonHelper != null)
        {
            countDownButtonHelper.stop();
        }
        super.onDestroy();
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
