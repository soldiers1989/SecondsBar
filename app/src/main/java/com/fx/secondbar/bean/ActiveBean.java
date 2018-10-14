package com.fx.secondbar.bean;

/**
 * function:活动实体信息
 * author: frj
 * modify date: 2018/9/23
 */
public class ActiveBean
{
    /**
     * 签到
     */
    public static final int TYPE_SIGN = 1;
    /**
     * 邀请
     */
    public static final int TYPE_INVITE = 2;
    /**
     * 分享
     */
    public static final int TYPE_SHARE = 3;
    /**
     * 浏览新闻
     */
    public static final int TYPE_BROWE = 4;
    /**
     * 打开App
     */
    public static final int TYPE_OPEN_APP = 5;
    /**
     * Web
     */
    public static final int TYPE_WEB = 6;
    /**
     * Q 夺宝
     */
    public static final int TYPE_RANK = 7;
    /**
     * 充值
     */
    public static final int TYPE_RECHARGE=9;


    private String name;//活动名称
    private String content;//活动内容
    private String sorts;//1,
    private String type;//活动类型
    private String endtime;//结束时间
    private String starttime;//开始时间
    private String description;//按钮描述
    private String activity_ID;//活动id
    private String path;    //链接

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public String getEndtime()
    {
        return endtime;
    }

    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }

    public String getStarttime()
    {
        return starttime;
    }

    public void setStarttime(String starttime)
    {
        this.starttime = starttime;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getActivity_ID()
    {
        return activity_ID;
    }

    public void setActivity_ID(String activity_ID)
    {
        this.activity_ID = activity_ID;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
}
