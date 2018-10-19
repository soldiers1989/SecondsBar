package com.fx.secondbar.application;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.util.LogUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.BuildConfig;
import com.fx.secondbar.bean.ResConfigInfo;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.config.BaseConfig;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.DataCacheUtils;
import com.fx.secondbar.util.ShareUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * function:
 * author: frj
 * modify date: 2018/9/7
 */
public class FxApplication extends BtApplication
{

    private static FxApplication mApplication;

    private UserInfoBean mUserInfoBean;

    private ResConfigInfo mConfigInfo;

    /**
     * 收益进度值
     */
    private int incomeProgress;

    @Override
    public void onCreate()
    {
        mApplication = this;
        super.onCreate();

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback()
        {
            @Override
            public void onViewInitFinished(boolean arg0)
            {
                // TODO Auto-generated method stub
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished()
            {
                // TODO Auto-generated method stub
            }
        };    //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        //初始化微博sdk
        WbSdk.install(this, new AuthInfo(this, ShareUtils.SINA_APPKEY, ShareUtils.REDIRECT_URL, ShareUtils.SCOPE));
        JPushInterface.init(this);
    }

    @Override
    protected void setBaseConfig()
    {
        mAbaseConfig = new BaseConfig();
    }

    @Override
    protected void setDebug()
    {
        isDebug = BuildConfig.DEBUG;
    }

    /**
     * 返回当前的Application对象
     *
     * @return 当前的Application对象
     */
    public static FxApplication getInstance()
    {
        return mApplication;
    }

    /**
     * 根据id获取资源值
     *
     * @param res
     * @return
     */
    public static String getStr(@StringRes int res)
    {
        return getInstance().getResources().getString(res);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfoBean getUserInfoBean()
    {
        if (mUserInfoBean == null)
        {
            ArrayList<UserInfoBean> list = DataCacheUtils.loadListCache(this, DataCacheUtils.FILE_USERINFO);
            if (VerificationUtil.noEmpty(list))
            {
                mUserInfoBean = list.get(0);
            } else
            {
                mUserInfoBean = new UserInfoBean();
            }
        }
        return mUserInfoBean;
    }

    /**
     * 设置用户信息
     *
     * @param mUserInfoBean
     */
    public void setUserInfoBean(UserInfoBean mUserInfoBean)
    {
        if (mUserInfoBean == null)
        {
            this.mUserInfoBean = new UserInfoBean();
        } else
        {
            this.mUserInfoBean = mUserInfoBean.clone();
        }
        DataCacheUtils.saveListCache(this, mUserInfoBean, DataCacheUtils.FILE_USERINFO);
    }

    /**
     * 获取配置信息
     *
     * @return
     */
    public ResConfigInfo getConfigInfo()
    {
        if (mConfigInfo == null)
        {
            ArrayList<ResConfigInfo> list = DataCacheUtils.loadListCache(this, DataCacheUtils.FILE_CONFIG_INFO);
            if (VerificationUtil.noEmpty(list))
            {
                mConfigInfo = list.get(0);
            } else
            {
                mConfigInfo = new ResConfigInfo();
            }
        }
        return mConfigInfo;
    }

    /**
     * 设置配置信息
     *
     * @param mConfigInfo
     */
    public void setConfigInfo(ResConfigInfo mConfigInfo)
    {
        if (mConfigInfo == null)
        {
            this.mConfigInfo = new ResConfigInfo();
        } else
        {
            this.mConfigInfo = mConfigInfo.clone();
        }
        DataCacheUtils.saveListCache(this, mConfigInfo, DataCacheUtils.FILE_CONFIG_INFO);
    }

    public int getIncomeProgress()
    {
        return incomeProgress;
    }

    public void setIncomeProgress(int incomeProgress)
    {
        this.incomeProgress = incomeProgress;
    }

    /**
     * 发送刷新用户信息广播
     */
    public static void refreshUserInfoBroadCast()
    {
        boolean isSendSuccess = LocalBroadcastManager.getInstance(getInstance()).sendBroadcast(new Intent(Constants.ACTION_REFRESH_USERINFO));
        LogUtil.e("refreshUserInfoBroadCast", isSendSuccess ? "发送成功" : "发送失败");
    }

    /**
     * 发送刷新个人中心显示广播
     */
    public static void refreshPersonShowBroadCast()
    {
        boolean isSendSuccess = LocalBroadcastManager.getInstance(getInstance()).sendBroadcast(new Intent(Constants.ACTION_REFRESH_PERSON_SHOW));
        LogUtil.e("refreshPersonShowBroadCast", isSendSuccess ? "发送成功" : "发送失败");
    }

    /**
     * 发送分享成功广播
     */
    public static void shareSuccessBroadCast()
    {
        boolean isSendSuccess = LocalBroadcastManager.getInstance(getInstance()).sendBroadcast(new Intent(Constants.ACTION_SHARE_SUCCESS));
        LogUtil.e("refreshPersonShowBroadCast", isSendSuccess ? "发送成功" : "发送失败");
    }

    /**
     * 发送刷新通知提示广播
     */
    public static void refreshNotifyTipsBroadCast()
    {
        boolean isSendSuccess = LocalBroadcastManager.getInstance(getInstance()).sendBroadcast(new Intent(Constants.ACTION_REFRESH_NOTIFY_TIPS));
        LogUtil.e("refreshPersonShowBroadCast", isSendSuccess ? "发送成功" : "发送失败");
    }
}
