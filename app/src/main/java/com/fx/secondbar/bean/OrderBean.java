package com.fx.secondbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * function:商品订单信息实体
 * author: frj
 * modify date: 2018/9/23
 */
public class OrderBean implements Parcelable
{

    private String memberid;// 会员id
    private String orderdate;// 订单日期
    private String amount;// 数量
    private String nickname;// 昵称
    private String order_ID;// 订单id
    private String goodsname;// 商品名称
    private String name;// 商品名称
    private Integer status;// 状态
    private String statusname;// 状态描述
    private String img;// 商品图片
    private String createtime;// 创建时间
    private String address;//地点
    private String timelength;// 时长
    private String price;// 价格
    private String merchandiseid;// 商品id
    private String qcoin;//Q金额
    private Integer type;//支付类型 2：Q支付，1：STE支付
    private Integer ordertype;  //订单类型  1：商品订单，2约她订单
    private String strokeid;    //约吧行程id

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getOrderdate()
    {
        return orderdate;
    }

    public void setOrderdate(String orderdate)
    {
        this.orderdate = orderdate;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getOrder_ID()
    {
        return order_ID;
    }

    public void setOrder_ID(String order_ID)
    {
        this.order_ID = order_ID;
    }

    public String getGoodsname()
    {
        return goodsname;
    }

    public void setGoodsname(String goodsname)
    {
        this.goodsname = goodsname;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getStatusname()
    {
        return statusname;
    }

    public void setStatusname(String statusname)
    {
        this.statusname = statusname;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getTimelength()
    {
        return timelength;
    }

    public void setTimelength(String timelength)
    {
        this.timelength = timelength;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getMerchandiseid()
    {
        return merchandiseid;
    }

    public void setMerchandiseid(String merchandiseid)
    {
        this.merchandiseid = merchandiseid;
    }

    public String getQcoin()
    {
        return qcoin;
    }

    public void setQcoin(String qcoin)
    {
        this.qcoin = qcoin;
    }

    public Integer getType()
    {
        return type == null ? 1 : type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getOrdertype()
    {
        return ordertype == null ? 1 : ordertype;
    }

    public void setOrdertype(Integer ordertype)
    {
        this.ordertype = ordertype;
    }

    public String getStrokeid()
    {
        return strokeid;
    }

    public void setStrokeid(String strokeid)
    {
        this.strokeid = strokeid;
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
        dest.writeString(this.orderdate);
        dest.writeString(this.amount);
        dest.writeString(this.nickname);
        dest.writeString(this.order_ID);
        dest.writeString(this.goodsname);
        dest.writeString(this.name);
        dest.writeValue(this.status);
        dest.writeString(this.statusname);
        dest.writeString(this.img);
        dest.writeString(this.createtime);
        dest.writeString(this.address);
        dest.writeString(this.timelength);
        dest.writeString(this.price);
        dest.writeString(this.merchandiseid);
        dest.writeString(this.qcoin);
        dest.writeValue(this.type);
        dest.writeValue(this.ordertype);
        dest.writeString(this.strokeid);
    }

    public OrderBean()
    {
    }

    protected OrderBean(Parcel in)
    {
        this.memberid = in.readString();
        this.orderdate = in.readString();
        this.amount = in.readString();
        this.nickname = in.readString();
        this.order_ID = in.readString();
        this.goodsname = in.readString();
        this.name = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.statusname = in.readString();
        this.img = in.readString();
        this.createtime = in.readString();
        this.address = in.readString();
        this.timelength = in.readString();
        this.price = in.readString();
        this.merchandiseid = in.readString();
        this.qcoin = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ordertype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.strokeid = in.readString();
    }

    public static final Parcelable.Creator<OrderBean> CREATOR = new Parcelable.Creator<OrderBean>()
    {
        @Override
        public OrderBean createFromParcel(Parcel source)
        {
            return new OrderBean(source);
        }

        @Override
        public OrderBean[] newArray(int size)
        {
            return new OrderBean[size];
        }
    };
}
