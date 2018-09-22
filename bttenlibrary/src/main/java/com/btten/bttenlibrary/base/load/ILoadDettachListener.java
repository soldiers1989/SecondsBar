package com.btten.bttenlibrary.base.load;

/**
 * function:表示LoadManager与Activity解绑监听器（可以理解为Activity finish）
 * author: frj
 * modify date: 2017/6/26
 */

public interface ILoadDettachListener
{
    /**
     * 当LoadManager与Activity解除绑定
     */
    void onLoadDettach();
}
