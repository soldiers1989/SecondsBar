package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:首页-资讯聚合信息实体
 * author: frj
 * modify date: 2018/9/22
 */
public class IndexInformationBean
{
    //推荐名人集合
    private List<PersonBean> listPeople;
    //资讯集合
    private List<InfomationBean> listNews;
    //推荐商品集合
    private List<CommodityBean> listMerchandise;
    //轮播图集合
    private List<BannerBean> listBanner;

    public List<PersonBean> getListPeople()
    {
        return listPeople;
    }

    public void setListPeople(List<PersonBean> listPeople)
    {
        this.listPeople = listPeople;
    }

    public List<InfomationBean> getListNews()
    {
        return listNews;
    }

    public void setListNews(List<InfomationBean> listNews)
    {
        this.listNews = listNews;
    }

    public List<CommodityBean> getListMerchandise()
    {
        return listMerchandise;
    }

    public void setListMerchandise(List<CommodityBean> listMerchandise)
    {
        this.listMerchandise = listMerchandise;
    }

    public List<BannerBean> getListBanner()
    {
        return listBanner;
    }

    public void setListBanner(List<BannerBean> listBanner)
    {
        this.listBanner = listBanner;
    }
}
