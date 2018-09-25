package com.fx.secondbar.bean;

/**
 * function:银行信息实体
 * author: frj
 * modify date: 2018/9/22
 */
public class BankBean
{
    private String address;//地址
    private String memberid;//会员id
    private String actualname;//开户人姓名
    private String bankno;//银行卡号
    private String bankname;//银行名称
    private String bank_ID;//银行卡id

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getActualname()
    {
        return actualname;
    }

    public void setActualname(String actualname)
    {
        this.actualname = actualname;
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

    public String getBank_ID()
    {
        return bank_ID;
    }

    public void setBank_ID(String bank_ID)
    {
        this.bank_ID = bank_ID;
    }
}
