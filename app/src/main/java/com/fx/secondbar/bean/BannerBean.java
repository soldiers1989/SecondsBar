package com.fx.secondbar.bean;

/**
 * function:广告轮播实体信息
 * author: frj
 * modify date: 2018/9/23
 */
public class BannerBean
{
    private String url;//访问地址
    private String file;//文件
    private String name;//名称
    private String status;//状态
    private String sorts;//排序
    private String type;//类型
    private String img;//展示图片地址
    private String createtime;//创建时间
    private String banner_ID;//id值
    private String description;//描述

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getFile()
    {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
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

    public String getBanner_ID()
    {
        return banner_ID;
    }

    public void setBanner_ID(String banner_ID)
    {
        this.banner_ID = banner_ID;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
