package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:微博信息实体
 * author: frj
 * modify date: 2018/9/23
 */
public class WBBean
{

    private String username;//用户名
    private List<String> pictures;// 图片集
    private String content;// 内容，html文本
    private String share_COPY;// 来源
    private String picture;// 单图片
    private String title;// 标题
    private String avatar;// 头像
    private String news_ID;// 新闻id
    private String scan_NUM;// 浏览数
    private String sorts;// 排序
    private String type;// 类别
    private String share_TOTAL;// 分享数

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public List<String> getPictures()
    {
        return pictures;
    }

    public void setPictures(List<String> pictures)
    {
        this.pictures = pictures;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getShare_COPY()
    {
        return share_COPY;
    }

    public void setShare_COPY(String share_COPY)
    {
        this.share_COPY = share_COPY;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getNews_ID()
    {
        return news_ID;
    }

    public void setNews_ID(String news_ID)
    {
        this.news_ID = news_ID;
    }

    public String getScan_NUM()
    {
        return scan_NUM;
    }

    public void setScan_NUM(String scan_NUM)
    {
        this.scan_NUM = scan_NUM;
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

    public String getShare_TOTAL()
    {
        return share_TOTAL;
    }

    public void setShare_TOTAL(String share_TOTAL)
    {
        this.share_TOTAL = share_TOTAL;
    }
}
