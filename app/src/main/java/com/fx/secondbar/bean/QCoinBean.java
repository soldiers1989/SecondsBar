package com.fx.secondbar.bean;

/**
 * function:Q币实体信息
 * author: frj
 * modify date: 2018/9/22
 */
public class QCoinBean
{
    private Integer type;//类型
    private Integer price;//获得的金币数量
    private String memberid;//会员id
    private String daily;//日期
    private Integer status;//状态
    private String description;//描述

    public Integer getType()
    {
        return type == null ? 0 : type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getPrice()
    {
        return price == null ? 0 : price;
    }

    public void setPrice(Integer price)
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

    public Integer getStatus()
    {
        return status == null ? 0 : status;
    }

    public void setStatus(Integer status)
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
