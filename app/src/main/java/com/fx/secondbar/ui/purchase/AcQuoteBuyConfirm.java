package com.fx.secondbar.ui.purchase;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.PurchaseInfoBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.http.exception.ApiException;
import com.fx.secondbar.ui.person.assets.AcRecharge;
import com.fx.secondbar.util.GlideLoad;
import com.fx.secondbar.util.ProgressDialogUtil;
import com.joooonho.SelectableRoundedImageView;

import rx.Subscriber;

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
    private TextView tv_total;

    //单价
    private double price;
    //每人限制购买数量
    private int limitSeconds;
    //申购id
    private String purchaseId;

    private ProgressDialog dialog;
    //最小购买数量
    private int minCount = 0;

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
        tv_total = findView(R.id.tv_total);
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.btn_buy).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
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
                    int seconds = 0;
                    if (TextUtils.isEmpty(s))
                    {
                        seconds = 0;
                    } else
                    {
                        seconds = Integer.parseInt(s.toString());
                        if (seconds * 1000 < minCount)
                        {
                            s.clear();
                            s.append(String.valueOf(minCount / 1000));
                            ShowToast.showToast("最小申购" + minCount + "秒");
                        }
                        if (seconds * 1000 > limitSeconds)
                        {
                            s.clear();
                            s.append(String.valueOf(limitSeconds / 1000));
                            ShowToast.showToast(FxApplication.getInstance(), "超过限额。最大购买秒数为=（持仓市值+1万元）/上新价格,当前最多可申购" + limitSeconds + "秒", true);
                        }
                    }
                    double pay = price * seconds * 1000;
                    VerificationUtil.setViewValue(tv_pay, String.format(getString(R.string.mall_detail_info_price), String.valueOf(pay)));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initData()
    {
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
        bindData((PurchaseInfoBean) getIntent().getParcelableExtra(KEY));
    }

    /**
     * 绑定数据
     *
     * @param bean
     */
    private void bindData(PurchaseInfoBean bean)
    {
        if (bean != null)
        {
            GlideLoad.load(img_avatar, bean.getPeopleimg(), true);
            VerificationUtil.setViewValue(tv_name, bean.getPeoplename());
            VerificationUtil.setViewValue(tv_price, String.format(getString(R.string.purchase_money_text), VerificationUtil.verifyDefault(bean.getPrice(), "0")));
            VerificationUtil.setViewValue(tv_available_tips, String.format(getString(R.string.purchase_confirm_buy_available_tips), String.valueOf(bean.getLimitseconds())));
            VerificationUtil.setViewValue(tv_total, VerificationUtil.verifyDefault(bean.getAmount(), "0") + "秒");
//            ed_input.setText(String.valueOf(bean.getLimitseconds()));
            purchaseId = bean.getPurchase_ID();
            minCount = bean.getRemainnum();
            try
            {
                price = Double.parseDouble(bean.getPrice());
                limitSeconds = bean.getLimitseconds();
//                double pay = price * limitSeconds;
//                VerificationUtil.setViewValue(tv_pay, String.format(getString(R.string.mall_detail_info_price), String.valueOf(pay)));
            } catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 余额不足，去充值的提示
     */
    private void rechargeTips()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("您的余额不足，请前往充值");
        builder.setPositiveButton("去充值", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                jump(AcRecharge.class);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * 申购购买
     */
    private void purchaseBuy(String purchaseId, String seconds)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.purchaseBuy(purchaseId, seconds, new Subscriber<ResponseBean>()
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
                if (e instanceof ApiException)
                {
                    ApiException exception = (ApiException) e;
                    if (exception != null)
                    {
                        //表示余额不足，请前往充值
                        if ("3".equals(exception.getErrorCode()))
                        {
                            rechargeTips();
                            return;
                        }
                    }
                    ShowToast.showToast(HttpManager.checkLoadError(e));
                } else
                {
                    ShowToast.showToast(HttpManager.checkLoadError(e));
                }
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
                ShowToast.showToast("申购成功");
                //通知更新用户余额信息
                FxApplication.refreshUserInfoBroadCast();
                jump(AcMyPurchase.class, true);
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
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_buy:
                if (VerificationUtil.validator(this, ed_input, "请输入申购秒数"))
                {
                    purchaseBuy(purchaseId, getTextView(ed_input));
                }
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
