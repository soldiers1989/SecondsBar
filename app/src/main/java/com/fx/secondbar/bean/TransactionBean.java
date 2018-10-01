package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:交易中心数据信息
 * author: frj
 * modify date: 2018/9/27
 */
public class TransactionBean
{

    private Double price_DT;//  //跌停价
    private Double balanceamt;   //可用STE
    private Double price_ZT;//涨停
    private Double canbuyseconds;//可购买秒数
    private String name;//姓名
    private Double price;   //现价
    private String people_ID;//1,
    private String zjm;//助记码
    private Double haveseconds; //拥有的时间
    private List<CommissionBean> list_buy;  //最新买入
    private List<CommissionBean> list_sell; //最新卖出
    private List<CommissionBean> list_current;  //当前委托
    private List<CommissionBean> list_history;  //历史委托


    public Double getPrice_DT()
    {
        return price_DT == null ? 0 : price_DT;
    }

    public void setPrice_DT(Double price_DT)
    {
        this.price_DT = price_DT;
    }

    public Double getBalanceamt()
    {
        return balanceamt == null ? 0 : balanceamt;
    }

    public void setBalanceamt(Double balanceamt)
    {
        this.balanceamt = balanceamt;
    }

    public Double getPrice_ZT()
    {
        return price_ZT == null ? 0 : price_ZT;
    }

    public void setPrice_ZT(Double price_ZT)
    {
        this.price_ZT = price_ZT;
    }

    public Double getCanbuyseconds()
    {
        return canbuyseconds == null ? 0 : canbuyseconds;
    }

    public void setCanbuyseconds(Double canbuyseconds)
    {
        this.canbuyseconds = canbuyseconds;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getPrice()
    {
        return price == null ? 0 : price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String getPeople_ID()
    {
        return people_ID;
    }

    public void setPeople_ID(String people_ID)
    {
        this.people_ID = people_ID;
    }

    public String getZjm()
    {
        return zjm;
    }

    public void setZjm(String zjm)
    {
        this.zjm = zjm;
    }

    public List<CommissionBean> getList_buy()
    {
        return list_buy;
    }

    public void setList_buy(List<CommissionBean> list_buy)
    {
        this.list_buy = list_buy;
    }

    public List<CommissionBean> getList_sell()
    {
        return list_sell;
    }

    public void setList_sell(List<CommissionBean> list_sell)
    {
        this.list_sell = list_sell;
    }

    public List<CommissionBean> getList_current()
    {
        return list_current;
    }

    public void setList_current(List<CommissionBean> list_current)
    {
        this.list_current = list_current;
    }

    public List<CommissionBean> getList_history()
    {
        return list_history;
    }

    public void setList_history(List<CommissionBean> list_history)
    {
        this.list_history = list_history;
    }

    public Double getHaveseconds()
    {
        return haveseconds;
    }

    public void setHaveseconds(Double haveseconds)
    {
        this.haveseconds = haveseconds;
    }
}
