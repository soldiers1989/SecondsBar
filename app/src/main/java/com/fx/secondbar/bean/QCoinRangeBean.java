package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:夺宝信息实体
 * author: frj
 * modify date: 2018/10/3
 */
public class QCoinRangeBean
{
    //今日收益榜
    private List<RangeBean> listToDayQcoinRank;
    //当前用户今日收益排行
    private RangeBean toDayQcoinRank;
    //昨日收益榜
    private List<RangeBean> listYesterDayQcoinRank;
    //当前用户昨日收益排行
    private RangeBean yesterDayQcoinRank;

    public List<RangeBean> getListToDayQcoinRank()
    {
        return listToDayQcoinRank;
    }

    public void setListToDayQcoinRank(List<RangeBean> listToDayQcoinRank)
    {
        this.listToDayQcoinRank = listToDayQcoinRank;
    }

    public RangeBean getToDayQcoinRank()
    {
        return toDayQcoinRank;
    }

    public void setToDayQcoinRank(RangeBean toDayQcoinRank)
    {
        this.toDayQcoinRank = toDayQcoinRank;
    }

    public List<RangeBean> getListYesterDayQcoinRank()
    {
        return listYesterDayQcoinRank;
    }

    public void setListYesterDayQcoinRank(List<RangeBean> listYesterDayQcoinRank)
    {
        this.listYesterDayQcoinRank = listYesterDayQcoinRank;
    }

    public RangeBean getYesterDayQcoinRank()
    {
        return yesterDayQcoinRank;
    }

    public void setYesterDayQcoinRank(RangeBean yesterDayQcoinRank)
    {
        this.yesterDayQcoinRank = yesterDayQcoinRank;
    }
}
