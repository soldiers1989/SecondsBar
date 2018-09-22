package com.fx.secondbar.bean;

import java.util.List;

/**
 * function:资讯信息实体
 * author: frj
 * modify date: 2018/9/19
 */
public class InfomationBean
{
    /**
     * 图片列表
     */
    private List<Integer> list;
    /**
     * 内容
     */
    private String content;
    /**
     * 来源
     */
    private String from;
    /**
     * 评论数
     */
    private String count;

    public InfomationBean(List<Integer> list, String content, String from, String count)
    {
        this.list = list;
        this.content = content;
        this.from = from;
        this.count = count;
    }

    public List<Integer> getList()
    {
        return list;
    }

    public void setList(List<Integer> list)
    {
        this.list = list;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getCount()
    {
        return count;
    }

    public void setCount(String count)
    {
        this.count = count;
    }
}
