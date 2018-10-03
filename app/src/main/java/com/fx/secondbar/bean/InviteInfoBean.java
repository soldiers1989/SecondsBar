package com.fx.secondbar.bean;

/**
 * function:邀请信息实体
 * author: frj
 * modify date: 2018/10/3
 */
public class InviteInfoBean
{
    private String description;// 规则描述
    private String memberid;//会员id
    private String qcointotal;   //邀请总Q币
    private String totals;       //邀请人数

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getQcointotal()
    {
        return qcointotal;
    }

    public void setQcointotal(String qcointotal)
    {
        this.qcointotal = qcointotal;
    }

    public String getTotals()
    {
        return totals;
    }

    public void setTotals(String totals)
    {
        this.totals = totals;
    }
}
