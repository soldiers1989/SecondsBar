package com.fx.secondbar.ui.date;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.DateBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.http.exception.ApiException;
import com.fx.secondbar.ui.mall.AcMallDetail;
import com.fx.secondbar.ui.mall.DialogBuy;
import com.fx.secondbar.ui.order.AcOrderManage;
import com.fx.secondbar.ui.person.assets.AcRecharge;
import com.fx.secondbar.util.GlideLoad;

import rx.Subscriber;

/**
 * function:约Ta详情
 * author: frj
 * modify date: 2018/10/23
 */
public class AcDateDetail extends ActivitySupport
{

    private ImageView img_avatar;
    private TextView tv_name;
    private TextView tv_account;
    private TextView tv_date_title;
    private TextView tv_location;
    private TextView tv_time;
    private TextView tv_timelength;
    private TextView tv_content;
    private TextView tv_notice;
    private TextView tv_person_count;
    private Button btn_buy;

    private DialogBuy dialogBuy;
    private ProgressDialog dialog;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_date_detail;
    }

    @Override
    protected void initView()
    {
        img_avatar = findView(R.id.img_avatar);
        tv_name = findView(R.id.tv_name);
        tv_account = findView(R.id.tv_account);
        tv_date_title = findView(R.id.tv_date_title);
        tv_location = findView(R.id.tv_location);
        tv_time = findView(R.id.tv_time);
        tv_timelength = findView(R.id.tv_timelength);
        tv_content = findView(R.id.tv_content);
        tv_notice = findView(R.id.tv_notice);
        btn_buy = findView(R.id.btn_buy);
        tv_person_count = findView(R.id.tv_person_count);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_buy.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        getData(getIntent().getStringExtra(KEY_STR));
    }

    /**
     * 获取约TA详情
     *
     * @param id 约TA id
     */
    private void getData(String id)
    {
        HttpManager.getDateDetail(id, new Subscriber<DateBean>()
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
            public void onNext(DateBean dateBean)
            {
                if (isDestroy())
                {
                    return;
                }
                bindData(dateBean);
            }
        });
    }

    /**
     * 绑定约TA数据
     *
     * @param dateBean
     */
    private void bindData(DateBean dateBean)
    {
        if (dateBean != null)
        {
            VerificationUtil.setViewValue(tv_name, dateBean.getNickname());
            VerificationUtil.setViewValue(tv_account, "秒吧号：" + VerificationUtil.verifyDefault(dateBean.getAccount(), ""));
            VerificationUtil.setViewValue(tv_date_title, dateBean.getName());
            VerificationUtil.setViewValue(tv_location, dateBean.getAddress());
            VerificationUtil.setViewValue(tv_time, dateBean.getDatetime());
            VerificationUtil.setViewValue(tv_timelength, "时长" + dateBean.getTimelength());
            GlideLoad.loadCicle(img_avatar, dateBean.getImg(), R.mipmap.default_avatar, R.mipmap.default_avatar);
            String buyBtn = String.format(getString(R.string.date_detail_buy_tips), VerificationUtil.verifyDefault(dateBean.getPrice(), "0"));
            if (btn_buy != null)
            {
                btn_buy.setText(buyBtn);
            }

            if (tv_content != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                {
                    tv_content.setText(Html.fromHtml(VerificationUtil.verifyDefault(dateBean.getContent(), ""), Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH | Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST | Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV | Html.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE | Html.FROM_HTML_OPTION_USE_CSS_COLORS, new AcMallDetail.CustomImageGetter(tv_content), null));
                } else
                {
                    tv_content.setText(Html.fromHtml(VerificationUtil.verifyDefault(dateBean.getContent(), ""), new AcMallDetail.CustomImageGetter(tv_content), null));
                }
            }

            if (tv_notice != null)
            {
                tv_notice.setText(Html.fromHtml(VerificationUtil.verifyDefault(dateBean.getDescription(), "")));
            }
            String personCount = String.format(getString(R.string.date_detail_person_count), VerificationUtil.verifyDefault(dateBean.getQuantity(), "0"));
            if (tv_person_count != null)
            {
                tv_person_count.setText(personCount);
            }
            dialogBuy = new DialogBuy(this, dateBean);
            dialogBuy.setOnBuyListener(new DialogBuy.OnBuyListener()
            {
                @Override
                public void onBuy(String goodsId, boolean isSTEPay)
                {
                    buyDate(goodsId, isSTEPay);
                }
            });
        }
    }

    private void buyDate(String id, final boolean isSTEPay)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        Subscriber<ResponseBean> subscriber = new Subscriber<ResponseBean>()
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
                            rechargeTips(isSTEPay);
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
                if (dialogBuy != null)
                {
                    dialogBuy.dismiss();
                }
                ShowToast.showToast("购买成功");
                //通知更新用户余额信息
                FxApplication.refreshUserInfoBroadCast();
                jump(AcOrderManage.class);
            }
        };
        if (isSTEPay)
        {
            HttpManager.buyDate(id, subscriber);
        } else
        {
            HttpManager.buyQCommodity(id, subscriber);
        }
    }

    /**
     * 余额不足，去充值的提示
     */
    private void rechargeTips(final boolean isSTEPay)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(isSTEPay ? "您的余额不足，请前往充值" : "您的q不足，请多做任务吧！");
        builder.setPositiveButton(isSTEPay ? "去充值" : "做任务", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (dialogBuy != null)
                {
                    dialogBuy.dismiss();
                }
                if (isSTEPay)
                {
                    jump(AcRecharge.class);
                } else
                {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
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
                if (dialogBuy != null)
                {
                    dialogBuy.show();
                }
                break;
        }
    }
}
