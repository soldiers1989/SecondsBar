package com.btten.bttenlibrary.base.config;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.http.interceptor.CurrencyInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * function:网络请求的基本配置抽象类
 * author: frj
 * modify date: 2016/11/19
 */

public abstract class ABaseConfig
{
    /**
     * 获取正式版Url;url链接最后必须以“/”结尾
     *
     * @return
     */
    protected abstract String getReleaseUrl();

    /**
     * 获取测试版Url;url链接最后必须以“/”结尾
     *
     * @return
     */
    protected abstract String getDebugUrl();

    /**
     * 获取项目在SD卡根目录
     *
     * @return
     */
    protected abstract String getRootDir();

    /**
     * 获取接口路径
     *
     * @return
     */
    public String getBaseUrl()
    {
        if (BtApplication.getApplication().isDebug())
        {
            return getDebugUrl();
        } else
        {
            return getReleaseUrl();
        }
    }

    /**
     * 获取读取超时时间
     *
     * @return 单位为秒
     */
    public int getReadTimeoutSec()
    {
        return 10;
    }


    /**
     * 获取写入超时时间
     *
     * @return 单位为秒
     */
    public int getWriteTimeoutSec()
    {
        return 10;
    }

    /**
     * 获取连接超时时间
     *
     * @return 单位为秒
     */
    public int getConnectTimeoutSec()
    {
        return 10;
    }

    /**
     * 获取拦截器集合
     *
     * @return
     */
    public List<Interceptor> getInterceptors()
    {
        List<Interceptor> interceptors = new ArrayList<>();
//        interceptors.add(new CurrencyInterceptor(BtApplication.getApplication().getApplicationContext()));
        if (BtApplication.getApplication().isDebug())
        {
            interceptors.add(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return interceptors;
    }

    /**
     * 表示是否需要在请求失败后重试
     *
     * @return true 表示在请求失败后重试；false表示在请求失败后直接返回
     */
    public boolean isRetryOnConnectionFail()
    {
        return true;
    }

    /**
     * 获取缓存配置
     *
     * @return
     */
    public Cache getCache()
    {
        return new Cache(new File(BtApplication.getApplication().getApplicationContext().getExternalCacheDir(), "responses"), 10 * 1024 * 1024);
    }

    /**
     * 获取基础的根目录路径
     *
     * @return
     */
    public String getBaseRootDir()
    {
        return getRootDir();
    }

}
