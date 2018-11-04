package com.fx.secondbar.http.interceptor;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.fx.secondbar.util.DeviceUuidFactory;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * function:通用的请求拦截器（在拦截器中可以添加通用的param）；该拦截器实现了通用的Param和缓存策略
 * author: frj
 * modify date: 2016/11/18
 */

public class CurrencyInterceptor implements Interceptor
{
    /**
     * 平台序号参数
     */
    public static final String OSTYPE_PARAM = "ostype";

    /**
     * 版本号参数
     */
    public static final String VERSION_PARAM = "version";

    /**
     * IEMI参数
     */
    public static final String IMEI_PARAM = "deviceid";

    /**
     * Android平台的序号
     */
    public static final String ANDROID_OSTYPE = "1";

    /**
     * App版本号
     */
    public static String VERSION_CODE = "";

    /**
     * 手机IMEI值
     */
//    public static String imei = "be16ecde-8f79-3a50-a5ad-21d55cea500f";
//    public static String imei = "f086d433-087a-30c8-af65-e172d0b2c7e0";
    public static String imei = "";

    private Context context;

    public CurrencyInterceptor(Context context)
    {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        HttpUrl httpUrl = addParameter(request.url().newBuilder())
                .build();
//        /*
//            判断是否有网络，如果无网络，那么从缓存中读取数据；如果有网络那就正常加载
//         */
//        if (!isNetworkReachable(context))
//        {
//            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).url(httpUrl).build();
//        } else
//        {
//            request = request.newBuilder().url(httpUrl).build();
//        }
        request = request.newBuilder().url(httpUrl).build();
        Response response = chain.proceed(request);
//        /*
//            先判断网络，网络好的时候，移除header后添加cache失效时间为1小时，网络未连接的情况下设置缓存时间为4周
//         */
//        if (isNetworkReachable(context))
//        {
//            int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
//            response.newBuilder()
//                    .removeHeader("Pragma")
//                    //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                    .header("Cache-Control", "public, max-age=" + maxAge)//设置缓存超时时间
//                    .build();
//        } else
//        {
//            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
//            response.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    //设置缓存策略，及超时策略
//                    .build();
//        }
        return response;
    }

    /**
     * 添加通用参数
     *
     * @param builder
     * @return
     */
    protected HttpUrl.Builder addParameter(HttpUrl.Builder builder)
    {
        builder.addQueryParameter(OSTYPE_PARAM, ANDROID_OSTYPE);
        if (TextUtils.isEmpty(VERSION_CODE))
        {
            VERSION_CODE = String.valueOf(getVersion(context));
        }
        builder.addQueryParameter(VERSION_PARAM, VERSION_CODE);
        if (TextUtils.isEmpty(imei))
        {
            imei = getImei(context);
        }
        builder.addQueryParameter(IMEI_PARAM, imei);
        return builder;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     * @throws PackageManager.NameNotFoundException
     */
    static int getVersion(Context context)
    {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try
        {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取手机IMEI号
     *
     * @return 当前手机IMEI号
     */
    static String getImei(Context context)
    {
        imei = new DeviceUuidFactory(context).getDeviceUuid().toString();
        return imei;
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     */
    static Boolean isNetworkReachable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null)
        {
            return false;
        }
        return (current.isAvailable());
    }

}
