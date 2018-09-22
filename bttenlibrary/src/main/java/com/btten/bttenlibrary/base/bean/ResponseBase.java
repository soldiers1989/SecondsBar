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

    private int code; // 状态值 state;//1:代表成功;0或其他值代表处理失败
    private String msg; // 描述信息

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    /**
     * 检查网络请求是否成功
     *
     * @return true 表示success
     */
    public boolean checkSuccess()
    {
        return SUCCESS == code;
    }
}
