package com.btten.bttenlibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * function:与屏幕宽高相关的工具类
 * author: frj
 * modify date: 2016/10/8
 */
public class DisplayUtil
{

    /**
     * 获取屏幕尺寸（如果有虚拟键盘，表示除去虚拟按键后的大小）
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getScreenSize(Context context)
    {
        if (context == null)
        {
            return null;
        }
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 获取屏幕真实尺寸
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getRealScreenSize(Context context)
    {
        if (context == null)
        {
            return null;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try
        {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            return displayMetrics;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context)
    {
        if (context == null)
        {
            return 0;
        }
        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取标题栏高度
     *
     * @param activity
     * @return
     */
    public static int getTitleHeight(Activity activity)
    {
        if (activity == null)
        {
            return 0;
        }
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获取虚拟按键区域高度（横屏时为宽度）
     *
     * @param context
     * @return
     */
    public static int getInventedHeight(Context context)
    {
        if (context == null)
        {
            return 0;
        }

        DisplayMetrics metrics = getScreenSize(context);
        DisplayMetrics realMetrics = getRealScreenSize(context);

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {//判断屏幕方向是否是横屏
            /*
                横屏时，为获取虚拟按钮的宽度
             */
            return realMetrics.widthPixels - metrics.widthPixels;
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {//判断屏幕方向是否是竖屏
            /*
                竖屏时，为获取虚拟按钮的高度
             */
            return realMetrics.heightPixels - metrics.heightPixels;
        }
        return 0;
    }

}
