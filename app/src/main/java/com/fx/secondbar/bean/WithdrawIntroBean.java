package com.fx.secondbar.bean;

/**
 * function:提现说明实体信息
 * author: frj
 * modify date: 2018/10/2
 */
public class WithdrawIntroBean
{
    private String explaination;//提现说明
    private String description;//
    private Double amount;  //可提现STE数量
    private Double ratio;       //提现比值,人民币兑STE，
    private Double feesratio;   //手续费比例%
    private Double minfees;    //最低手续费STE

    public String getExplaination()
    {
        return explaination;
    }

    public void setExplaination(String explaination)
    {
        this.explaination = explaination;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Double getAmount()
    {
        return amount == null ? 0 : amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public Double getRatio()
    {
        return ratio == null ? 0 : ratio;
    }

    public void setRatio(Double ratio)
    {
        this.ratio = ratio;
    }

    public Double getFeesratio()
    {
        return feesratio == null ? 0 : feesratio;
    }

    public void setFeesratio(Double feesratio)
    {
        this.feesratio = feesratio;
    }

    public Double getMinfees()
    {
        return minfees == null ? 0 : minfees;
    }

    public void setMinfees(Double minfees)
    {
        this.minfees = minfees;
    }
}
