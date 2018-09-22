package com.fx.secondbar.bean;

import android.support.annotation.DrawableRes;

/**
 * function:推荐名人实体信息
 * author: frj
 * modify date: 2018/9/19
 */
public class PersonBean
{
    @DrawableRes
    private int avatar;
    private String price;
    private String name;

    public PersonBean(int avatar, String price, String name)
    {
        this.avatar = avatar;
        this.price = price;
        this.name = name;
    }

    public int getAvatar()
    {
        return avatar;
    }

    public void setAvatar(int avatar)
    {
        this.avatar = avatar;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
