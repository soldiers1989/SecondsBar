package com.fx.secondbar.bean;

/**
 * function:持仓市值明细实体
 * author: frj
 * modify date: 2018/11/3
 */
public class MarketValueBean
{
    private String ownpeople_ID;
    private String img;
    //持有秒数
    private String amount;
    //名人助记码
    private String zjm;
    private String peopleid;
    //名人名称
    private String name;
    //名人现价
    private String price;
    private String createtime;
    private String memberid;
    //市值金额
    private String total;

    public String getOwnpeople_ID()
    {
        return ownpeople_ID;
    }

    public void setOwnpeople_ID(String ownpeople_ID)
    {
        this.ownpeople_ID = ownpeople_ID;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
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

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }
}
