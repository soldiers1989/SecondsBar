package com.fx.secondbar.bean;

/**
 * function:今日收益说明
 * author: frj
 * modify date: 2018/10/2
 */
public class TodayIncomeBean
{
    private String memberid;    //会员id
    private String description; //收益描述
    private DayIncomeDetaisBean toDayQcoinTotal;    //今日收益
    private DayIncomeDetaisBean yesterDayQcoinTotal;    //昨日收益

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public DayIncomeDetaisBean getToDayQcoinTotal()
    {
        return toDayQcoinTotal;
    }

    public void setToDayQcoinTotal(DayIncomeDetaisBean toDayQcoinTotal)
    {
        this.toDayQcoinTotal = toDayQcoinTotal;
    }

    public DayIncomeDetaisBean getYesterDayQcoinTotal()
    {
        return yesterDayQcoinTotal;
    }

    public void setYesterDayQcoinTotal(DayIncomeDetaisBean yesterDayQcoinTotal)
    {
        this.yesterDayQcoinTotal = yesterDayQcoinTotal;
    }

    /**
     * 日收益说明
     */
    public static class DayIncomeDetaisBean
    {
        private String type;
        private String memberid;//
        private String daily;//
        private String totaltimes;    //今日总分钟数
        private String description;
        private String qcointotals;   //今日总Q币

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getMemberid()
        {
            return memberid;
        }

        public void setMemberid(String memberid)
        {
            this.memberid = memberid;
        }

        public String getDaily()
        {
            return daily;
        }

        public void setDaily(String daily)
        {
            this.daily = daily;
        }

        public String getTotaltimes()
        {
            return totaltimes;
        }

        public void setTotaltimes(String totaltimes)
        {
            this.totaltimes = totaltimes;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public String getQcointotals()
        {
            return qcointotals;
        }

        public void setQcointotals(String qcointotals)
        {
            this.qcointotals = qcointotals;
        }
    }
}
