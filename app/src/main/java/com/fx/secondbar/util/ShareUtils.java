package com.fx.secondbar.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;

import com.btten.bttenlibrary.util.BitmapUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * function:分享工具类
 * author: frj
 * modify date: 2018/5/9
 */
public class ShareUtils
{
    /**
     * 腾讯开放平台AppId
     */
    public static final String TENCENT_APPID = "1107845568";

    /**
     * 微信开放平台AppId
     */
    public static final String WECHAT_APPID = "wxa60ff63820e06c8b";

    /**
     * 新浪开放平台AppId
     */
    public static final String SINA_APPKEY = "2628714932";

    /**
     * 默认回调页
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * 新浪微博权限Scope
     */
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    /**
     * 分享链接
     */
    private static final String SHAREURL = "html/getDetail.html?id=%s&share=1";

    /**
     * 图标网络访问链接
     */
    private static final String URL_ICON = "http://zixun.hekonggroup.com/AdvisoryManager/static_resources/img/icons.png";

    /**
     * 当前类静态对象
     */
    private static ShareUtils mUtils;

    private Tencent mTencent;   //腾讯
    private IWXAPI mApi;        //微信Api对象

    private ShareUtils()
    {
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        mTencent = Tencent.createInstance(TENCENT_APPID, FxApplication.getInstance());
        //第三个参数Boolean表示是否校验签名，true表示校验，false反之。如果是测试版，那么不校验
        mApi = WXAPIFactory.createWXAPI(FxApplication.getInstance(), WECHAT_APPID, !FxApplication.getInstance().isDebug());
    }

    private static ShareUtils getInstance()
    {
        if (mUtils == null)
        {
            synchronized (ShareUtils.class)
            {
                if (mUtils == null)
                {
                    mUtils = new ShareUtils();
                }
            }
        }
        return mUtils;
    }

    /**
     * 获取分享链接
     *
     * @return
     */
    private static String getShareUrl()
    {
        return Constants.ROOT_URL + SHAREURL;
    }

    /**
     * 分享到QQ
     *
     * @param activity
     * @param shareContent 分享跳转的链接
     * @param listener     分享回调结果
     */
    public static void shareToQQ(Activity activity, Bundle shareContent, IUiListener listener)
    {
        if (activity != null)
        {
            if (getInstance().mTencent.isQQInstalled(activity))
            {

                if (listener == null)
                {
                    listener = new IUiListener()
                    {
                        @Override
                        public void onComplete(Object o)
                        {
                        }

                        @Override
                        public void onError(UiError uiError)
                        {
                        }

                        @Override
                        public void onCancel()
                        {
                        }
                    };
                }
                if (shareContent == null)
                {
                    shareContent = buildQQShareContent("", "", "");
                }
                getInstance().mTencent.shareToQQ(activity, shareContent, listener);
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
    public static void shareToWeibo(Activity activity, WeiboMultiMessage msg)
    {
        if (activity != null)
        {
            if (WbSdk.isWbInstall(activity))
            {
                WbShareHandler wbShareHandler = new WbShareHandler(activity);
                wbShareHandler.registerApp();
                if (msg == null)
                {
                    msg = buildWeiboShareContent("", "", "");
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
    public static void shareToWechat(WXMediaMessage msg)
    {
        if (getInstance().mApi.isWXAppInstalled())
        {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            //表示分享至微信好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
            getInstance().mApi.sendReq(req);
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
    public static void shareToWechatFriends(WXMediaMessage msg)
    {
        if (getInstance().mApi.isWXAppInstalled())
        {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            //表示分享至朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
            getInstance().mApi.sendReq(req);
        } else
        {
            ShowToast.showToast(FxApplication.getInstance(), "您没有安装微信客户端");
        }
    }

    /**
     * 构建QQ分享内容
     *
     * @param title 标题
     * @param intro 简介
     * @param id    链接对应id
     * @return
     */
    public static Bundle buildQQShareContent(String title, String intro, String id)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, TextUtils.isEmpty(title) ? FxApplication.getInstance().getString(R.string.app_name) : title);
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, String.format(getShareUrl(), id));
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, VerificationUtil.verifyDefault(intro, ""));
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, URL_ICON);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, FxApplication.getInstance().getString(R.string.app_name) + TENCENT_APPID);
        return bundle;
    }

    /**
     * 构建QQ分享内容（纯图片）
     *
     * @param path 图片本地路径
     * @return
     */
    public static Bundle buildQQShareContent(String path)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, FxApplication.getInstance().getString(R.string.app_name) + TENCENT_APPID);
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
        return bundle;
    }

    /**
     * 构建微博分享内容
     *
     * @param title 标题
     * @param intro 简介
     * @param id    链接对应id
     * @return
     */
    public static WeiboMultiMessage buildWeiboShareContent(String title, String intro, String id)
    {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append("\n");
        sb.append(intro);
        sb.append(String.format(getShareUrl(), id));
        textObject.text = sb.toString();
        textObject.title = title;
        textObject.actionUrl = String.format(getShareUrl(), id);
        weiboMessage.textObject = textObject;
        return weiboMessage;
    }

    /**
     * 构建微博分享（纯图片）
     *
     * @param bitmap
     * @return
     */
    public static WeiboMultiMessage buildWeiboShareContent(Bitmap bitmap)
    {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        ImageObject imgObj = new ImageObject();
        imgObj.setImageObject(bitmap);
        bitmap.recycle();
        weiboMessage.imageObject = imgObj;
        return weiboMessage;
    }

    /**
     * 构建微信分享内容
     *
     * @param title 标题
     * @param intro 简介
     * @param id    链接对应id
     * @return
     */
    public static WXMediaMessage buildWXShareContent(String title, String intro, String id)
    {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = String.format(getShareUrl(), id);
        Bitmap bmp = BitmapFactory.decodeResource(FxApplication.getInstance().getResources(), R.mipmap.icons);
        byte[] bytes = null;
        if (bmp != null)
        {
            bytes = BitmapUtil.Bitmap2Bytes(bmp);
        }
        bmp.recycle();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = intro;
        if (bytes != null)
        {
            msg.thumbData = bytes;
        }
        return msg;
    }

    /**
     * 构建微信分享内容
     *
     * @param bitmap 图片
     * @return
     */
    public static WXMediaMessage buildWXShareContent(Bitmap bitmap)
    {
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        bitmap.recycle();
        msg.mediaObject = imgObj;
        msg.thumbData = BitmapUtil.Bitmap2Bytes(thumbBitmap);
        thumbBitmap.recycle();
        return msg;
    }
}
