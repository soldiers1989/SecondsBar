package com.fx.secondbar.ui.person;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.fx.secondbar.util.ProgressDialogUtil;

import rx.Subscriber;

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
    private ProgressDialog dialog;

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
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
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

    /**
     * 绑定手机号
     *
     * @param phone
     * @param code
     */
    private void bindPhone(final String phone, String code)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.bindPhone(phone, code, new Subscriber<ResponseBean>()
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
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(ResponseBean responseBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                ShowToast.showToast("绑定成功");
                FxApplication.refreshUserInfoBroadCast();
                Intent intent = new Intent();
                intent.putExtra(KEY_STR, phone);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_get_code:
                if (VerificationUtil.validator(this, ed_phone, "请输入手机号"))
                {
                    tv_get_code.setEnabled(false);
                    getPhoneCode(getTextView(ed_phone));
                }
                break;
            case R.id.btn_bind:
                if (VerificationUtil.requiredFieldValidator(this, new View[]{ed_phone, ed_code}, new String[]{"请输入手机号", "请输入短信验证码"}))
                {
                    bindPhone(getTextView(ed_phone), getTextView(ed_code));
                }

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
