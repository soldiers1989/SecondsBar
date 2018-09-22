package com.btten.bttenlibrary.base.load;

/**
 * function:抽象的加载图层管理器
 * author: frj
 * modify date: 2016/12/29
 */

public abstract class BaseLoadManager
{
    /**
     * loadManager加载完成或没进行任何显示时
     */
    public static final int STATE_NORMAL = 0;
    /**
     * loadManager加载时的状态
     */
    public static final int STATE_LOADDING = 1;
    /**
     * loadManager加载失败时的状态
     */
    public static final int STATE_FAIL = 2;

    /**
     * loadManager 加载结果为空时的状态
     */
    public static final int STATE_EMPTY = 3;

    //当前加载图层的状态
    private int state = STATE_NORMAL;


    /**
     * 正在加载中
     */
    public void loadding()
    {
        state = STATE_LOADDING;
    }

    /**
     * 加载失败
     *
     * @param text     加载失败提示文本
     * @param listener 重试监听
     */
    public void loadFail(String text, OnReloadListener listener)
    {
        state = STATE_FAIL;
    }

    /**
     * 加载失败
     *
     * @param listener 重试监听
     */
    public void loadFail(OnReloadListener listener)
    {
        loadFail("", listener);
    }

    /**
     * 空数据
     *
     * @param text 空数据提示
     */
    public void loadEmpty(String text)
    {
        state = STATE_EMPTY;
    }

    /**
     * 加载成功
     */
    public void loadSuccess()
    {
        state = STATE_NORMAL;
    }

    /**
     * 获取当前加载图层状态
     *
     * @return 值为{#STATE_NORMAL #STATE_EMPTY #STATE_FAIL #STATE_LOADDING}
     */
    public int getLoadState()
    {
        return state;
    }

    /**
     * 判断是否是正在加载中
     *
     * @return true表示正在加载中
     */
    public boolean isLoadding()
    {
        return STATE_LOADDING == state;
    }

    /**
     * 判断是否是加载失败
     *
     * @return true表示加载失败
     */
    public boolean isLoadFail()
    {
        return STATE_FAIL == state;
    }

    /**
     * 判断是否加载为空的
     *
     * @return true 表示加载为空
     */
    public boolean isLoadEmpty()
    {
        return STATE_EMPTY == state;
    }

    /**
     * 判断是否是加载成功的
     *
     * @return true 表示加载成功
     */
    public boolean isLoadSuccess()
    {
        return STATE_NORMAL == state;
    }
}
