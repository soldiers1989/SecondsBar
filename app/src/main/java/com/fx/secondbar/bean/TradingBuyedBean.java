package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:交易中心-已购
 * author: frj
 * modify date: 2018/10/4
 */
public class TradingBuyedBean
{
    private Double balance;    //可用STE
    private Double marketcapamount;//市值STE
    private List<EntityBean> listTransactionInfo;   //列表

    public Double getBalance()
    {
        return balance == null ? 0 : balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
    }

    public Double getMarketcapamount()
    {
        return marketcapamount == null ? 0 : marketcapamount;
    }

    public void setMarketcapamount(Double marketcapamount)
    {
        this.marketcapamount = marketcapamount;
    }

    public List<EntityBean> getListTransactionInfo()
    {
        return listTransactionInfo;
    }

    public void setListTransactionInfo(List<EntityBean> listTransactionInfo)
    {
        this.listTransactionInfo = listTransactionInfo;
    }

    public static class EntityBean
    {
        private String transactionrecord_ID;//交易id
        private String spreadprice;      //盈亏，正为盈，负为亏
        private String name;//名人姓名
        private String img;//名人头像
        private String amount;//总价
        private String memberid;//会员id
        private String price;        //购买成本价格
        private String zjm;//助记码
        private String peopleid;//人物id
        private String second;  //购买秒数
        private String newprice;    //最新价格

        public String getTransactionrecord_ID()
        {
            return transactionrecord_ID;
        }

        public void setTransactionrecord_ID(String transactionrecord_ID)
        {
            this.transactionrecord_ID = transactionrecord_ID;
        }

        public String getSpreadprice()
        {
            return spreadprice;
        }

        public void setSpreadprice(String spreadprice)
        {
            this.spreadprice = spreadprice;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getImg()
        {
            return img;
        }

        public void setImg(String img)
        {
            this.img = img;
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

        public String getPrice()
        {
            return price;
        }

        public void setPrice(String price)
        {
            this.price = price;
        }

        public String getZjm()
        {
            return zjm;
        }

        public void setZjm(String zjm)
        {
            this.zjm = zjm;
        }

        public String getPeopleid()
        {
            return peopleid;
        }

        public void setPeopleid(String peopleid)
        {
            this.peopleid = peopleid;
        }

        public String getSecond()
        {
            return second;
        }

        public void setSecond(String second)
        {
            this.second = second;
        }

        public String getNewprice()
        {
            return newprice;
        }

        public void setNewprice(String newprice)
        {
            this.newprice = newprice;
        }
    }
}
