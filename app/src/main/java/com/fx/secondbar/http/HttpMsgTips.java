package com.fx.secondbar.http;

import com.fx.secondbar.R;

/**
 * function:网络请求提示语定义
 * author: frj
 * modify date: 2017/6/26
 * modify:2018/04/23修改，将所有字符串常量移植到string文件中定义，此处定义引用id常量
 */

public class HttpMsgTips
{
    /**
     * 进度对话框提示语
     */
    public static final int PROGRESS_TIPS = R.string.progress_tips;

    /**
     * 网络超时提示语
     */
    public static final int TIMEOUT_TIPS = R.string.network_timeout_tips;

    /**
     * 数据异常提示语
     */
    public static final int DATA_ERROR_TIPS = R.string.network_data_error_tips;

    /**
     * 默认提示语
     */
    public static final int DEFAULT_MSG = R.string.network_default_error_tips;
}
