package com.fx.secondbar.http.subscriber;

import android.os.Handler;
import android.os.Message;

import com.btten.bttenlibrary.base.load.ILoadDettachListener;
import com.btten.bttenlibrary.base.load.LoadManager;
import com.btten.bttenlibrary.base.load.OnReloadListener;

/**
 * function:LoadManager与Subscriber绑定时，用于更新LoadManager的Handler
 * author: frj
 * modify date: 2017/6/26
 */

public class LoadHandler extends Handler
{
    /**
     * 显示加载
     */
    public static final int SHOW_LOAD = 1;
    /**
     * 显示空数据
     */
    public static final int SHOW_EMPTY = 2;
    /**
     * 显示加载错误
     */
    public static final int SHOW_ERROR = 3;
    /**
     * 显示加载成功
     */
    public static final int SHOW_SUCCESS = 4;

    private LoadManager loadManager;
    private OnReloadListener mOnReloadListener;

    public LoadHandler(LoadManager loadManager, ILoadDettachListener loadDettachListener, OnReloadListener onReloadListener)
    {
        super();
        this.loadManager = loadManager;
        this.loadManager.setLoadDettachListener(loadDettachListener);
        this.mOnReloadListener = onReloadListener;
    }

    /**
     * 表示加载完成
     */
    private void loadSuccess()
    {
        if (loadManager != null)
        {
            loadManager.loadSuccess();
        }
    }

    /**
     * 表示加载失败
     *
     * @param msg 失败提示语
     */
    private void loadFail(String msg)
    {
        if (loadManager != null)
        {
            loadManager.loadFail(msg, mOnReloadListener);
        }
    }

    /**
     * 表示加载为空数据
     *
     * @param msg 空数据提示语
     */
    private void loadEmpty(String msg)
    {
        if (loadManager != null)
        {
            loadManager.loadEmpty(msg);
        }
    }

    /**
     * 表示加载中...
     */
    private void loadding()
    {
        if (loadManager != null)
        {
            loadManager.loadding();
        }
    }

    @Override
    public void handleMessage(Message msg)
    {
        super.handleMessage(msg);
        switch (msg.what)
        {
            case SHOW_SUCCESS:
                loadSuccess();
                break;
            case SHOW_LOAD:
                loadding();
                break;
            case SHOW_EMPTY:
                loadEmpty((String) msg.obj);
                break;
            case SHOW_ERROR:
                loadFail((String) msg.obj);
                break;
        }
    }
}
