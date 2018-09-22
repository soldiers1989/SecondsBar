package com.fx.secondbar.bean;

/**
 * function:如何获得Q的说明
 * author: frj
 * modify date: 2018/9/20
 */
public class QIntroBean
{
    private String name;
    private String intro;
    private String option;
    private String url;
    private String type;

    public QIntroBean(String name, String intro, String option, String url, String type)
    {
        this.name = name;
        this.intro = intro;
        this.option = option;
        this.url = url;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public String getOption()
    {
        return option;
    }

    public void setOption(String option)
    {
        this.option = option;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
