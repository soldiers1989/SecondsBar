package com.fx.secondbar.bean;

/**
 * function:盘口实体信息
 * author: frj
 * modify date: 2018/9/20
 */
public class Handicap
{

    private String index;
    private String price;
    private String num;

    public Handicap(String index, String price, String num)
    {
        this.index = index;
        this.price = price;
        this.num = num;
    }

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getNum()
    {
        return num;
    }

    public void setNum(String num)
    {
        this.num = num;
    }
}
