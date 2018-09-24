package com.fx.secondbar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function:用户信息实体
 * author: frj
 * modify date: 2018/9/22
 */
public class UserInfoBean implements Serializable
{
    private String paymentpassword;//是否设置支付密码 1表示已设置
    private String img;// 头像地址
    private String actualname;// 名字
    private Integer level;// 等级
    private Double balance;// 余额
    private String deviceid;// 设备号
    private String account;// 用户账号
    private String nickname;// 昵称
    private Double qcoin;// Q币
    private Double amt;// 30.0,
    private Double freeze;// 0.0,  //冻结金额
    private String inviter; //邀请人
    private Integer isCheckin;  //是否签到 1表示已签到
    private Integer checkinDays;    //签到天数
    private List<QCoinBean> listQcoin; //今日收益记录

    public String getPaymentpassword()
    {
        return paymentpassword;
    }

    public void setPaymentpassword(String paymentpassword)
    {
        this.paymentpassword = paymentpassword;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getActualname()
    {
        return actualname;
    }

    public void setActualname(String actualname)
    {
        this.actualname = actualname;
    }

    public Integer getLevel()
    {
        return level == null ? 0 : level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Double getBalance()
    {
        return balance == null ? 0 : balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
    }

    public String getDeviceid()
    {
        return deviceid;
    }

    public void setDeviceid(String deviceid)
    {
        this.deviceid = deviceid;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Double getQcoin()
    {
        return qcoin == null ? 0 : qcoin;
    }

    public void setQcoin(Double qcoin)
    {
        this.qcoin = qcoin;
    }

    public Double getAmt()
    {
        return amt == null ? 0 : amt;
    }

    public void setAmt(Double amt)
    {
        this.amt = amt;
    }

    public Double getFreeze()
    {
        return freeze == null ? 0 : freeze;
    }

    public void setFreeze(Double freeze)
    {
        this.freeze = freeze;
    }

    public String getInviter()
    {
        return inviter;
    }

    public void setInviter(String inviter)
    {
        this.inviter = inviter;
    }

    public Integer getIsCheckin()
    {
        return isCheckin == null ? 0 : isCheckin;
    }

    public void setIsCheckin(Integer isCheckin)
    {
        this.isCheckin = isCheckin;
    }

    public Integer getCheckinDays()
    {
        return checkinDays == null ? 0 : checkinDays;
    }

    public void setCheckinDays(Integer checkinDays)
    {
        this.checkinDays = checkinDays;
    }

    public List<QCoinBean> getListQcoin()
    {
        return listQcoin;
    }

    public void setListQcoin(List<QCoinBean> listQcoin)
    {
        this.listQcoin = listQcoin;
    }
}
