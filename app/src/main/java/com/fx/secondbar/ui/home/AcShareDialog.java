package com.fx.secondbar.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DateUtils;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.LogUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.GlideLoad;
import com.fx.secondbar.util.ShareUtils;
import com.joooonho.SelectableRoundedImageView;
import com.king.zxing.util.CodeUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * function:Dialog样式分享对话框
 * author: frj
 * modify date: 2018/9/27
 */
public class AcShareDialog extends ActivitySupport implements WbShareCallback
{

    private static final int PLATFORM_QQ = 1;
    private static final int PLATFORM_WEIBO = 2;
    private static final int PLATFORM_WECHAT = 3;
    private static final int PLATFORM_TIMELINE = 4;

    /**
     * 当前分享类型-图片海报，内容
     */
    public static final int TYPE_POSTER_CONTENT = 1;
    /**
     * 当前分享类型-图片海报，申购
     */
    public static final int TYPE_POST_PURCHASE = 2;
    /**
     * 当前分享类型-图片海报，商品
     */
    public static final int TYPE_POSTER_GOODS = 3;
    /**
     * 当前分享类型-图片海报，邀请海报
     */
    public static final int TYPE_POSTER_INVITE = 4;
    /**
     * 当前分享类型-文本
     */
    public static final int TYPE_TEXT = 5;

    /**
     * 类型的key值
     */
    public static final String KEY_TYPE = "key_type";
    /**
     * 图片的Key
     */
    public static final String KEY_IMG = "key_img";
    /**
     * 标题的Key
     */
    public static final String KEY_TITLE = "key_title";
    /**
     * 内容的Key
     */
    public static final String KEY_CONTENT = "key_content";
    /**
     * Url的Key
     */
    public static final String KEY_URL = "key_url";

    private Tencent mTencent;   //腾讯
    private IWXAPI mApi;        //微信Api对象
    private WbShareHandler wbShareHandler;  //微博分享对象

    //分享类型
    private int type;

    private TextView tv_time;               //分享时间
    private LinearLayout ll_content;

    private TextView tv_content;

    private ConstraintLayout cl_commodity;
    private ImageView img_commodity;        //商品图片
    private TextView tv_commodity_title;    //商品标题
    private TextView tv_commodity_price;    //商品价格
    private TextView tv_timelength;         //商品时长
    private TextView tv_place;              //商品地点
    private ImageView img_code;             //二维码

    private ConstraintLayout cl_purchase;
    private SelectableRoundedImageView img_avatar;  //名人头像
    private TextView tv_person_name;        //名人姓名
    private TextView tv_person_price;       //名人价格

    private ConstraintLayout cl_poster_share;
    private ImageView img_poster_share_code;    //二维码


    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_share_dialog;
    }

    @Override
    protected void initView()
    {
        ImageButton img_qq = findViewById(R.id.img_qq);
        ImageButton img_weibo = findViewById(R.id.img_weibo);
        ImageButton img_cycle = findViewById(R.id.img_cycle);
        ImageButton img_wechat = findViewById(R.id.img_wechat);
        tv_time = findView(R.id.tv_time);
        tv_content = findView(R.id.tv_content);
        ll_content = findView(R.id.ll_content);
        img_code = findView(R.id.img_code);

        cl_commodity = findView(R.id.cl_commodity);
        img_commodity = findView(R.id.img_commodity);
        tv_commodity_title = findView(R.id.tv_commodity_title);
        tv_commodity_price = findView(R.id.tv_commodity_price);
        tv_timelength = findView(R.id.tv_timelength);
        tv_place = findView(R.id.tv_place);

        cl_purchase = findView(R.id.cl_purchase);
        img_avatar = findView(R.id.img_avatar);
        tv_person_name = findView(R.id.tv_person_name);
        tv_person_price = findView(R.id.tv_person_price);

        cl_poster_share = findView(R.id.cl_poster_share);
        img_poster_share_code = findView(R.id.img_poster_share_code);

        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isFastDoubleClick(v))
                {
                    return;
                }
                Bitmap bitmap = null;
                if (TYPE_POSTER_INVITE == type)
                {
                    bitmap = cl_poster_share.getDrawingCache();
                } else
                {
                    bitmap = ll_content.getDrawingCache();
                }
                String path = saveBitmapToFile(bitmap);
                if (R.id.img_qq == v.getId())
                {
                    shareToQQ(AcShareDialog.this, ShareUtils.buildQQShareContent(path));
                } else if (R.id.img_weibo == v.getId())
                {
                    shareToWeibo(AcShareDialog.this, ShareUtils.buildWeiboShareContent(bitmap));
                } else if (R.id.img_cycle == v.getId())
                {
                    shareToWechatFriends(ShareUtils.buildWXShareContent(bitmap));
                } else if (R.id.img_wechat == v.getId())
                {
                    shareToWechat(ShareUtils.buildWXShareContent(bitmap));
                }


            }
        };
        img_qq.setOnClickListener(onClickListener);
        img_weibo.setOnClickListener(onClickListener);
        img_cycle.setOnClickListener(onClickListener);
        img_wechat.setOnClickListener(onClickListener);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        mTencent = Tencent.createInstance(ShareUtils.TENCENT_APPID, FxApplication.getInstance());
        //第三个参数Boolean表示是否校验签名，true表示校验，false反之。如果是测试版，那么不校验
        mApi = WXAPIFactory.createWXAPI(FxApplication.getInstance(), ShareUtils.WECHAT_APPID, !FxApplication.getInstance().isDebug());
        mApi.registerApp(ShareUtils.WECHAT_APPID);
        //微博分享对象构建
        wbShareHandler = new WbShareHandler(this);
        wbShareHandler.registerApp();


        //分享类型
        type = getIntent().getIntExtra(KEY_TYPE, TYPE_POSTER_CONTENT);
        String content = getIntent().getStringExtra(KEY_CONTENT);
        if (TYPE_POSTER_INVITE != type)
        {
            img_code.setImageBitmap(CodeUtils.createQRCode(String.format(Constants.URL_SHARE, FxApplication.getInstance().getUserInfoBean().getMemberid(), String.valueOf(Math.random())), DensityUtil.dip2px(this, 120), BitmapFactory.decodeResource(getResources(), R.mipmap.icons)));
            tv_time.setText(DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm"));

            ll_content.setVisibility(View.VISIBLE);
            ll_content.setDrawingCacheEnabled(true);
            ll_content.buildDrawingCache();

            if (TYPE_POSTER_CONTENT == type)
            {
                tv_content.setVisibility(View.VISIBLE);
                tv_content.setText(Html.fromHtml(VerificationUtil.verifyDefault(content, "")));
            } else if (TYPE_POSTER_GOODS == type)
            {
                cl_commodity.setVisibility(View.VISIBLE);
                String[] values = content.split("==");
                //索引顺序分别为：标题、时长、地点和价格
                if (values != null && values.length == 4)
                {
                    VerificationUtil.setViewValue(tv_commodity_title, values[0]);
                    VerificationUtil.setViewValue(tv_timelength, values[1]);
                    VerificationUtil.setViewValue(tv_place, values[2]);
                    VerificationUtil.setViewValue(tv_commodity_price, values[3]);
                }
                GlideLoad.load(img_commodity, getIntent().getStringExtra(KEY_IMG));
            } else if (TYPE_POST_PURCHASE == type)
            {
                cl_purchase.setVisibility(View.VISIBLE);
                String[] values = content.split("==");
                //索引顺序分别为：姓名和价格
                if (values != null && values.length == 2)
                {
                    VerificationUtil.setViewValue(tv_person_name, values[0]);
                    VerificationUtil.setViewValue(tv_person_price, values[1]);
                }
                GlideLoad.load(img_avatar, getIntent().getStringExtra(KEY_IMG), true);
            }
        } else
        {
            ll_content.setVisibility(View.GONE);
            cl_poster_share.setVisibility(View.VISIBLE);
            cl_poster_share.setDrawingCacheEnabled(true);
            cl_poster_share.buildDrawingCache();
            img_poster_share_code.setImageBitmap(CodeUtils.createQRCode(String.format(Constants.URL_SHARE, FxApplication.getInstance().getUserInfoBean().getMemberid(), String.valueOf(Math.random())), DensityUtil.dip2px(this, 100), BitmapFactory.decodeResource(getResources(), R.mipmap.icons)));
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

    /**
     * 分享到QQ
     *
     * @param activity
     * @param shareContent 分享跳转的链接
     */
    public void shareToQQ(Activity activity, Bundle shareContent)
    {
        if (activity != null)
        {
            if (mTencent.isQQInstalled(activity))
            {
                if (shareContent == null)
                {
                    shareContent = ShareUtils.buildQQShareContent("", "", "");
                }
                mTencent.shareToQQ(activity, shareContent, new QQShareListener());
            } else
            {
                ShowToast.showToast(activity, "您没有安装QQ客户端");
            }
        }
    }

    /**
     * 分享到微博
     *
     * @param activity
     * @param msg      微博分享内容
     */
    public void shareToWeibo(Activity activity, WeiboMultiMessage msg)
    {
        if (activity != null)
        {
            if (WbSdk.isWbInstall(activity))
            {
                if (msg == null)
                {
                    msg = ShareUtils.buildWeiboShareContent("", "", "");
                }
                wbShareHandler.shareMessage(msg, false);
            } else
            {
                ShowToast.showToast(activity, "您没有安装微博客户端");
            }
        }
    }

    /**
     * 分享给微信好友
     *
     * @param msg
     */
    public void shareToWechat(WXMediaMessage msg)
    {
        if (mApi.isWXAppInstalled())
        {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            //表示分享至微信好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
            mApi.sendReq(req);
        } else
        {
            ShowToast.showToast(FxApplication.getInstance(), "您没有安装微信客户端");
        }
    }

    /**
     * 分享至微信朋友圈
     *
     * @param msg
     */
    public void shareToWechatFriends(WXMediaMessage msg)
    {
        if (mApi.isWXAppInstalled())
        {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            //表示分享至朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
            mApi.sendReq(req);
        } else
        {
            ShowToast.showToast("您没有安装微信客户端");
        }
    }

    /**
     * 保存图片至文件
     *
     * @param bitmap
     */
    private String saveBitmapToFile(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        String path = "";
        try
        {
            File folder = new File(Environment.getExternalStorageDirectory(), Constants.ROOT_DIR);
            if (!folder.exists())
            {
                folder.mkdirs();
            }
            File file = new File(folder, System.currentTimeMillis() + ".jpg");
            path = file.getAbsolutePath();
            if (!file.exists()) // 如果文件不存在，那么创建文件
            {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e)
        {
            e.printStackTrace();

        }
        return path;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new QQShareListener());
        if (wbShareHandler != null)
        {
            wbShareHandler.doResultIntent(data, this);
        }
    }

    @Override
    public void onWbShareSuccess()
    {
        LogUtil.e("wb share", "share success");
        FxApplication.shareSuccessBroadCast();
        ShowToast.showToast("分享成功");
    }

    @Override
    public void onWbShareCancel()
    {
        LogUtil.e("wb share", "share cancel");
    }

    @Override
    public void onWbShareFail()
    {
        LogUtil.e("wb share", "share fail");
    }

    /**
     * QQ分享监听
     */
    public static class QQShareListener implements IUiListener
    {

        @Override
        public void onComplete(Object o)
        {
            LogUtil.e("tencent share", "share success");
            FxApplication.shareSuccessBroadCast();
            ShowToast.showToast("分享成功");
        }

        @Override
        public void onError(UiError uiError)
        {
            LogUtil.e("tencent share", "share error. message:" + uiError.errorMessage);
        }

        @Override
        public void onCancel()
        {
            LogUtil.e("tencent share", "share cancel");
        }
    }

}
