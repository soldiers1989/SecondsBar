package com.fx.secondbar.bean;

/**
 * function:商品实体信息
 * author: frj
 * modify date: 2018/9/19
 */
public class CommodityBean
{
    private String name;//商品名称
    private String status;//状态
    private String content;//商品介绍
    private String sorts;//排序
    private String type;//类型
    private String createtime;//创建时间
    private String price;//价格
    private String qcoin;   //Q价格
    private String address;//地点
    private String categoryid;//栏目id
    private String timelength;//时长
    private String image;//图片地址
    private String merchandise_ID;//商品id
    private String datetime;    //开始日期
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

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getSorts()
    {
        return sorts;
    }

    public void setSorts(String sorts)
    {
        this.sorts = sorts;
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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCategoryid()
    {
        return categoryid;
    }

    public void setCategoryid(String categoryid)
    {
        this.categoryid = categoryid;
    }

    public String getTimelength()
    {
        return timelength;
    }

    public void setTimelength(String timelength)
    {
        this.timelength = timelength;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getMerchandise_ID()
    {
        return merchandise_ID;
    }

    public void setMerchandise_ID(String merchandise_ID)
    {
        this.merchandise_ID = merchandise_ID;
    }

    public String getQcoin()
    {
        return qcoin;
    }

    public void setQcoin(String qcoin)
    {
        this.qcoin = qcoin;
    }

    public String getDatetime()
    {
        return datetime;
    }

    public void setDatetime(String datetime)
    {
        this.datetime = datetime;
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
