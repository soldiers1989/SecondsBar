package com.fx.secondbar.ui.person.assets;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.PayResult;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.CashierInputFilter;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    private LinearLayout ll_fixed_amount;

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
        ll_fixed_amount = findView(R.id.ll_fixed_amount);
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
        ed_input.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                try
                {
                    double money = Double.parseDouble(s.toString());
                    btn_pay.setEnabled(money > 0);
                    btn_pay.setText(setPayTips());
                } catch (Exception e)
                {
                    e.printStackTrace();
                    btn_pay.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void initData()
    {
        btn_pay.setEnabled(false);
        tv_wechat.setSelected(true);
        tv_alipay.setSelected(false);
        addFilter(ed_input, new CashierInputFilter());
        bindFixedAmountClick();
    }

    /**
     * 添加输入过滤器
     *
     * @param ed
     * @param inputFilter
     */
    private void addFilter(EditText ed, InputFilter inputFilter)
    {
        InputFilter inputFilters[] = ed.getFilters();
        InputFilter afterFilters[] = null;
        if (inputFilters != null)
        {
            afterFilters = new InputFilter[inputFilters.length + 1];
            for (int i = 0; i < inputFilters.length; i++)
            {
                afterFilters[i] = inputFilters[i];
            }
            afterFilters[inputFilters.length] = inputFilter;
        } else
        {
            afterFilters = new InputFilter[1];
            afterFilters[0] = inputFilter;
        }
        ed.setFilters(afterFilters);
    }

    /**
     * 设置支付提示
     *
     * @return
     */
    private String setPayTips()
    {
        return String.format(getString(R.string.recharge_pay_tips), tv_wechat.isSelected() ? getTextView(tv_wechat) : getTextView(tv_alipay), getTextView(ed_input));
    }

    /**
     * 固定充值金额的点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (isFastDoubleClick(v))
            {
                return;
            }
            if (v instanceof TextView)
            {
                TextView textView = (TextView) v;
                ed_input.setText(textView.getText().toString().replace("￥", ""));
            }
        }
    };

    /**
     * 绑定固定充值金额点击
     */
    private void bindFixedAmountClick()
    {
        int count = ll_fixed_amount.getChildCount();
        for (int i = 0; i < count; i++)
        {
            LinearLayout linearLayout = (LinearLayout) ll_fixed_amount.getChildAt(i);
            for (int j = 0; j < linearLayout.getChildCount(); j++)
            {
                linearLayout.getChildAt(j).setOnClickListener(onClickListener);
            }
        }
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
            case R.id.tv_wechat:
                if (tv_alipay.isSelected())
                {
                    tv_alipay.setSelected(false);
                }
                tv_wechat.setSelected(true);
                btn_pay.setText(setPayTips());
                break;
            case R.id.tv_alipay:
                if (tv_wechat.isSelected())
                {
                    tv_wechat.setSelected(false);
                }
                tv_alipay.setSelected(true);
                btn_pay.setText(setPayTips());
                break;
            case R.id.btn_pay:
                getRechargeOrderInfo(getTextView(ed_input));
                break;
            case R.id.ib_back:
                finish();
                break;
        }
    }

    /**
     * 获取充值订单支付字符串
     *
     * @param money 充值金额
     */
    private void getRechargeOrderInfo(String money)
    {
        HttpManager.getRechargeOrderInfo(money, new Subscriber<String>()
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
            }

            @Override
            public void onNext(String s)
            {
                if (isDestroy())
                {
                    return;
                }
                alipay(s);
            }
        });
    }

    /**
     * 支付宝支付
     *
     * @param orderStr 支付字符串
     */
    private void alipay(String orderStr)
    {
        Observable.just(orderStr).map(new Func1<String, PayResult>()
        {
            @Override
            public PayResult call(String s)
            {
                PayTask task = new PayTask(AcRecharge.this);
                Map<String, String> result = task.payV2(s, true);
                return new PayResult(result);
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<PayResult>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                e.printStackTrace();
            }

            @Override
            public void onNext(PayResult payResult)
            {
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000"))
                {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    ShowToast.showToast("支付成功");
                    FxApplication.refreshUserInfoBroadCast();
                    jump(AcAssetDetail.class, true);
                } else
                {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    ShowToast.showToast("支付失败");
                }
            }
        });
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
