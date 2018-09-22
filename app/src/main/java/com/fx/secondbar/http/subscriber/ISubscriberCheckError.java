package com.fx.secondbar.http.subscriber;

/**
 * function:检查异常
 * author: frj
 * modify date: 2017/6/26
 */

public interface ISubscriberCheckError
{
    /**
     * 检查异常，并将对应提示语返回
     *
     * @param e
     * @return
     */
    String checkFail(Throwable e);
}
