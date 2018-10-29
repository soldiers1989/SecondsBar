package com.fx.secondbar.bean;

/**
 * function:约TA实体信息
 * author: frj
 * modify date: 2018/10/24
 */
public class DateBean
{
    private String name;   //名称
    private String status;//状态
    private String datetime;   //活动日期
    private String content; //活动说明
    private String createtime;//创建时间
    private String address;  //地址
    private String timelength; //时长分钟
    private String lon;   //经度
    private String memberid;//1,
    private String image;  //封面图
    private String categoryid;//
    private String lat;    //纬度
    private String distance;
    private String price;     //STE价格
    private String sorts;//排序
    private String qcoin;//Q价值
    private String tag;    //标签
    private String stroke_ID;//2,
    private String type;
    private String quantity;   //人数
    private String img;   //头像
    private String account;   //会员账号
    private String nickname;    //会员昵称
    private String description;//null
    private Integer paytype;    //支付类型，1：Q支付，0：人民币支付

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

    public String getDatetime()
    {
        return datetime;
    }

    public void setDatetime(String datetime)
    {
        this.datetime = datetime;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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

    public String getLon()
    {
        return lon;
    }

    public void setLon(String lon)
    {
        this.lon = lon;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getCategoryid()
    {
        return categoryid;
    }

    public void setCategoryid(String categoryid)
    {
        this.categoryid = categoryid;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getSorts()
    {
        return sorts;
    }

    public void setSorts(String sorts)
    {
        this.sorts = sorts;
    }

    public String getQcoin()
    {
        return qcoin;
    }

    public void setQcoin(String qcoin)
    {
        this.qcoin = qcoin;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getStroke_ID()
    {
        return stroke_ID;
    }

    public void setStroke_ID(String stroke_ID)
    {
        this.stroke_ID = stroke_ID;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getPaytype()
    {
        return paytype == null ? 0 : paytype;
    }

    public void setPaytype(Integer paytype)
    {
        this.paytype = paytype;
    }
}
