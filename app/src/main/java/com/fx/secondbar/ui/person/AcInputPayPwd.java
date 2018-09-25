package com.fx.secondbar.ui.person;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.ProgressDialogUtil;
import com.fx.secondbar.view.PayPwdView;
import com.fx.secondbar.view.PwdInputMethodView;

import rx.Subscriber;

/**
 * function:输入支付密码
 * author: frj
 * modify date: 2018/9/22
 */
public class AcInputPayPwd extends ActivitySupport
{
    /**
     * 表示设置支付密码
     */
    public static final int TYPE_SET = 1;
    /**
     * 表示修改支付密码
     */
    public static final int TYPE_UPDATE = 2;
    /**
     * 表示输入支付密码校验
     */
    public static final int TYPE_INPUT = 3;

    private PayPwdView payPwdView;
    private PwdInputMethodView pwdInputMethodView;
    private TextView tv_input_tips;
    private TextView tv_title;
    //当前界面逻辑处理类型
    private int type;
    //如果当前是设置支付密码，该值有效，表示是否是第一次输入
    private boolean isFirst = true;
    //如果当前是设置支付密码，该值有效，表示第一次输入的值
    private String firstPwd;

    private ProgressDialog dialog;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_input_pay_pwd;
    }

    @Override
    protected void initView()
    {
        tv_title = findView(R.id.tv_title);
        payPwdView = findView(R.id.payPwdView);
        pwdInputMethodView = findView(R.id.pwdInputMethodView);
        tv_input_tips = findView(R.id.tv_input_tips);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        payPwdView.setInputCallBack(new PayPwdView.InputCallBack()
        {
            @Override
            public void onInputFinish(String result)
            {
                if (TYPE_SET == type || TYPE_UPDATE == type)
                {
                    if (isFirst)
                    {
                        payPwdView.clearResult();
                        tv_input_tips.setText("请再次输入支付密码");
                        firstPwd = result;
                        isFirst = false;
                    } else
                    {
                        if (firstPwd != null && firstPwd.equals(result))
                        {
                            if (TYPE_SET == type)
                            {
                                setPayPwd(result);
                            } else
                            {
                                updatePayPwd(result);
                            }
                        } else
                        {
                            payPwdView.clearResult();
                            isFirst = true;
                            firstPwd = "";
                            tv_input_tips.setText("两次输入不一致，请重新输入");
                            tv_input_tips.postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    tv_input_tips.setText("请输入支付密码");
                                }
                            }, 2 * 1000);
                        }
                    }
                } else
                {
                    finish();
                }
            }
        });
    }

    @Override
    protected void initData()
    {
        payPwdView.setInputMethodView(pwdInputMethodView);
        type = getIntent().getIntExtra(KEY, TYPE_SET);
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
        if (TYPE_INPUT == type)
        {
            tv_title.setText("校验支付密码");
        } else if (TYPE_SET == type)
        {
            tv_title.setText("设置支付密码");
        } else
        {
            tv_title.setText("修改支付密码");
        }
    }

    /**
     * 设置支付密码
     *
     * @param payPwd 支付密码
     */
    private void setPayPwd(String payPwd)
    {
        if (dialog != null)
        {
            dialog.show();
        }

        HttpManager.setPayPwd(payPwd, new Subscriber<ResponseBean>()
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
                ShowToast.showToast("设置成功");
                FxApplication.refreshUserInfoBroadCast();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    /**
     * 设置支付密码
     *
     * @param payPwd 支付密码
     */
    private void updatePayPwd(String payPwd)
    {
        if (dialog != null)
        {
            dialog.show();
        }

        HttpManager.updatePayPwd(payPwd, new Subscriber<ResponseBean>()
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
                ShowToast.showToast("修改成功");
                setResult(RESULT_OK);
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
