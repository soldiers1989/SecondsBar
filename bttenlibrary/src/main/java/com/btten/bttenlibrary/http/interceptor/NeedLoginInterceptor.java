package com.btten.bttenlibrary.http.interceptor;


import android.content.Context;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * function:需要登录信息的拦截器
 * author: frj
 * modify date: 2016/11/18
 */
public class NeedLoginInterceptor extends CurrencyInterceptor implements Interceptor
{

    private String uid; //用户Id
    private String token; //用户Token

    public NeedLoginInterceptor(Context context, String uid, String token)
    {
        super(context);
        this.uid = uid;
        this.token = token;
    }

    @Override
    protected HttpUrl.Builder addParameter(HttpUrl.Builder builder)
    {
        builder.addQueryParameter("uid", uid);
        builder.addQueryParameter("token", token);
        return super.addParameter(builder);
    }
}
