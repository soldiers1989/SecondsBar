package com.btten.bttenlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.btten.bttenlibrary.base.load.ILoadDettachListener;

/**
 * function:加载图层RelativeLayout
 * author: frj
 * modify date: 2017/6/26
 */

public class LoadRelativeLayout extends RelativeLayout
{
    //    表示LoadManager与Activity解绑监听器（可以理解为Activity finish）
    private ILoadDettachListener loadDettachListener;

    public LoadRelativeLayout(Context context)
    {
        super(context);
    }

    public LoadRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LoadRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置LoaManager与Window解绑监听器
     *
     * @param loadDettachListener
     */
    public void setLoadDettachListener(ILoadDettachListener loadDettachListener)
    {
        this.loadDettachListener = loadDettachListener;
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if (loadDettachListener != null)
        {
            loadDettachListener.onLoadDettach();
        }
    }
}
