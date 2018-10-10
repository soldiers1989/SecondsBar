package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:微博数据实体
 * author: frj
 * modify date: 2018/10/10
 */
public class WBData
{
    private List<BannerBean> listBanner;
    private List<WBBean> listNews;

    public List<BannerBean> getListBanner()
    {
        return listBanner;
    }

    public void setListBanner(List<BannerBean> listBanner)
    {
        this.listBanner = listBanner;
    }

    public List<WBBean> getListNews()
    {
        return listNews;
    }

    public void setListNews(List<WBBean> listNews)
    {
        this.listNews = listNews;
    }
}
