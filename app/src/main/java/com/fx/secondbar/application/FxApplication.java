package com.fx.secondbar.application;

import android.support.annotation.StringRes;
import android.util.Log;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.BuildConfig;
import com.fx.secondbar.bean.ResConfigInfo;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.config.BaseConfig;
import com.fx.secondbar.util.DataCacheUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;

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
            mUserInfoBean = new UserInfoBean();
        }
        this.mUserInfoBean = mUserInfoBean;
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
            mConfigInfo = new ResConfigInfo();
        }
        this.mConfigInfo = mConfigInfo;
        DataCacheUtils.saveListCache(this, mConfigInfo, DataCacheUtils.FILE_CONFIG_INFO);
    }
}
