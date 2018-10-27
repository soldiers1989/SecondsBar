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
    private Integer status; //1申购中，2申购成功
    private String successnum;  //申购成功秒数
    private String successmoney; //申购成功总金额

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

    public Integer getStatus()
    {
        return status == null ? 1 : status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getSuccessnum()
    {
        return successnum;
    }

    public void setSuccessnum(String successnum)
    {
        this.successnum = successnum;
    }

    public String getSuccessmoney()
    {
        return successmoney;
    }

    public void setSuccessmoney(String successmoney)
    {
        this.successmoney = successmoney;
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
        dest.writeValue(this.status);
        dest.writeString(this.successnum);
        dest.writeString(this.successmoney);
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
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.successnum = in.readString();
        this.successmoney = in.readString();
    }

    public static final Parcelable.Creator<MyPurchaseBean> CREATOR = new Parcelable.Creator<MyPurchaseBean>()
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
