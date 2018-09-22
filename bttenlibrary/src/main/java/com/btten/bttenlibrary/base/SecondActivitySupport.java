package com.btten.bttenlibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.btten.bttenlibrary.R;
import com.btten.bttenlibrary.view.SwipeBackLayout;

/**
 * function:第二级界面基类——第二级界面支持右滑返回上一级界面
 * author: frj
 * modify date: 2016/11/24
 */

public abstract class SecondActivitySupport extends ActivitySupport
{
    /**
     * 右滑回到上一级界面控件
     */
    protected SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.ac_second_base, null);
        layout.attachToActivity(this);
    }
}
