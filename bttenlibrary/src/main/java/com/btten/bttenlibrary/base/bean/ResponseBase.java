package com.btten.bttenlibrary.base.bean;

/**
 * function:网络请求返回结果类
 * author: frj
 * modify date: 2016/11/24
 */
public class ResponseBase
{
    /**
     * 成功请求返回
     */
    public static final int SUCCESS = 1;

    private int state; // 状态值 state;//1:代表成功;0或其他值代表处理失败
    private String message; // 描述信息

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * 检查网络请求是否成功
     *
     * @return true 表示success
     */
    public boolean checkSuccess()
    {
        return SUCCESS == state;
    }
}
