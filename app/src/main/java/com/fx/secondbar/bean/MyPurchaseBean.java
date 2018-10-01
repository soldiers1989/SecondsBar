package com.fx.secondbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * function:我的申购信息实体
 * author: frj
 * modify date: 2018/9/23
 */
public class MyPurchaseBean implements Parcelable
{
    private String createtime;//创建时间
    private String memberid;//会员id
    private String peopleid;//名人id
    private String amount;//数量
    private String price;//当前价格
    private String zjm;//名人助记码
    private String totalmoney;//总金额
    private String peopleimg;//头像地址
    private String peoplename;//名人姓名

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getPeopleid()
    {
        return peopleid;
    }

    public void setPeopleid(String peopleid)
    {
        this.peopleid = peopleid;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
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

    public String getTotalmoney()
    {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney)
    {
        this.totalmoney = totalmoney;
    }

    public String getPeopleimg()
    {
        return peopleimg;
    }

    public void setPeopleimg(String peopleimg)
    {
        this.peopleimg = peopleimg;
    }

    public String getPeoplename()
    {
        return peoplename;
    }

    public void setPeoplename(String peoplename)
    {
        this.peoplename = peoplename;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.createtime);
        dest.writeString(this.memberid);
        dest.writeString(this.peopleid);
        dest.writeString(this.amount);
        dest.writeString(this.price);
        dest.writeString(this.zjm);
        dest.writeString(this.totalmoney);
        dest.writeString(this.peopleimg);
        dest.writeString(this.peoplename);
    }

    public MyPurchaseBean()
    {
    }

    protected MyPurchaseBean(Parcel in)
    {
        this.createtime = in.readString();
        this.memberid = in.readString();
        this.peopleid = in.readString();
        this.amount = in.readString();
        this.price = in.readString();
        this.zjm = in.readString();
        this.totalmoney = in.readString();
        this.peopleimg = in.readString();
        this.peoplename = in.readString();
    }

    public static final Creator<MyPurchaseBean> CREATOR = new Creator<MyPurchaseBean>()
    {
        @Override
        public MyPurchaseBean createFromParcel(Parcel source)
        {
            return new MyPurchaseBean(source);
        }

        @Override
        public MyPurchaseBean[] newArray(int size)
        {
            return new MyPurchaseBean[size];
        }
    };
}
