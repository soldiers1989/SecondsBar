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
    private String memberid;//会员id
    private String peopleid;//名人id
    private String amount;//数量
    private String typename;//类型名称
    private String startprice;//发行价格
    private String date;//日期
    private String statusname;//状态描述
    private String hours;//时间
    private String second;//秒数
    private String status;//状态
    private String type;//类型
    private String createtime;//创建时间
    private String price;//当前价格
    private String transaction_ID;//交易id

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

    public String getTypename()
    {
        return typename;
    }

    public void setTypename(String typename)
    {
        this.typename = typename;
    }

    public String getStartprice()
    {
        return startprice;
    }

    public void setStartprice(String startprice)
    {
        this.startprice = startprice;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getStatusname()
    {
        return statusname;
    }

    public void setStatusname(String statusname)
    {
        this.statusname = statusname;
    }

    public String getHours()
    {
        return hours;
    }

    public void setHours(String hours)
    {
        this.hours = hours;
    }

    public String getSecond()
    {
        return second;
    }

    public void setSecond(String second)
    {
        this.second = second;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getTransaction_ID()
    {
        return transaction_ID;
    }

    public void setTransaction_ID(String transaction_ID)
    {
        this.transaction_ID = transaction_ID;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.memberid);
        dest.writeString(this.peopleid);
        dest.writeString(this.amount);
        dest.writeString(this.typename);
        dest.writeString(this.startprice);
        dest.writeString(this.date);
        dest.writeString(this.statusname);
        dest.writeString(this.hours);
        dest.writeString(this.second);
        dest.writeString(this.status);
        dest.writeString(this.type);
        dest.writeString(this.createtime);
        dest.writeString(this.price);
        dest.writeString(this.transaction_ID);
    }

    public MyPurchaseBean()
    {
    }

    protected MyPurchaseBean(Parcel in)
    {
        this.memberid = in.readString();
        this.peopleid = in.readString();
        this.amount = in.readString();
        this.typename = in.readString();
        this.startprice = in.readString();
        this.date = in.readString();
        this.statusname = in.readString();
        this.hours = in.readString();
        this.second = in.readString();
        this.status = in.readString();
        this.type = in.readString();
        this.createtime = in.readString();
        this.price = in.readString();
        this.transaction_ID = in.readString();
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
