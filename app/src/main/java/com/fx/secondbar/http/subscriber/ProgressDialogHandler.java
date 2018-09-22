package com.fx.secondbar.http.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.http.HttpMsgTips;

/**
 * function:ProgressDialog Handler;用于处理ProgressDialog 的显示与关闭
 * author: frj
 * modify date: 2017/6/26
 */

public class ProgressDialogHandler extends Handler
{
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;

    private Context context;
    private boolean cancelable; //表示是否可以取消
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable)
    {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    /**
     * 初始化ProgressDialog
     */
    private void initProgressDialog()
    {
        if (pd == null)
        {
            pd = new ProgressDialog(context);
            pd.setMessage(FxApplication.getStr(HttpMsgTips.PROGRESS_TIPS));
            pd.setCancelable(cancelable);
            if (cancelable)
            {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialogInterface)
                    {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!pd.isShowing())
            {
                pd.show();
            }
        }
    }

    /**
     * 关闭ProgressDialog
     */
    private void dismissProgressDialog()
    {
        if (pd != null)
        {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg)
    {
        switch (msg.what)
        {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
