package com.fx.secondbar.bean;

/**
 * function:系统说明
 * author: frj
 * modify date: 2018/10/12
 */
public class SystemIntroBean
{
    private String status;//状态,
    private String content;//内容
    private String createtime;//创建时间
    private String sorts;//1,
    private String title;//标题
    private String type;//类型
    private String img;//图片地址
    private String course_ID;

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

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getSorts()
    {
        return sorts;
    }

    public void setSorts(String sorts)
    {
        this.sorts = sorts;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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

    public String getCourse_ID()
    {
        return course_ID;
    }

    public void setCourse_ID(String course_ID)
    {
        this.course_ID = course_ID;
    }
}
