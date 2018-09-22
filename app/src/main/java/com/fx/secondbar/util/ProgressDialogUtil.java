package com.fx.secondbar.util;

import android.app.ProgressDialog;
import android.text.TextUtils;

import com.btten.bttenlibrary.base.ActivitySupport;

/**
 * function:进度对话框生成器
 * author: frj
 * modify date: 2018/9/22
 */
public class ProgressDialogUtil
{
    /**
     * 获取ProgressDialog对象
     *
     * @param activitySupport
     * @param msg
     * @param isCanCancel
     * @return
     */
    public static ProgressDialog getProgressDialog(ActivitySupport activitySupport, String msg, boolean isCanCancel)
    {
        if (activitySupport == null)
        {
            return null;
        }
        ProgressDialog progressDialog = new ProgressDialog(activitySupport);
        if (!TextUtils.isEmpty(msg))
        {
            progressDialog.setMessage(msg);
        }
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(isCanCancel);
        return progressDialog;
    }
}
