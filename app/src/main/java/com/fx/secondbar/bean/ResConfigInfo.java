package com.fx.secondbar.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * function:配置信息
 * author: frj
 * modify date: 2018/9/24
 */
public class ResConfigInfo implements Serializable, Cloneable
{
    //资讯栏目
    private List<CategoryBean> listCategoryNews;
    //名人和商城栏目
    private List<CategoryBean> listCategoryStar;
    //签到规则
    private List<SignRuleBean> listSigninRule;

    public List<CategoryBean> getListCategoryNews()
    {
        return listCategoryNews == null ? new ArrayList<CategoryBean>() : listCategoryNews;
    }

    public void setListCategoryNews(List<CategoryBean> listCategoryNews)
    {
        this.listCategoryNews = listCategoryNews;
    }

    public List<CategoryBean> getListCategoryStar()
    {
        return listCategoryStar == null ? new ArrayList<CategoryBean>() : listCategoryStar;
    }

    public void setListCategoryStar(List<CategoryBean> listCategoryStar)
    {
        this.listCategoryStar = listCategoryStar;
    }

    public List<SignRuleBean> getListSigninRule()
    {
        return listSigninRule == null ? new ArrayList<SignRuleBean>() : listSigninRule;
    }

    public void setListSigninRule(List<SignRuleBean> listSigninRule)
    {
        this.listSigninRule = listSigninRule;
    }

    @Override
    public ResConfigInfo clone()
    {
        ResConfigInfo configInfo = null;
        try
        {
            configInfo = (ResConfigInfo) super.clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            configInfo = new ResConfigInfo();
        }
        return configInfo;
    }
}
