package com.btten.bttenlibrary.http;

import com.btten.bttenlibrary.base.bean.ResponseBean;

/**
 * function:网络请求返回结果回调
 * author: frj
 * modify date: 2016/12/30
 */
public interface CallBackData<T>
{
    /**
     * 网络请求成功
     *
     * @param t 返回结果实体
     */
    void onSuccess(ResponseBean<T> t);

    /**
     * 网络请求失败
     *
     * @param failMsg 失败信息
     */
    void onFail(String failMsg);
}
