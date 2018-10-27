package com.fx.secondbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * function:申购实体信息
 * author: frj
 * modify date: 2018/9/24
 */
public class PurchaseInfoBean implements Parcelable
{
    private Integer limitseconds;   //个人限购数量
    private String purchase_ID;//申购Id
    private Integer remainnum;//最小申购数量
    private String peoplename;//用户名称
    private String peopleimg;//用户头像
    private String recommend;//是否推荐
    private String type;//
    private String price;//申购单价
    private String zjm;//助记码
    private String amount;//总量
    private String peopleid;//名人id
//    private String starttime;//开始时间
//    private String endtime;//结束时间


    public String getPurchase_ID()
    {
        return purchase_ID;
    }

    public void setPurchase_ID(String purchase_ID)
    {
        this.purchase_ID = purchase_ID;
    }

    public Integer getLimitseconds()
    {
        return limitseconds == null ? 0 : limitseconds;
    }

    public void setLimitseconds(Integer limitseconds)
    {
        this.limitseconds = limitseconds;
    }

    public Integer getRemainnum()
    {
        return remainnum == null ? 0 : remainnum;
    }

    public void setRemainnum(Integer remainnum)
    {
        this.remainnum = remainnum;
    }

    public String getPeoplename()
    {
        return peoplename;
    }

    public void setPeoplename(String peoplename)
    {
        this.peoplename = peoplename;
    }

    public String getPeopleimg()
    {
        return peopleimg;
    }

    public void setPeopleimg(String peopleimg)
    {
        this.peopleimg = peopleimg;
    }

    public String getRecommend()
    {
        return recommend;
    }

    public void setRecommend(String recommend)
    {
        this.recommend = recommend;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getZjm()
    {
        return zjm;
    }

    public void setZjm(String zjm)
    {
        this.zjm = zjm;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getPeopleid()
    {
        return peopleid;
    }

    public void setPeopleid(String peopleid)
    {
        this.peopleid = peopleid;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this.limitseconds);
        dest.writeString(this.purchase_ID);
        dest.writeValue(this.remainnum);
        dest.writeString(this.peoplename);
        dest.writeString(this.peopleimg);
        dest.writeString(this.recommend);
        dest.writeString(this.type);
        dest.writeString(this.price);
        dest.writeString(this.zjm);
        dest.writeString(this.amount);
        dest.writeString(this.peopleid);
    }

    public PurchaseInfoBean()
    {
    }

    protected PurchaseInfoBean(Parcel in)
    {
        this.limitseconds = (Integer) in.readValue(Integer.class.getClassLoader());
        this.purchase_ID = in.readString();
        this.remainnum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.peoplename = in.readString();
        this.peopleimg = in.readString();
        this.recommend = in.readString();
        this.type = in.readString();
        this.price = in.readString();
        this.zjm = in.readString();
        this.amount = in.readString();
        this.peopleid = in.readString();
    }

    public static final Parcelable.Creator<PurchaseInfoBean> CREATOR = new Parcelable.Creator<PurchaseInfoBean>()
    {
        @Override
        public PurchaseInfoBean createFromParcel(Parcel source)
        {
            return new PurchaseInfoBean(source);
        }

        @Override
        public PurchaseInfoBean[] newArray(int size)
        {
            return new PurchaseInfoBean[size];
        }
    };
}
