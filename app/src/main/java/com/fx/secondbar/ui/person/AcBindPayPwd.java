package com.fx.secondbar.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.CountDownButtonHelper;

import rx.Subscriber;

/**
 * function:绑定支付密码
 * author: frj
 * modify date: 2018/9/22
 */
public class AcBindPayPwd extends ActivitySupport
{
    /**
     * 设置支付密码请求码
     */
    private static final int REQUEST_CODE_SET_PAYPWD = 10;

    private TextView tv_title;
    private TextView tv_phone;
    private EditText ed_code;
    private TextView tv_get_code;
    private Button btn_next;
    private CountDownButtonHelper countDownButtonHelper;//倒计时帮助类
    //设置还是修改支付密码
    private int type;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_bind_pay_pwd;
    }

    @Override
    protected void initView()
    {
        tv_title = findView(R.id.tv_title);
        ed_code = findView(R.id.ed_code);
        tv_get_code = findView(R.id.tv_get_code);
        tv_phone = findView(R.id.tv_phone);
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
        type = getIntent().getIntExtra(KEY, AcInputPayPwd.TYPE_SET);
        VerificationUtil.setViewValue(tv_phone, FxApplication.getInstance().getUserInfoBean().getAccount(), "");
        if (AcInputPayPwd.TYPE_SET == type)
        {
            tv_title.setText("设置支付密码");
        } else if (AcInputPayPwd.TYPE_UPDATE == type)
        {
            tv_title.setText("修改支付密码");
        }
    }


    /**
     * 获取短信验证码
     *
     * @param phone
     */
    private void getPhoneCode(String phone)
    {
        HttpManager.getPhoneCode(phone, new Subscriber<ResponseBean>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isDestroy())
                {
                    return;
                }
                e.printStackTrace();
                ShowToast.showToast(HttpManager.checkLoadError(e));
                tv_get_code.setEnabled(true);
            }

            @Override
            public void onNext(ResponseBean responseBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (countDownButtonHelper != null)
                {
                    countDownButtonHelper.start();
                }
            }
        });
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
            case R.id.tv_get_code:
                tv_get_code.setEnabled(false);
                getPhoneCode(FxApplication.getInstance().getUserInfoBean().getAccount());
                break;
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_next:
                if (VerificationUtil.validator(this, ed_code, "请输入短信验证码"))
                {
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_STR, getTextView(ed_code));
                    bundle.putInt(KEY, type);
                    jump(AcInputPayPwd.class, bundle, REQUEST_CODE_SET_PAYPWD);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_SET_PAYPWD == requestCode && RESULT_OK == resultCode)
        {
            setResult(RESULT_OK);
            finish();
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
