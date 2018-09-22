package com.fx.secondbar.bean;

import android.support.annotation.DrawableRes;

/**
 * function:商品实体信息
 * author: frj
 * modify date: 2018/9/19
 */
public class CommodityBean
{
    @DrawableRes
    private int img;
    /**
     * 标题
     */
    private String title;
    /**
     * 价格
     */
    private String price;
    /**
     * 时间
     */
    private String time;
    /**
     * 地点
     */
    private String place;

    public CommodityBean(int img, String title, String price, String time, String place)
    {
        this.img = img;
        this.title = title;
        this.price = price;
        this.time = time;
        this.place = place;
    }

    public int getImg()
    {
        return img;
    }

    public void setImg(int img)
    {
        this.img = img;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }
}
