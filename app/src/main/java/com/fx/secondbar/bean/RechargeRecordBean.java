package com.fx.secondbar.bean;

/**
 * function:充值记录
 * author: frj
 * modify date: 2018/9/29
 */
public class RechargeRecordBean
{
    private String rechargerecord_ID;//充值id
    private String type;//1,
    private String createtime;//创建时间
    private String amount;  //充值金额
    private String memberid;//会员id
    private String status;   //状态值，2表示充值成功
    private String money;   //STE

    public String getRechargerecord_ID()
    {
        return rechargerecord_ID;
    }

    public void setRechargerecord_ID(String rechargerecord_ID)
    {
        this.rechargerecord_ID = rechargerecord_ID;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMoney()
    {
        return money;
    }

    public void setMoney(String money)
    {
        this.money = money;
    }
}
