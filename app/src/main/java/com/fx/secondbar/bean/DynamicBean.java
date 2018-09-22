package com.fx.secondbar.bean;

/**
 * function:交易动态实体信息
 * author: frj
 * modify date: 2018/9/19
 */
public class DynamicBean
{
    private int avatar;
    private String content;
    private String time;

    public DynamicBean(int avatar, String content, String time)
    {
        this.avatar = avatar;
        this.content = content;
        this.time = time;
    }

    public int getAvatar()
    {
        return avatar;
    }

    public void setAvatar(int avatar)
    {
        this.avatar = avatar;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}
