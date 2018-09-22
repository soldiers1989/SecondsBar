package com.fx.secondbar.http.exception;

import android.text.TextUtils;

import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.http.HttpMsgTips;

/**
 * function:表示接口请求回调异常
 * author: frj
 * modify date: 2017/6/26
 */

public class ApiException extends RuntimeException
{
    //错误码
    private String errorCode;
    //错误内容
    private String message;

    public ApiException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 获取错误代码
     *
     * @return
     */
    public String getErrorCode()
    {
        return errorCode;
    }

    /**
     * 获取错误信息
     *
     * @return
     */
    public String getErrorMsg()
    {
        if (TextUtils.isEmpty(message))
        {
            return FxApplication.getStr(HttpMsgTips.DEFAULT_MSG);
        }
        return message;
    }
}
