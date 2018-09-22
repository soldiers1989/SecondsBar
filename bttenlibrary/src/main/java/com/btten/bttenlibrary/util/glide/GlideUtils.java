package com.btten.bttenlibrary.util.glide;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.btten.bttenlibrary.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * function:Glide工具类
 * author: frj
 * modify date: 2017/6/8
 */

public class GlideUtils
{

    /**
     * 获取当前默认的配置
     *
     * @return
     */
    public static RequestOptions getDefaultOptions()
    {
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ic_default_adimage).error(R.drawable.ic_default_adimage);
        return options;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url     http头地址
     * @param into    需要加载图片的View
     */
    public static void load(Context context, String url, ImageView into)
    {
        load(context, url, into, getDefaultOptions());
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url     http头地址
     * @param into    需要加载图片的View
     */
    public static void load(Context context, String url, ImageView into, RequestOptions requestOptions)
    {
        Glide.with(context).load(url).apply(requestOptions).into(into);
    }

    /**
     * Fragment中的加载图片
     *
     * @param fragment
     * @param url      http头地址
     * @param into     需要加载的View
     */
    public static void load(Fragment fragment, String url, ImageView into)
    {
        load(fragment, url, into, getDefaultOptions());
    }

    /**
     * Fragment中的加载图片
     *
     * @param fragment
     * @param url      http头地址
     * @param into     需要加载的View
     */
    public static void load(Fragment fragment, String url, ImageView into, RequestOptions requestOptions)
    {
        Glide.with(fragment).load(url).apply(requestOptions).transition(withCrossFade()).into(into);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param file    图片文件
     * @param into    需要加载图片的View
     */
    public static void load(Context context, File file, ImageView into)
    {
        load(context, file, into, getDefaultOptions());
    }

    /**
     * 加载图片
     *
     * @param context
     * @param file    图片文件
     * @param into    需要加载图片的View
     */
    public static void load(Context context, File file, ImageView into, RequestOptions requestOptions)
    {
        Glide.with(context).load(file).apply(requestOptions).transition(withCrossFade()).into(into);
    }

    /**
     * Fragment中的加载图片
     *
     * @param fragment
     * @param file     图片文件
     * @param into     需要加载图片的View
     */
    public static void load(Fragment fragment, File file, ImageView into)
    {
        load(fragment, file, into, getDefaultOptions());
    }

    /**
     * Fragment中的加载图片
     *
     * @param fragment
     * @param file     图片文件
     * @param into     需要加载图片的View
     */
    public static void load(Fragment fragment, File file, ImageView into, RequestOptions requestOptions)
    {
        Glide.with(fragment).load(file).apply(requestOptions).transition(withCrossFade()).into(into);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param uri     Uri地址
     * @param into    需要加载图片的View
     */
    public static void load(Context context, Uri uri, ImageView into)
    {
        load(context, uri, into, getDefaultOptions());
    }

    /**
     * 加载图片
     *
     * @param context
     * @param uri     Uri地址
     * @param into    需要加载图片的View
     */
    public static void load(Context context, Uri uri, ImageView into, RequestOptions requestOptions)
    {
        Glide.with(context).load(uri).apply(requestOptions).transition(withCrossFade()).into(into);
    }

    /**
     * Fragment中的加载图片
     *
     * @param fragment
     * @param uri      Uri地址
     * @param into     需要加载图片的View
     */
    public static void load(Fragment fragment, Uri uri, ImageView into)
    {
        load(fragment, uri, into, getDefaultOptions());
    }

    /**
     * Fragment中的加载图片
     *
     * @param fragment
     * @param uri      Uri地址
     * @param into     需要加载图片的View
     */
    public static void load(Fragment fragment, Uri uri, ImageView into, RequestOptions requestOptions)
    {
        Glide.with(fragment).load(uri).apply(requestOptions).transition(withCrossFade()).into(into);
    }
}
