package com.fx.secondbar.bean;

/**
 * function:排行榜实体信息
 * author: frj
 * modify date: 2018/10/3
 */
public class RangeBean
{
    private String img;//头像
    private String memberid;//会员id
    private String nickname;//昵称
    private Integer level;//排名
    private String amt;//当日获取的Q总收益
    private String daily;//日期

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Integer getLevel()
    {
        return level == null ? 0 : level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public String getAmt()
    {
        return amt;
    }

    public void setAmt(String amt)
    {
        this.amt = amt;
    }

    public String getDaily()
    {
        return daily;
    }

    public void setDaily(String daily)
    {
        this.daily = daily;
    }
}
