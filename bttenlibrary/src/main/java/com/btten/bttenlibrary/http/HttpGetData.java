package com.btten.bttenlibrary.http;


import com.btten.bttenlibrary.base.config.ABaseConfig;
import com.btten.bttenlibrary.util.VerificationUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * function:Http网络请求库初始化
 * author: frj
 * modify date: 2016/11/18
 */

public class HttpGetData
{
    private static HttpGetData mHttpManager;

    private Retrofit mRetrofit;

    private HttpGetData()
    {
    }

    /**
     * 初始化网络初始配置
     *
     * @param config 网络请求基础配置对象，应是实现ABaseConfig抽象类的实体
     */
    private void init(ABaseConfig config)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //添加通用的拦截器，用来添加默认参数
        List<Interceptor> interceptors = config.getInterceptors();
        if (VerificationUtil.noEmpty(interceptors))
        {
            for (Interceptor interceptor : interceptors)
            {
                if (interceptor != null)
                {
                    builder.addInterceptor(interceptor);
                }
            }
        }
        //连接超时时间
        int connectTimeout = config.getConnectTimeoutSec();
        if (connectTimeout != 0)
        {
            builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        }
        //读取超时时间
        int readTimeout = config.getReadTimeoutSec();
        if (readTimeout != 0)
        {
            builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        }
        //写入超时时间
        int writeTimeout = config.getWriteTimeoutSec();
        if (writeTimeout != 0)
        {
            builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        }
        //设置出现错误进行重新连接
        builder.retryOnConnectionFailure(config.isRetryOnConnectionFail());
        //设置缓存目录和缓存大小
        Cache cache = config.getCache();
        if (cache != null)
        {
            builder.cache(cache);
        }
        OkHttpClient client = builder.build();

        mRetrofit = new Retrofit.Builder().baseUrl(config.getBaseUrl())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //自定义OkHttpClient
                .client(client)
                .build();
    }

    /**
     * 获取单例的HttpGetData对象
     *
     * @return HttpGetData对象，该方法只在当前类中被调用
     */
    private static HttpGetData getInstance()
    {
        if (mHttpManager == null)
        {
            mHttpManager = new HttpGetData();
        }
        return mHttpManager;
    }

    /**
     * 初始化网络请求的基本配置
     *
     * @param config 网络请求基础配置对象，应是实现ABaseConfig抽象类的实体
     */
    public static void initConfig(ABaseConfig config)
    {
        if (config == null)
        {
            throw new NullPointerException("The ABaseConfig object cannot be null.");
        }
        getInstance().init(config);
    }

    /**
     * 获取Retrofit对象
     *
     * @return 返回自定义配置的Retrofit对象
     */
    public static Retrofit getRetrofit()
    {
        if (getInstance().mRetrofit == null)
        {
            throw new IllegalStateException("You should call the initConfig method before calling this method.");
        }
        return getInstance().mRetrofit;
    }
}
