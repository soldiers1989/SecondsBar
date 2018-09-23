package com.fx.secondbar.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.glide.GlideRequest;
import com.btten.bttenlibrary.glide.GlideRequests;

/**
 * function:Glide加载工具类
 * author: frj
 * modify date: 2018/9/22
 */
public class GlideLoad
{

    /**
     * Glide加载图片
     *
     * @param img
     * @param url
     */
    public static void load(ImageView img, String url)
    {
        load(img, url, false);
    }

    /**
     * Glide加载图片
     *
     * @param img
     * @param url
     * @param asBitmap
     */
    public static void load(ImageView img, String url, boolean asBitmap)
    {
        load(img, url, asBitmap, 0, 0);
    }

    /**
     * Glide加载图片
     *
     * @param img
     * @param url
     * @param asBitmap
     * @param placeholder
     * @param error
     */
    public static void load(ImageView img, String url, boolean asBitmap, @DrawableRes int placeholder, @DrawableRes int error)
    {
        GlideRequests requests = GlideApp.with(img);
        String loadUrl = null;
        if (!TextUtils.isEmpty(url))
        {
            if (url.startsWith("http://") || url.startsWith("https://"))
            {
                loadUrl = url;
            } else
            {
                loadUrl = Constants.ROOT_URL + url;
            }
        }
        if (asBitmap)
        {
            GlideRequest<Bitmap> glideRequest = requests.asBitmap();
            glideRequest.load(loadUrl);
            if (placeholder != 0)
            {
                glideRequest.placeholder(placeholder);
            }
            if (error != 0)
            {
                glideRequest.error(placeholder);
            }
            glideRequest.centerCrop().into(img);
        } else
        {
            GlideRequest<Drawable> glideRequest = requests.load(loadUrl);
            if (placeholder != 0)
            {
                glideRequest.placeholder(placeholder);
            }
            if (error != 0)
            {
                glideRequest.error(placeholder);
            }
            glideRequest.centerCrop().into(img);
        }
    }
}
