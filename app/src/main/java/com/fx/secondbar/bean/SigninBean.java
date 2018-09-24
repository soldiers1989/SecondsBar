package com.fx.secondbar.bean;

/**
 * function:签到实体信息
 * author: frj
 * modify date: 2018/9/24
 */
public class SigninBean
{
    private Integer total;//签到排名
    private Integer days;//签到天数
    private String qcoin;//获得的奖励

    public Integer getTotal()
    {
        return total;
    }

    public void setTotal(Integer total)
    {
        this.total = total;
    }

    public Integer getDays()
    {
        return days == null ? 0 : days;
    }

    public void setDays(Integer days)
    {
        this.days = days;
    }

    public String getQcoin()
    {
        return qcoin;
    }

    public void setQcoin(String qcoin)
    {
        this.qcoin = qcoin;
    }
}
