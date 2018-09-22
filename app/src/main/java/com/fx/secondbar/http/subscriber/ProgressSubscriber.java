package com.fx.secondbar.http.subscriber;

import android.content.Context;

import com.btten.bttenlibrary.util.LogUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.http.HttpManager;

import rx.Subscriber;

/**
 * function:绑定了ProgressDialog的Subscriber；当Subscriber开始创建的时候，显示ProgressDialog，当Subscriber完成时关闭ProgressDialog。或当ProgressDialog关闭的时候，取消Subscriber的订阅
 * author: frj
 * modify date: 2017/6/26
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener, ISubscriberCheckError
{
    private Context context;
    private ProgressDialogHandler mProgressDialogHandler;
    private SubscriberOnNextListener mSubscriberOnNextListener;

    public ProgressSubscriber(Context context, SubscriberOnNextListener<T> mSubscriberOnNextListener)
    {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    /**
     * 显示ProgressDialog
     */
    private void showProgressDialog()
    {
        if (mProgressDialogHandler != null)
        {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * 关闭ProgressDialog
     */
    private void dismissProgressDialog()
    {
        if (mProgressDialogHandler != null)
        {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted()
    {
    }

    @Override
    public void onError(Throwable e)
    {
        dismissProgressDialog();
        String failMsg = checkFail(e);
        LogUtil.e("error:" + e.toString());
        ShowToast.showToast(context, failMsg);
    }

    @Override
    public void onNext(T t)
    {
        dismissProgressDialog();
        mSubscriberOnNextListener.onNext(t);
    }

    @Override
    public void onCancelProgress()
    {
        if (!this.isUnsubscribed())
        {
            this.unsubscribe();
        }
    }

    @Override
    public String checkFail(Throwable t)
    {
        return HttpManager.checkLoadError(t);
    }
}
