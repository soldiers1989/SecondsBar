package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:申购详情实体信息
 * author: frj
 * modify date: 2018/9/26
 */
public class PurchaseDetailBean
{
    //    private String type;//
//    private String price;//申购价格
//    private String zjm;//名人助记码
//    private String amount;//总数量
//    private String peopleid;//
//    private String soldnum;//已销售数量
//    private String peoplename;//名人名称
//    private String recommend;//推荐
//    private String starttime;//开始时间
//    private String endtime;//结束时间
//    private String peopleimg;//名人头像
//    private String remainnum;//当前会员剩余申购数量
//    private String purchase_ID;//null,
//    private String limitseconds;//个人限购数量
    private PurchaseInfoBean purchaseVO;    //申购信息
    private PersonBean peopleVO;    //名人信息
    private List<String> listTimers;    //时间作用

//    public String getType()
//    {
//        return type;
//    }
//
//    public void setType(String type)
//    {
//        this.type = type;
//    }
//
//    public String getPrice()
//    {
//        return price;
//    }
//
//    public void setPrice(String price)
//    {
//        this.price = price;
//    }
//
//    public String getZjm()
//    {
//        return zjm;
//    }
//
//    public void setZjm(String zjm)
//    {
//        this.zjm = zjm;
//    }
//
//    public String getAmount()
//    {
//        return amount;
//    }
//
//    public void setAmount(String amount)
//    {
//        this.amount = amount;
//    }
//
//    public String getPeopleid()
//    {
//        return peopleid;
//    }
//
//    public void setPeopleid(String peopleid)
//    {
//        this.peopleid = peopleid;
//    }
//
//    public String getSoldnum()
//    {
//        return soldnum;
//    }
//
//    public void setSoldnum(String soldnum)
//    {
//        this.soldnum = soldnum;
//    }
//
//    public String getPeoplename()
//    {
//        return peoplename;
//    }
//
//    public void setPeoplename(String peoplename)
//    {
//        this.peoplename = peoplename;
//    }
//
//    public String getRecommend()
//    {
//        return recommend;
//    }
//
//    public void setRecommend(String recommend)
//    {
//        this.recommend = recommend;
//    }
//
//    public String getStarttime()
//    {
//        return starttime;
//    }
//
//    public void setStarttime(String starttime)
//    {
//        this.starttime = starttime;
//    }
//
//    public String getEndtime()
//    {
//        return endtime;
//    }
//
//    public void setEndtime(String endtime)
//    {
//        this.endtime = endtime;
//    }
//
//    public String getPeopleimg()
//    {
//        return peopleimg;
//    }
//
//    public void setPeopleimg(String peopleimg)
//    {
//        this.peopleimg = peopleimg;
//    }
//
//    public String getRemainnum()
//    {
//        return remainnum;
//    }
//
//    public void setRemainnum(String remainnum)
//    {
//        this.remainnum = remainnum;
//    }
//
//    public String getPurchase_ID()
//    {
//        return purchase_ID;
//    }
//
//    public void setPurchase_ID(String purchase_ID)
//    {
//        this.purchase_ID = purchase_ID;
//    }
//
//    public String getLimitseconds()
//    {
//        return limitseconds;
//    }
//
//    public void setLimitseconds(String limitseconds)
//    {
//        this.limitseconds = limitseconds;
//    }

    public PersonBean getPeopleVO()
    {
        return peopleVO;
    }

    public void setPeopleVO(PersonBean peopleVO)
    {
        this.peopleVO = peopleVO;
    }

    public List<String> getListTimers()
    {
        return listTimers;
    }

    public void setListTimers(List<String> listTimers)
    {
        this.listTimers = listTimers;
    }

    public PurchaseInfoBean getPurchaseVO()
    {
        return purchaseVO;
    }

    public void setPurchaseVO(PurchaseInfoBean purchaseVO)
    {
        this.purchaseVO = purchaseVO;
    }
}
