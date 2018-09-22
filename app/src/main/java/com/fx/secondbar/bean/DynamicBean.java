package com.fx.secondbar.bean;

/**
 * function:交易动态实体信息
 * author: frj
 * modify date: 2018/9/19
 */
public class DynamicBean
{
    private Integer minutes;//分钟
    private String name;//名称
    private String status;//状态
    private String img;//商品图片地址
    private String createtime;//创建时间
    private String price;//价格
    private String address;//地点
    private String timelength;//时长
    private String memberid;//用户id
    private String order_ID;//订单id
    private String amount;//数量
    private String orderdate;//订单时间
    private String nickname;//用户昵称
    private String goodsname;//商品名称
    private String merchandiseid;//商品id

    public Integer getMinutes()
    {
        return minutes == null ? 0 : minutes;
    }

    public void setMinutes(Integer minutes)
    {
        this.minutes = minutes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
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

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getOrder_ID()
    {
        return order_ID;
    }

    public void setOrder_ID(String order_ID)
    {
        this.order_ID = order_ID;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getOrderdate()
    {
        return orderdate;
    }

    public void setOrderdate(String orderdate)
    {
        this.orderdate = orderdate;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getGoodsname()
    {
        return goodsname;
    }

    public void setGoodsname(String goodsname)
    {
        this.goodsname = goodsname;
    }

    public String getMerchandiseid()
    {
        return merchandiseid;
    }

    public void setMerchandiseid(String merchandiseid)
    {
        this.merchandiseid = merchandiseid;
    }
}
