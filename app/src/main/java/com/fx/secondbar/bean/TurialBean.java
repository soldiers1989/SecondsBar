package com.fx.secondbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * function:教程实体信息
 * author: frj
 * modify date: 2018/9/23
 */
public class TurialBean implements Parcelable
{
    private String file;//null,
    private String status;//状态值
    private String content;//内容
    private String title;//标题
    private String sorts;//排序
    private String type;//类型
    private String img;//教程展示图片
    private String createtime;//创建时间
    private String course_ID;//教程id

    public String getFile()
    {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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

    public String getCourse_ID()
    {
        return course_ID;
    }

    public void setCourse_ID(String course_ID)
    {
        this.course_ID = course_ID;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.file);
        dest.writeString(this.status);
        dest.writeString(this.content);
        dest.writeString(this.title);
        dest.writeString(this.sorts);
        dest.writeString(this.type);
        dest.writeString(this.img);
        dest.writeString(this.createtime);
        dest.writeString(this.course_ID);
    }

    public TurialBean()
    {
    }

    protected TurialBean(Parcel in)
    {
        this.file = in.readString();
        this.status = in.readString();
        this.content = in.readString();
        this.title = in.readString();
        this.sorts = in.readString();
        this.type = in.readString();
        this.img = in.readString();
        this.createtime = in.readString();
        this.course_ID = in.readString();
    }

    public static final Parcelable.Creator<TurialBean> CREATOR = new Parcelable.Creator<TurialBean>()
    {
        @Override
        public TurialBean createFromParcel(Parcel source)
        {
            return new TurialBean(source);
        }

        @Override
        public TurialBean[] newArray(int size)
        {
            return new TurialBean[size];
        }
    };
}
