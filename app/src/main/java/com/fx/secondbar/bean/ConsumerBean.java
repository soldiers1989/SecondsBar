package com.fx.secondbar.bean;

/**
 * function: 消费明细实体信息
 * author: frj
 * modify date: 2018/10/2
 */
public class ConsumerBean
{
    private String type;//类型
    private String createtime;//创建时间
    private String memberid;//会员id
    private String amount;//数量
    private String description;//说明
    private String expensesrecord_ID;//消费id

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

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getExpensesrecord_ID()
    {
        return expensesrecord_ID;
    }

    public void setExpensesrecord_ID(String expensesrecord_ID)
    {
        this.expensesrecord_ID = expensesrecord_ID;
    }
}
