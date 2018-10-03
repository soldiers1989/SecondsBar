package com.fx.secondbar.ui.person.assets;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.Arithmetic;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.WithdrawIntroBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.CashierInputFilter;
import com.fx.secondbar.util.ProgressDialogUtil;

import rx.Subscriber;

/**
 * function:提现
 * author: frj
 * modify date: 2018/9/22
 */
public class AcWithdraw extends ActivitySupport
{
    /**
     * 选择银行卡
     */
    private static final int REQUEST_SELECT_CARD = 10;

    private LinearLayout ll_bank;
    private TextView tv_all;
    private TextView tv_conversion_tips;
    private TextView tv_select_card;    //选中的银行卡
    private Button btn_withdraw;
    private EditText ed_input;

    private ProgressDialog dialog;
    //提现比值,STE兑人民币
    private double ratio;
    //可提现数量
    private double canWithdrawNum;
    //手续费比例
    private double feesRatio;
    //最少手续费
    private double minFees;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_withdraw;
    }

    @Override
    protected void initView()
    {
        ll_bank = findView(R.id.ll_bank);
        tv_all = findView(R.id.tv_all);
        tv_conversion_tips = findView(R.id.tv_conversion_tips);
        tv_select_card = findView(R.id.tv_select_card);
        btn_withdraw = findView(R.id.btn_withdraw);
        ed_input = findView(R.id.ed_input);

        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_withdraw.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        ll_bank.setOnClickListener(this);
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
                    if (!TextUtils.isEmpty(s))
                    {
                        double ste = Double.parseDouble(s.toString());
                        if (Double.compare(ste, canWithdrawNum) > 0)
                        {
                            s.clear();
                            s.append(Arithmetic.doubleToStr(canWithdrawNum));
                            return;
                        }
                        setWithdrawFeesTips(ratio, calFees(ste), minFees, feesRatio);
                    }
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initData()
    {
        addFilter(ed_input, new CashierInputFilter());
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
        getIntro();
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
     * 获取提现说明
     */
    private void getIntro()
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.getWithdrawIntro(new Subscriber<WithdrawIntroBean>()
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
            public void onNext(WithdrawIntroBean withdrawIntroBean)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                if (isDestroy())
                {
                    return;
                }
                VerificationUtil.setViewValue(tv_conversion_tips, withdrawIntroBean.getExplaination());
                if (ed_input != null)
                {
                    ed_input.setHint("可提现" + Arithmetic.doubleToStr(withdrawIntroBean.getAmount()));
                }
                ratio = withdrawIntroBean.getRatio();
                canWithdrawNum = withdrawIntroBean.getAmount();
                feesRatio = withdrawIntroBean.getFeesratio();
                minFees = withdrawIntroBean.getMinfees();
            }
        });
    }

    /**
     * 计算手续费
     *
     * @param ste 提现数量
     * @return
     */
    private double calFees(double ste)
    {
        return Arithmetic.mul(ste, feesRatio / 100);
    }

    /**
     * 全部提现
     *
     * @return
     */
    private double withdrawAll()
    {
        double all = Arithmetic.div(canWithdrawNum, 1 + feesRatio / 100, 2);
        return Math.abs(all);
    }

    /**
     * 设置提现手续费提示
     *
     * @param ratio     STE兑人民币比值
     * @param fees      手续费
     * @param minFees   最少手续费
     * @param feesRatio 手续费比值
     */
    private void setWithdrawFeesTips(double ratio, double fees, double minFees, double feesRatio)
    {
        String tips = String.format(getString(R.string.withdraw_fees_tips), Arithmetic.doubleToStr(ratio), Arithmetic.doubleToStr(Math.max(fees, minFees)), Arithmetic.doubleToStr(feesRatio));
        VerificationUtil.setViewValue(tv_conversion_tips, tips);
    }

    /**
     * 获取银行卡尾号
     *
     * @param bankNo
     * @return
     */
    private String getBankNoEnd(String bankNo)
    {
        if (TextUtils.isEmpty(bankNo))
        {
            return "";
        }
        if (bankNo.length() > 4)
        {
            return bankNo.substring(bankNo.length() - 4);
        }
        return bankNo;
    }

    /**
     * 提现
     *
     * @param amount 提现数量
     * @param bankNo 银行卡号
     */
    private void withdraw(String amount, String bankNo)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.withdraw(amount, bankNo, new Subscriber<ResponseBean>()
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
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                e.printStackTrace();
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
                FxApplication.refreshUserInfoBroadCast();
                ShowToast.showToast("提现申请成功");
                ed_input.setText("");
                getIntro();
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
            case R.id.btn_withdraw:
                if (TextUtils.isEmpty(getTextView(ed_input)))
                {
                    ShowToast.showToast("请输入提现数量");
                    return;
                }
                String bankNo = (String) tv_select_card.getTag();
                if (TextUtils.isEmpty(bankNo))
                {
                    ShowToast.showToast("请选择银行卡");
                    return;
                }
                withdraw(getTextView(ed_input), bankNo);
                break;
            case R.id.tv_all:   //全部提现
                double ste = withdrawAll();
                setWithdrawFeesTips(ratio, Arithmetic.mul(canWithdrawNum, ste), minFees, feesRatio);
                ed_input.setText(Arithmetic.doubleToStr(ste));
                break;
            case R.id.ll_bank:
                jump(AcMyBankCard.class, REQUEST_SELECT_CARD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_SELECT_CARD == requestCode && RESULT_OK == resultCode)
        {
            if (data == null)
            {
                return;
            }
            String bankNo = data.getStringExtra(KEY_STR);
            tv_select_card.setText("尾号" + getBankNoEnd(bankNo));
            if (!TextUtils.isEmpty(bankNo))
            {
                tv_select_card.setTag(bankNo);
            }
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
