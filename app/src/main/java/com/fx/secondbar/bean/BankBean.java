package com.fx.secondbar.bean;

import android.support.annotation.DrawableRes;

/**
 * function:银行信息实体
 * author: frj
 * modify date: 2018/9/22
 */
public class BankBean
{
    private String name;
    private Integer icon;
    private String num;

    public BankBean(String name, @DrawableRes Integer icon)
    {
        this.name = name;
        this.icon = icon;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getIcon()
    {
        return icon;
    }

    public void setIcon(Integer icon)
    {
        this.icon = icon;
    }
}
