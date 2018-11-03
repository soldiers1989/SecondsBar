package com.fx.secondbar.bean;

/**
 * function:提现记录实体信息
 * author: frj
 * modify date: 2018/11/3
 */
public class WithdrawRecordBean
{
    private String fee; //提现手续费
    private String type;
    private String createtime; //提现生成时间
    private String ratio;        //提现的比例
    private String audittime;   //审核时间
    private String memberid;
    private String bankno; //提现银行卡号
    private String bankname;   //银行名称
    private String amount;      //提现STE数量
    private String money;    //提现的人民币金额
    private String statusname;    //提现的状态
    private String bankaddress;   //开户行地址
    private String withdrawrecord_ID;  //提现id
    private String description;   //提现备注，驳回原因
    private String remittancetime; //汇款时间
    //1	待审核
    //2	汇款中
    //3	提现成功
    //4	被驳回
    //0	全部
    private String status;  //状态
    private String name;   //真实名称

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
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

    public String getRatio()
    {
        return ratio;
    }

    public void setRatio(String ratio)
    {
        this.ratio = ratio;
    }

    public String getAudittime()
    {
        return audittime;
    }

    public void setAudittime(String audittime)
    {
        this.audittime = audittime;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getBankno()
    {
        return bankno;
    }

    public void setBankno(String bankno)
    {
        this.bankno = bankno;
    }

    public String getBankname()
    {
        return bankname;
    }

    public void setBankname(String bankname)
    {
        this.bankname = bankname;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getMoney()
    {
        return money;
    }

    public void setMoney(String money)
    {
        this.money = money;
    }

    public String getStatusname()
    {
        return statusname;
    }

    public void setStatusname(String statusname)
    {
        this.statusname = statusname;
    }

    public String getBankaddress()
    {
        return bankaddress;
    }

    public void setBankaddress(String bankaddress)
    {
        this.bankaddress = bankaddress;
    }

    public String getWithdrawrecord_ID()
    {
        return withdrawrecord_ID;
    }

    public void setWithdrawrecord_ID(String withdrawrecord_ID)
    {
        this.withdrawrecord_ID = withdrawrecord_ID;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getRemittancetime()
    {
        return remittancetime;
    }

    public void setRemittancetime(String remittancetime)
    {
        this.remittancetime = remittancetime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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
