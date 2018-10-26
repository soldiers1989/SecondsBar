package com.fx.secondbar.bean;

/**
 * function: 消费明细实体信息
 * author: frj
 * modify date: 2018/10/2
 */
public class ConsumerBean
{
    //类型 1	充值；2	提现；3	购买名人商品；4	购买名人时间；5	卖出名人时间；6	购买名人申购
    private String type;//类型
    private String createtime;//创建时间
    private String memberid;//会员id
    private String amount;//数量
    private String description;//说明
    private String expensesrecord_ID;//消费id
    private String qcoin;       //Q金额
    private Integer paytype;    //支付类型，1：人民币支付；2：Q支付

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

    public String getQcoin()
    {
        return qcoin;
    }

    public void setQcoin(String qcoin)
    {
        this.qcoin = qcoin;
    }

    public Integer getPaytype()
    {
        return paytype == null ? 1 : paytype;
    }

    public void setPaytype(Integer paytype)
    {
        this.paytype = paytype;
    }
}
