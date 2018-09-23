package com.fx.secondbar.ui.mall;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.DialogShare;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.GlideLoad;

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
    private TextView tv_price;
    private TextView tv_intro;


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
            GlideLoad.load(img, bean.getImage());
            VerificationUtil.setViewValue(tv_title, bean.getName());
            VerificationUtil.setViewValue(tv_time, String.format(getString(R.string.mall_detail_info_time), VerificationUtil.verifyDefault(bean.getTimelength(), "0")));
            VerificationUtil.setViewValue(tv_place, String.format(getString(R.string.mall_detail_info_place), VerificationUtil.verifyDefault(bean.getAddress(), "等待客服通知")));
            VerificationUtil.setViewValue(tv_price, String.format(getString(R.string.mall_detail_info_price), VerificationUtil.verifyDefault(bean.getPrice(), "0")));
            if (tv_intro != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                {
                    tv_intro.setText(Html.fromHtml(bean.getContent(), Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH | Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST | Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV | Html.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE | Html.FROM_HTML_OPTION_USE_CSS_COLORS, new Html.ImageGetter()
                    {
                        @Override
                        public Drawable getDrawable(String source)
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
                            GlideApp.with(AcMallDetail.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>()
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
                                            CharSequence text = tv_intro.getText();
                                            tv_intro.setText(text);
                                            tv_intro.refreshDrawableState();
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
                    }, null));
                } else
                {
                    tv_intro.setText(Html.fromHtml(bean.getContent(), new Html.ImageGetter()
                    {
                        @Override
                        public Drawable getDrawable(String source)
                        {
                            return null;
                        }
                    }, null));
                }
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

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.img_forward:
                new DialogShare(this).show();
                break;
            case R.id.btn_buy:
                new DialogBuy(this).show();
                break;
        }
    }
}
