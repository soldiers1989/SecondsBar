package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:个人中心数据聚合
 * author: frj
 * modify date: 2018/10/10
 */
public class MineData
{
    private List<ActiveBean> listActivity;
    private List<BannerBean> listBanner;

    public List<ActiveBean> getListActivity()
    {
        return listActivity;
    }

    public void setListActivity(List<ActiveBean> listActivity)
    {
        this.listActivity = listActivity;
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
