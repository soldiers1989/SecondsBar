package com.fx.secondbar.bean;

import java.io.Serializable;

/**
 * function:Q币实体信息
 * author: frj
 * modify date: 2018/9/22
 */
public class QCoinBean implements Serializable
{
    private String type;//类型
    private String price;//获得的金币数量
    private String memberid;//会员id
    private String daily;//日期
    private String status;//状态
    private String description;//描述

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getDaily()
    {
        return daily;
    }

    public void setDaily(String daily)
    {
        this.daily = daily;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
