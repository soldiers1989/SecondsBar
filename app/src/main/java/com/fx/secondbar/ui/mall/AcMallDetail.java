package com.fx.secondbar.ui.mall;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DensityUtil;
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
import com.fx.secondbar.util.ScaleTransform;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

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
//        //宽高比为30:17
//        params.height = params.width * 17 / 30;
        //宽高比为1:1
//        params.height = params.width;
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
            GlideLoad.loadFitCenter(img, bean.getImage(), R.drawable.ic_default_adimage, R.drawable.ic_default_adimage);
            VerificationUtil.setViewValue(tv_title, bean.getName());
            VerificationUtil.setViewValue(tv_time, String.format(getString(R.string.mall_detail_info_time), VerificationUtil.verifyDefault(bean.getTimelength(), "0")));
            VerificationUtil.setViewValue(tv_place, String.format(getString(R.string.mall_detail_info_place), VerificationUtil.verifyDefault(bean.getAddress(), "等待客服通知")));
            if (0 == bean.getPaytype())
            {
                VerificationUtil.setViewValue(tv_price, String.format(getString(R.string.mall_detail_info_price), VerificationUtil.verifyDefault(bean.getPrice(), "0")));
            } else
            {
                VerificationUtil.setViewValue(tv_price, String.format(getString(R.string.mall_detail_info_q_price), VerificationUtil.verifyDefault(bean.getQcoin(), "0")));
            }
            VerificationUtil.setViewValue(tv_start_time, String.format(getString(R.string.mall_detail_info_start_time), VerificationUtil.verifyDefault(bean.getDatetime(), "")));
            if (tv_intro != null)
            {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                {
//                    tv_intro.setText(Html.fromHtml(VerificationUtil.verifyDefault(bean.getContent(), ""), Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH | Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST | Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV | Html.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE | Html.FROM_HTML_OPTION_USE_CSS_COLORS, new CustomImageGetter(tv_intro), null));
//                } else
//                {
//                    tv_intro.setText(Html.fromHtml(VerificationUtil.verifyDefault(bean.getContent(), ""), new CustomImageGetter(tv_intro), null));
//                }
                String text = VerificationUtil.verifyDefault(bean.getContent(), "");
                text = text.replaceAll("<span", "<_span");
                text = text.replaceAll("</span", "</_span");
                tv_intro.setText(Html.fromHtml(text, new CustomImageGetter(tv_intro), new CustomerTagHandler()));
//                loadHtml(tv_intro, VerificationUtil.verifyDefault(bean.getContent(), ""));
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
     * TextView加载html文本
     *
     * @param tv
     * @param html
     */
    private void loadHtml(TextView tv, String html)
    {
        if (tv == null)
        {
            return;
        }
        String text = html; // HTML text to convert
        // Preprocessing phase to set up for HTML.fromHtml(...)
        text = text.replaceAll("<span style=\"(?:color: (#[a-fA-F\\d]{6})?; )?(?:font-family: (.*?); )?(?:font-size: (.*?);)? ?\">(.*?)</span>",
                "<font color=\"$1\" face=\"$2\" size=\"$3\">$4</font>");
        text = text.replaceAll("(?<=<font color=\"#[a-fA-F0-9]{6}\" )face=\"'(.*?)', .*?\"", "face=\"$1\"");
        text = text.replaceAll("(?<=<font color=\"#[a-fA-F0-9]{6}\" )(face=\".*?\" )size=\"xx-small\"", "$1size=\"1\"");
        text = text.replaceAll("(?<=<font color=\"#[a-fA-F0-9]{6}\" )(face=\".*?\" )size=\"x-small\"", "$1size=\"2\"");
        text = text.replaceAll("(?<=<font color=\"#[a-fA-F0-9]{6}\" )(face=\".*?\" )size=\"small\"", "$1size=\"3\"");
        text = text.replaceAll("(?<=<font color=\"#[a-fA-F0-9]{6}\" )(face=\".*?\" )size=\"medium\"", "$1size=\"4\"");
        text = text.replaceAll("(?<=<font color=\"#[a-fA-F0-9]{6}\" )(face=\".*?\" )size=\"large\"", "$1size=\"5\"");
        text = text.replaceAll("(?<=<font color=\"#[a-fA-F0-9]{6}\" )(face=\".*?\" )size=\"x-large\"", "$1size=\"6\"");
        text = text.replaceAll("(?<=<font color=\"#[a-fA-F0-9]{6}\" )(face=\".*?\" )size=\"xx-large\"", "$1size=\"7\"");
        text = text.replaceAll("<strong>(.*?)</strong>", "<_em>$1</_em>");  // we use strong for bold-face
        text = text.replaceAll("<em>(.*?)</em>", "<strong>$1</strong>");    // and em for italics
        text = text.replaceAll("<_em>(.*?)</_em>", "<em>$1</em>");          // but Android uses em for bold-face
        text = text.replaceAll("<span style=\"background-color: #([a-fA-F0-9]{6}).*?>(.*?)</span>", "<_$1>$2</_$1>");
        text = text.replaceAll("null", "");
        tv.setText(Html.fromHtml(text, new CustomImageGetter(tv), new Html.TagHandler()
        {
            private List<Object> _format_stack = new LinkedList<Object>();

            @Override
            public void handleTag(boolean open_tag, String tag, Editable output, XMLReader xmlReader)
            {
                if (tag.startsWith("ul"))
                    processBullet(open_tag, output);
                else if (tag.matches(".[a-fA-F0-9]{6}"))
                    processBackgroundColor(open_tag, output, tag.substring(1));
            }

            private void processBullet(boolean open_tag, Editable output)
            {
                final int length = output.length();
                if (open_tag)
                {
                    final Object format = new BulletSpan(BulletSpan.STANDARD_GAP_WIDTH);
                    _format_stack.add(format);
                    output.setSpan(format, length, length, Spanned.SPAN_MARK_MARK);
                } else
                {
                    applySpan(output, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

            private void processBackgroundColor(boolean open_tag, Editable output, String color)
            {
                final int length = output.length();
                if (open_tag)
                {
                    final Object format = new BackgroundColorSpan(Color.parseColor('#' + color));
                    _format_stack.add(format);
                    output.setSpan(format, length, length, Spanned.SPAN_MARK_MARK);
                } else
                {
                    applySpan(output, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

            private Object getLast(Editable text, Class kind)
            {
                @SuppressWarnings("unchecked") final Object[] spans = text.getSpans(0, text.length(), kind);

                if (spans.length != 0)
                    for (int i = spans.length; i > 0; i--)
                        if (text.getSpanFlags(spans[i - 1]) == Spannable.SPAN_MARK_MARK)
                            return spans[i - 1];

                return null;
            }

            private void applySpan(Editable output, int length, int flags)
            {
                if (_format_stack.isEmpty()) return;

                final Object format = _format_stack.remove(0);
                final Object span = getLast(output, format.getClass());
                final int where = output.getSpanStart(span);

                output.removeSpan(span);

                if (where != length)
                    output.setSpan(format, where, length, flags);
            }
        }));
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
        private int width;

        public CustomImageGetter(TextView textView)
        {
            this.textView = textView;
            this.width = DisplayUtil.getScreenSize(FxApplication.getInstance()).widthPixels - DensityUtil.dip2px(FxApplication.getInstance(), 15) * 2;
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
                GlideApp.with(textView.getContext()).asBitmap().load(url).transform(new ScaleTransform(width)).into(new SimpleTarget<Bitmap>()
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

    public static class CustomerTagHandler implements Html.TagHandler
    {
        int startTag;
        int endTag;
        int fontSize;

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader)
        {
            try
            {
                if (tag.equalsIgnoreCase("_span"))
                {
                    if (opening)
                    {
                        fontSize = 13;
                        startTag = output.length();
                        String style = getProperty(xmlReader, "font-size");
                        if (!TextUtils.isEmpty(style))
                        {
                            style = style.replace("font-size", "");
                            style = style.replace(" ", "");
                            style = style.replace(":", "");
                            style = style.replace(";", "");
                            style = style.replace("px", "");
                            try
                            {
                                fontSize = Integer.parseInt(style);
                            } catch (NumberFormatException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    } else
                    {
                        endTag = output.length();
                        output.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, DensityUtil.sp2px(FxApplication.getInstance(), fontSize), null, null), startTag, endTag, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        /**
         * 利用反射获取html标签的属性值
         *
         * @param xmlReader
         * @param property
         * @return
         */
        private String getProperty(XMLReader xmlReader, String property)
        {
            try
            {
                Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
                elementField.setAccessible(true);
                Object element = elementField.get(xmlReader);
                Field attsField = element.getClass().getDeclaredField("theAtts");
                attsField.setAccessible(true);
                Object atts = attsField.get(element);
                Field dataField = atts.getClass().getDeclaredField("data");
                dataField.setAccessible(true);
                String[] data = (String[]) dataField.get(atts);
                Field lengthField = atts.getClass().getDeclaredField("length");
                lengthField.setAccessible(true);
                int len = (Integer) lengthField.get(atts);

//                for (int i = 0; i < len; i++)
//                {
//                    // 这边的property换成你自己的属性名就可以了
//                    if (property.equals(data[i * 5 + 1]))
//                    {
//                        return data[i * 5 + 4];
//                    }
//                }
                if (data != null)
                {
                    for (String item : data)
                    {
                        if (item != null && item.contains(property))
                        {
                            return item;
                        }
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
