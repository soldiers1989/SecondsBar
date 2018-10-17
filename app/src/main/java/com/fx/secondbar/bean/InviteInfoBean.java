package com.fx.secondbar.bean;

/**
 * function:邀请信息实体
 * author: frj
 * modify date: 2018/10/3
 */
public class InviteInfoBean
{
    private String description;// 规则描述
    private String memberid;//会员id
    private String qcointotal;   //邀请总Q币
    private String totals;       //邀请人数
    private LevelIncomeBean oneInviteInfo;  //一级收益说明
    private LevelIncomeBean twoInviteInfo;  //二级收益说明

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getQcointotal()
    {
        return qcointotal;
    }

    public void setQcointotal(String qcointotal)
    {
        this.qcointotal = qcointotal;
    }

    public String getTotals()
    {
        return totals;
    }

    public void setTotals(String totals)
    {
        this.totals = totals;
    }

    public LevelIncomeBean getOneInviteInfo()
    {
        return oneInviteInfo;
    }

    public void setOneInviteInfo(LevelIncomeBean oneInviteInfo)
    {
        this.oneInviteInfo = oneInviteInfo;
    }

    public LevelIncomeBean getTwoInviteInfo()
    {
        return twoInviteInfo;
    }

    public void setTwoInviteInfo(LevelIncomeBean twoInviteInfo)
    {
        this.twoInviteInfo = twoInviteInfo;
    }

    /**
     * 级别收益说明
     */
    public static class LevelIncomeBean
    {
        private String amount;  //一级奖励值
        private String memberid;
        private String totals;    //一级邀请人数
        private String qcointotal;   //一级奖励总和

        public String getAmount()
        {
            return amount;
        }

        public void setAmount(String amount)
        {
            this.amount = amount;
        }

        public String getMemberid()
        {
            return memberid;
        }

        public void setMemberid(String memberid)
        {
            this.memberid = memberid;
        }

        public String getTotals()
        {
            return totals;
        }

        public void setTotals(String totals)
        {
            this.totals = totals;
        }

        public String getQcointotal()
        {
            return qcointotal;
        }

        public void setQcointotal(String qcointotal)
        {
            this.qcointotal = qcointotal;
        }
    }
}
