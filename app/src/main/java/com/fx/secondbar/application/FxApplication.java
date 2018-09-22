package com.fx.secondbar.application;

import android.support.annotation.StringRes;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.BuildConfig;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.config.BaseConfig;
import com.fx.secondbar.util.DataCacheUtils;

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

    @Override
    public void onCreate()
    {
        mApplication = this;
        super.onCreate();
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
}
