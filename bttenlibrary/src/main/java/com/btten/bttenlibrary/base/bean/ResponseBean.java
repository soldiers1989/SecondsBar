package com.btten.bttenlibrary.base.bean;

public class ResponseBean<T> extends ResponseBase
{
    //返回数据实体信息
    private T data;

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }


}
