package com.fx.secondbar.http.subscriber;

/**
 * function:subscriber onNext 监听
 * author: frj
 * modify date: 2017/6/26
 */

public interface SubscriberOnNextListener<T>
{
    void onNext(T t);
}
