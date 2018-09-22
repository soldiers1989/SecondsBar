package com.fx.secondbar.http.subscriber;


import com.btten.bttenlibrary.base.load.ILoadDettachListener;
import com.btten.bttenlibrary.base.load.LoadManager;
import com.btten.bttenlibrary.base.load.OnReloadListener;
import com.btten.bttenlibrary.util.LogUtil;
import com.fx.secondbar.http.HttpManager;

import rx.Subscriber;

/**
 * function:绑定LoadManager的Subscriber
 * author: frj
 * modify date: 2017/6/26
 */

public class LoadSubscriber<T> extends Subscriber<T> implements ILoadDettachListener, ISubscriberCheckError, OnReloadListener
{

    private LoadHandler loadHandler;
    private OnLoadResultListener onLoadResultListener;

    public LoadSubscriber(LoadManager loadManager, OnLoadResultListener<T> onLoadResultListener)
    {
        loadHandler = new LoadHandler(loadManager, this, this);
        this.onLoadResultListener = onLoadResultListener;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        loadHandler.obtainMessage(LoadHandler.SHOW_LOAD).sendToTarget();
    }

    @Override
    public void onCompleted()
    {
    }

    @Override
    public void onError(Throwable e)
    {
        LogUtil.e(e.toString());
        String failMsg = checkFail(e);
        loadHandler.obtainMessage(LoadHandler.SHOW_ERROR, failMsg).sendToTarget();
    }

    @Override
    public void onNext(T t)
    {
        if (onLoadResultListener != null)
        {
            onLoadResultListener.onResult(t);
        }
    }


    @Override
    public String checkFail(Throwable t)
    {
        return HttpManager.checkLoadError(t);
    }

    @Override
    public void onLoadDettach()
    {
        if (!this.isUnsubscribed())
        {
            this.unsubscribe();
        }
    }

    @Override
    public void onReload()
    {
        if (onLoadResultListener != null)
        {
            onLoadResultListener.onReload();
        }
    }

    /**
     * 表示监听加载结果（两种：重新加载和加载完成）
     *
     * @param <T>
     */
    public interface OnLoadResultListener<T>
    {
        /**
         * 表示重新加载
         */
        void onReload();

        /**
         * 表示加载成功，返回结果
         *
         * @param t 返回结果
         */
        void onResult(T t);
    }
}
