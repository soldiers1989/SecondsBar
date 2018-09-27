package com.fx.secondbar.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.LogUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.util.ShareUtils;
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

/**
 * function:分享的Dialog
 * author: frj
 * modify date: 2018/9/27
 */
public class FragmentShareDialog extends DialogFragment implements WbShareCallback
{
    /**
     * 图片的Key
     */
    private static final String KEY_IMG = "key_img";
    /**
     * 标题的Key
     */
    private static final String KEY_TITLE = "key_title";
    /**
     * 内容的Key
     */
    private static final String KEY_CONTENT = "key_content";
    /**
     * Url的Key
     */
    private static final String KEY_URL = "key_url";

    /**
     * 当前分享类型-图片
     */
    private static final int TYPE_IMG = 1;
    /**
     * 当前分享类型-文本
     */
    private static final int TYPE_TEXT = 2;

    private Tencent mTencent;   //腾讯
    private IWXAPI mApi;        //微信Api对象
    private WbShareHandler wbShareHandler;  //微博分享对象

    //分享类型
    private int type;

    public static FragmentShareDialog newInstance(Bitmap bitmap)
    {
        FragmentShareDialog fragment = new FragmentShareDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_IMG, bitmap);
        fragment.setArguments(bundle);
        fragment.type = TYPE_IMG;
        return fragment;
    }

    public static FragmentShareDialog newInstance(String title, String content, String url)
    {
        FragmentShareDialog fragment = new FragmentShareDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_CONTENT, content);
        fragment.setArguments(bundle);
        fragment.type = TYPE_TEXT;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.dialog_share, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mTencent = Tencent.createInstance(ShareUtils.TENCENT_APPID, FxApplication.getInstance());
        //第三个参数Boolean表示是否校验签名，true表示校验，false反之。如果是测试版，那么不校验
        mApi = WXAPIFactory.createWXAPI(FxApplication.getInstance(), ShareUtils.WECHAT_APPID, !FxApplication.getInstance().isDebug());
        mApi.registerApp(ShareUtils.WECHAT_APPID);
        //微博分享对象构建
        wbShareHandler = new WbShareHandler(getActivity());
        wbShareHandler.registerApp();

        ImageButton img_qq = getView().findViewById(R.id.img_qq);
        ImageButton img_weibo = getView().findViewById(R.id.img_weibo);
        ImageButton img_cycle = getView().findViewById(R.id.img_cycle);
        ImageButton img_wechat = getView().findViewById(R.id.img_wechat);

        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (R.id.img_qq == v.getId())
                {
                    shareToQQ(getActivity(), ShareUtils.buildQQShareContent(getArguments().getString(KEY_TITLE), getArguments().getString(KEY_CONTENT), getArguments().getString(KEY_URL)));
                } else if (R.id.img_weibo == v.getId())
                {
                    shareToWeibo(getActivity(), ShareUtils.buildWeiboShareContent(getArguments().getString(KEY_TITLE), getArguments().getString(KEY_CONTENT), getArguments().getString(KEY_URL)));
                } else if (R.id.img_cycle == v.getId())
                {
                    shareToWechatFriends(ShareUtils.buildWXShareContent(getArguments().getString(KEY_TITLE), getArguments().getString(KEY_CONTENT), getArguments().getString(KEY_URL)));
                } else if (R.id.img_wechat == v.getId())
                {
                    shareToWechat(ShareUtils.buildWXShareContent(getArguments().getString(KEY_TITLE), getArguments().getString(KEY_CONTENT), getArguments().getString(KEY_URL)));
                }
            }
        };
        img_qq.setOnClickListener(onClickListener);
        img_weibo.setOnClickListener(onClickListener);
        img_cycle.setOnClickListener(onClickListener);
        img_wechat.setOnClickListener(onClickListener);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DisplayUtil.getRealScreenSize(getContext()).widthPixels;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(R.style.dialog_share);
        return dialog;
    }

    @Override
    public int getTheme()
    {
        return R.style.dialog_share;
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
