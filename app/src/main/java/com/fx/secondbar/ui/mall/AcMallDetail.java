package com.fx.secondbar.ui.mall;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.http.exception.ApiException;
import com.fx.secondbar.ui.home.AcShareDialog;
import com.fx.secondbar.ui.order.AcOrderManage;
import com.fx.secondbar.ui.person.assets.AcRecharge;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.GlideLoad;
import com.fx.secondbar.util.ProgressDialogUtil;

import rx.Subscriber;

/**
 * function:商城详情页
 * author: frj
 * modify date: 2018/9/9
 */
public class AcMallDetail extends ActivitySupport
{

    private Toolbar toolbar;
    private ImageButton img_back;
    private ImageButton img_forward;
    private Button btn_buy;
    private ImageView img;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_place;
    private TextView tv_start_time;
    private TextView tv_price;
    private TextView tv_intro;

    private DialogBuy dialogBuy;
    private ProgressDialog dialog;

    //商品图片
    private String goodsPicture;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_mall_detail;
    }

    @Override
    protected void initView()
    {
        toolbar = findView(R.id.toolbar);
        img_back = findView(R.id.img_back);
        img_forward = findView(R.id.img_forward);
        btn_buy = findView(R.id.btn_buy);
        img = findView(R.id.img);
        tv_title = findView(R.id.tv_title);
        tv_time = findView(R.id.tv_time);
        tv_place = findView(R.id.tv_place);
        tv_start_time = findView(R.id.tv_start_time);
        tv_price = findView(R.id.tv_price);
        tv_intro = findView(R.id.tv_intro);
    }

    @Override
    protected void initListener()
    {
        img_back.setOnClickListener(this);
        img_forward.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
        params.width = DisplayUtil.getScreenSize(this).widthPixels;
        //宽高比为30:17
        params.height = params.width * 17 / 30;
        img.setLayoutParams(params);

        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);

        btn_buy.setVisibility(getIntent().getBooleanExtra(KEY, false) ? View.GONE : View.VISIBLE);

        getData(getIntent().getStringExtra(KEY_STR));
    }

    /**
     * 获取商品详情信息
     *
     * @param id
     */
    private void getData(String id)
    {
        HttpManager.getMallDetail(id, new Subscriber<CommodityBean>()
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
            public void onNext(CommodityBean commodityBean)
            {
                if (isDestroy())
                {
                    return;
                }
                bindData(commodityBean);
            }
        });
    }

    /**
     * 绑定数据显示
     *
     * @param bean
     */
    private void bindData(CommodityBean bean)
    {
        if (bean != null)
        {
            goodsPicture = bean.getImage();
            GlideLoad.load(img, bean.getImage());
            VerificationUtil.setViewValue(tv_title, bean.getName());
            VerificationUtil.setViewValue(tv_time, String.format(getString(R.string.mall_detail_info_time), VerificationUtil.verifyDefault(bean.getTimelength(), "0")));
            VerificationUtil.setViewValue(tv_place, String.format(getString(R.string.mall_detail_info_place), VerificationUtil.verifyDefault(bean.getAddress(), "等待客服通知")));
            VerificationUtil.setViewValue(tv_price, String.format(getString(R.string.mall_detail_info_price), VerificationUtil.verifyDefault(bean.getPrice(), "0")));
            VerificationUtil.setViewValue(tv_start_time, String.format(getString(R.string.mall_detail_info_start_time), VerificationUtil.verifyDefault(bean.getDatetime(), "")));
            if (tv_intro != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                {
                    tv_intro.setText(Html.fromHtml(VerificationUtil.verifyDefault(bean.getContent(), ""), Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH | Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST | Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV | Html.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE | Html.FROM_HTML_OPTION_USE_CSS_COLORS, new CustomImageGetter(tv_intro), null));
                } else
                {
                    tv_intro.setText(Html.fromHtml(VerificationUtil.verifyDefault(bean.getContent(), ""), new CustomImageGetter(tv_intro), null));
                }
            }
            dialogBuy = new DialogBuy(this, bean);
            dialogBuy.setOnBuyListener(new DialogBuy.OnBuyListener()
            {
                @Override
                public void onBuy(String goodsId, boolean isSTEPay)
                {
                    buyGoods(goodsId, isSTEPay);
                }
            });
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

    /**
     * 购买商品
     */
    private void buyGoods(String goodsId, final boolean isSTEPay)
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
                jump(AcOrderManage.class, true);
            }
        };
        if (isSTEPay)
        {
            HttpManager.buyCommodity(goodsId, subscriber);
        } else
        {
            HttpManager.buyQCommodity(goodsId, subscriber);
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

    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.img_forward:
                Bundle bundle = new Bundle();
                bundle.putInt(AcShareDialog.KEY_TYPE, AcShareDialog.TYPE_POSTER_GOODS);
                StringBuilder sb = new StringBuilder();
                sb.append(getTextView(tv_title));
                sb.append("==");
                sb.append(getTextView(tv_time));
                sb.append("==");
                sb.append(getTextView(tv_place));
                sb.append("==");
                sb.append(getTextView(tv_price));
                bundle.putString(AcShareDialog.KEY_CONTENT, sb.toString());
                bundle.putString(AcShareDialog.KEY_IMG, goodsPicture);
                jump(AcShareDialog.class, bundle, false);
                break;
            case R.id.btn_buy:
                if (dialogBuy != null)
                {
                    dialogBuy.show();
                }
                break;
        }
    }

    public static class CustomImageGetter implements Html.ImageGetter
    {

        private TextView textView;

        public CustomImageGetter(TextView textView)
        {
            this.textView = textView;
        }

        @Override
        public Drawable getDrawable(String source)
        {
            if (textView != null)
            {
                String url = "";
                if (!TextUtils.isEmpty(source))
                {
                    if (source.startsWith("http"))
                    {
                        url = source;
                    } else
                    {
                        url = Constants.ROOT_URL + source;
                    }
                }
                //级别列表Drawable
                final LevelListDrawable drawable = new LevelListDrawable();
                GlideApp.with(textView.getContext()).asBitmap().load(url).into(new SimpleTarget<Bitmap>()
                {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                    {
                        try
                        {
                            if (resource != null)
                            {
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                                drawable.addLevel(1, 1, bitmapDrawable);
                                drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                                drawable.setLevel(1);
                                CharSequence text = textView.getText();
                                textView.setText(text);
                                textView.refreshDrawableState();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        } catch (Error error)
                        {
                            error.printStackTrace();
                        }
                    }
                });

                return drawable;
            }
            return null;
        }
    }
}
