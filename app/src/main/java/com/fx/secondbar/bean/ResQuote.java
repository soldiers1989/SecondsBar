package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:行情信息返回结果
 * author: frj
 * modify date: 2018/9/24
 */
public class ResQuote
{
    private List<QuoteBean> listTradingMarket;

    public List<QuoteBean> getListTradingMarket()
    {
        return listTradingMarket;
    }

    public void setListTradingMarket(List<QuoteBean> listTradingMarket)
    {
        this.listTradingMarket = listTradingMarket;
    }
}
