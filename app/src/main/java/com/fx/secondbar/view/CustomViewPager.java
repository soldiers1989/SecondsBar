package com.fx.secondbar.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * function:自定义的ViewPager，用于包含嵌套ViewPager
 * author: frj
 * modify date: 2018/9/24
 */
public class CustomViewPager extends ViewPager
{

    public CustomViewPager(@NonNull Context context)
    {
        this(context, null);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y)
    {
        if (v != this && v instanceof ViewPager)
        {
            return false;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
