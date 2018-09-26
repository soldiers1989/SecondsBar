package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:首页-时间场数据实体信息
 * author: frj
 * modify date: 2018/9/23
 */
public class IndexTimeBean
{
    private List<PurchaseInfoBean> listPurchase;    //名人
    private List<InfomationBean> listNews;  //资讯
    private List<BannerBean> listBanner;    //轮播图
    private List<DynamicBean> listOrder;    //交易动态
    private List<CommodityBean> listMerchandise;    //商品

    public List<PurchaseInfoBean> getListPurchase()
    {
        return listPurchase;
    }

    public void setListPurchase(List<PurchaseInfoBean> listPurchase)
    {
        this.listPurchase = listPurchase;
    }

    public List<InfomationBean> getListNews()
    {
        return listNews;
    }

    public void setListNews(List<InfomationBean> listNews)
    {
        this.listNews = listNews;
    }

    public List<BannerBean> getListBanner()
    {
        return listBanner;
    }

    public void setListBanner(List<BannerBean> listBanner)
    {
        this.listBanner = listBanner;
    }

    public List<DynamicBean> getListOrder()
    {
        return listOrder;
    }

    public void setListOrder(List<DynamicBean> listOrder)
    {
        this.listOrder = listOrder;
    }

    public List<CommodityBean> getListMerchandise()
    {
        return listMerchandise;
    }

    public void setListMerchandise(List<CommodityBean> listMerchandise)
    {
        this.listMerchandise = listMerchandise;
    }
}
