package com.fx.secondbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * function:消息实体信息
 * author: frj
 * modify date: 2018/10/8
 */
public class MessageBean implements Parcelable
{
    private String content;
    private String title;
    private String createtime;


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

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.content);
        dest.writeString(this.title);
        dest.writeString(this.createtime);
    }

    public MessageBean()
    {
    }

    protected MessageBean(Parcel in)
    {
        this.content = in.readString();
        this.title = in.readString();
        this.createtime = in.readString();
    }

    public static final Parcelable.Creator<MessageBean> CREATOR = new Parcelable.Creator<MessageBean>()
    {
        @Override
        public MessageBean createFromParcel(Parcel source)
        {
            return new MessageBean(source);
        }

        @Override
        public MessageBean[] newArray(int size)
        {
            return new MessageBean[size];
        }
    };
}
