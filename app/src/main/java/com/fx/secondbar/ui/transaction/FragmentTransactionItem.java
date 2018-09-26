package com.fx.secondbar.ui.transaction;

import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;

/**
 * function:交易中心项的Item
 * author: frj
 * modify date: 2018/9/26
 */
public abstract class FragmentTransactionItem extends FragmentViewPagerBase
{
    /**
     * 用户id
     */
    protected String peopleId;
    /**
     * 用户姓名
     */
    protected String personName;
    /**
     * 准备刷新数据
     */
    protected boolean isPrepareRefresh;

    /**
     * 设置数据
     *
     * @param peopleId   名人id
     * @param personName 名人姓名
     */
    public void setData(String peopleId, String personName)
    {
        this.peopleId = peopleId;
        this.personName = personName;
        isPrepareRefresh = true;
    }

}
