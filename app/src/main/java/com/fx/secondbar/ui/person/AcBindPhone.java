package com.fx.secondbar.ui.person;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;
import com.fx.secondbar.util.CountDownButtonHelper;

/**
 * function:绑定手机号
 * author: frj
 * modify date: 2018/9/22
 */
public class AcBindPhone extends ActivitySupport
{

    private EditText ed_phone;
    private EditText ed_code;
    private TextView tv_get_code;
    private Button btn_bind;
    private CountDownButtonHelper countDownButtonHelper;//倒计时帮助类

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_bind_phone;
    }

    @Override
    protected void initView()
    {
        ed_phone = findView(R.id.ed_phone);
        ed_code = findView(R.id.ed_code);
        tv_get_code = findView(R.id.tv_get_code);
        btn_bind = findView(R.id.btn_bind);
    }

    @Override
    protected void initListener()
    {
        btn_bind.setOnClickListener(this);
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
            case R.id.btn_bind:
                finish();
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
