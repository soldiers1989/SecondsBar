package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:商城列表信息实体
 * author: frj
 * modify date: 2018/9/23
 */
public class ResMall
{
    private List<CommodityBean> listMerchandise;

    public List<CommodityBean> getListMerchandise()
    {
        return listMerchandise;
    }

    public void setListMerchandise(List<CommodityBean> listMerchandise)
    {
        this.listMerchandise = listMerchandise;
    }
}
