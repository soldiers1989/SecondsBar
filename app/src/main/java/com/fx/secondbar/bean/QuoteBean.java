package com.fx.secondbar.bean;

/**
 * function:行情实体信息
 * author: frj
 * modify date: 2018/9/20
 */
public class QuoteBean
{
    private int avatar;
    //姓名和代币
    private String name;
    //价格
    private String price;
    //涨跌
    private String upsAndDowns;
    //涨跌幅度
    private String percent;
    //是否是涨
    private boolean isUp;

    public QuoteBean(int avatar, String name, String price, String upsAndDowns, String percent, boolean isUp)
    {
        this.avatar = avatar;
        this.name = name;
        this.price = price;
        this.upsAndDowns = upsAndDowns;
        this.percent = percent;
        this.isUp = isUp;
    }

    public int getAvatar()
    {
        return avatar;
    }

    public void setAvatar(int avatar)
    {
        this.avatar = avatar;
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

    public String getUpsAndDowns()
    {
        return upsAndDowns;
    }

    public void setUpsAndDowns(String upsAndDowns)
    {
        this.upsAndDowns = upsAndDowns;
    }

    public String getPercent()
    {
        return percent;
    }

    public void setPercent(String percent)
    {
        this.percent = percent;
    }

    public boolean isUp()
    {
        return isUp;
    }

    public void setUp(boolean up)
    {
        isUp = up;
    }
}
