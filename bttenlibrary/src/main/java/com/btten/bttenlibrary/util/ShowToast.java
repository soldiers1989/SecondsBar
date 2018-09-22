package com.btten.bttenlibrary.util;

import com.btten.bttenlibrary.application.BtApplication;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 功能：显示Toast信息工具类
 */
public class ShowToast
{

    /**
     * 表示无效的值，用于判断颜色与资源id值
     */
    private static final int INVALID = -1;

    /**
     * 默认的自定义的Toast显示的时间长度
     */
    public static final int DEFAULT_CUSTOM_DURATION = 1500;

    /**
     * 长时间显示Toast的时间长度
     */
    public static final int LONG_CUSTOM_DURATION = 3 * 1000;

    /**
     * 显示Toast提示信息
     *
     * @param msg 提示内容
     */
    public static void showToast(String msg)
    {
        showToast(BtApplication.getApplication().getApplicationContext(), msg);
    }

    /**
     * @param context
     * @param msg
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, String msg)
    {
        showToast(context, msg, false);
    }

    /**
     * 显示Toast提示信息
     *
     * @param value 提示内容
     */
    public static void showToast(int value)
    {
        showToast(BtApplication.getApplication().getApplicationContext(), value);
    }

    /**
     * @param context
     * @param value
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, int value)
    {
        showToast(context, String.valueOf(value), false);
    }

    /**
     * 显示Toast提示信息
     *
     * @param value 提示内容
     */
    public static void showToast(float value)
    {
        showToast(BtApplication.getApplication().getApplicationContext(), value);
    }


    /**
     * @param context
     * @param value
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, float value)
    {
        showToast(context, String.valueOf(value), false);
    }

    /**
     * 显示Toast提示信息
     *
     * @param value 提示内容
     */
    public static void showToast(double value)
    {
        showToast(BtApplication.getApplication().getApplicationContext(), value);
    }

    /**
     * @param context
     * @param value
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, double value)
    {
        showToast(context, String.valueOf(value), false);
    }

    /**
     * 显示Toast提示信息
     *
     * @param value 提示内容
     */
    public static void showToast(boolean value)
    {
        showToast(BtApplication.getApplication().getApplicationContext(), value);
    }

    /**
     * @param context
     * @param value
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, boolean value)
    {
        showToast(context, String.valueOf(value), false);
    }

    /**
     * @param context
     * @param msg
     * @param isLong  表示是否是长时间显示 TRUE表示是
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, String msg, boolean isLong)
    {
        if (context != null && msg != null)
        {
            if (isLong)
            {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * @param context
     * @param value
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, int value, boolean isLong)
    {
        showToast(context, String.valueOf(value), isLong);
    }

    /**
     * @param context
     * @param value
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, float value, boolean isLong)
    {
        showToast(context, String.valueOf(value), isLong);
    }

    /**
     * @param context
     * @param value
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, double value, boolean isLong)
    {
        showToast(context, String.valueOf(value), isLong);
    }

    /**
     * @param context
     * @param value
     * @author：Frj 功能:显示Toast提示信息 使用方法：
     */
    public static void showToast(Context context, boolean value, boolean isLong)
    {
        showToast(context, String.valueOf(value), isLong);
    }

    /**
     * 显示自定义的Toast
     *
     * @param context
     * @param text      提示文本
     * @param duration  显示时间长度
     * @param bgResId   背景资源Id
     * @param textColor 字体颜色
     */
    public static void showToastCustom(Context context, String text, int duration, int bgResId, int textColor)
    {
        Toast toast = new Toast(context);
        if (INVALID != duration)
        {
            toast.setDuration(duration);
        } else
        {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        TextView textView = new TextView(context);
        textView.setText(VerificationUtil.verifyDefault(text, ""));
        if (INVALID != bgResId)
        {
            textView.setBackgroundResource(bgResId);
        }
        if (INVALID != textColor)
        {
            textView.setTextColor(textColor);
        }
        textView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        toast.setView(textView);
        toast.show();
    }

    /**
     * 显示自定义的Toast（风格已配置好，如需修改，请参照代码中的资源修改）
     *
     * @param context
     * @param text     显示文本
     * @param duration 显示长度
     */
    public static void showToastCustom(Context context, String text, int duration)
    {
        showToastCustom(context, text, duration,
                ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getDrawableId(
                        "drawable_toast_bg"),
                BtApplication.getApplication().getApplicationContext().getResources()
                        .getColor(ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext())
                                .getColorId("custom_toast_text_color")));
    }

    /**
     * 显示自定义的Toast（风格已配置好，如需修改，请参照代码中的资源修改）, 显示时长为自定义
     *
     * @param context
     * @param text    显示文本
     */
    public static void showToastCustom(Context context, String text)
    {
        showToastCustom(context, text, DEFAULT_CUSTOM_DURATION);
    }
}
