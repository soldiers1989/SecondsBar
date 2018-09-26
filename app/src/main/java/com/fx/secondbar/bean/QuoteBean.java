package com.fx.secondbar.bean;

/**
 * function:行情实体信息
 * author: frj
 * modify date: 2018/9/20
 */
public class QuoteBean
{

    private Double yesterdaysprice;  //昨天收盘价
    private Double highest;     //最高价
    private Double todayprice;   //今日开盘价
    private Double lowest;     //最低价
    private Double decline;     //涨跌值
    private Double gain;        //涨跌幅百分百
    private String name;   //名人名称
    private String zjm;       //名人助记码
    private String img;//头像
    private String peopleid;//名人id
    private Double price;   //当前价格
    private Integer iscollection;   //1表示已自选

    public Double getYesterdaysprice()
    {
        return yesterdaysprice == null ? 0 : yesterdaysprice;
    }

    public void setYesterdaysprice(Double yesterdaysprice)
    {
        this.yesterdaysprice = yesterdaysprice;
    }

    public Double getHighest()
    {
        return highest == null ? 0 : highest;
    }

    public void setHighest(Double highest)
    {
        this.highest = highest;
    }

    public Double getTodayprice()
    {
        return todayprice == null ? 0 : todayprice;
    }

    public void setTodayprice(Double todayprice)
    {
        this.todayprice = todayprice;
    }

    public Double getLowest()
    {
        return lowest == null ? 0 : lowest;
    }

    public void setLowest(Double lowest)
    {
        this.lowest = lowest;
    }

    public Double getDecline()
    {
        return decline == null ? 0 : decline;
    }

    public void setDecline(Double decline)
    {
        this.decline = decline;
    }

    public Double getGain()
    {
        return gain == null ? 0 : gain;
    }

    public void setGain(Double gain)
    {
        this.gain = gain;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getZjm()
    {
        return zjm;
    }

    public void setZjm(String zjm)
    {
        this.zjm = zjm;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getPeopleid()
    {
        return peopleid;
    }

    public void setPeopleid(String peopleid)
    {
        this.peopleid = peopleid;
    }

    public Double getPrice()
    {
        return price == null ? 0 : price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Integer getIscollection()
    {
        return iscollection == null ? 0 : iscollection;
    }

    public void setIscollection(Integer iscollection)
    {
        this.iscollection = iscollection;
    }
}
