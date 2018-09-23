package com.fx.secondbar.bean;

/**
 * function:签到规则实体信息
 * author: frj
 * modify date: 2018/9/24
 */
public class SignRuleBean
{
    private String name;//规则名称
    private String amount;//奖励数量
    private String level;//签到天数
    private String remark;//备注
    private String activityid;//
    private String description;//描述
    private String activerule_ID;//

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getActivityid()
    {
        return activityid;
    }

    public void setActivityid(String activityid)
    {
        this.activityid = activityid;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getActiverule_ID()
    {
        return activerule_ID;
    }

    public void setActiverule_ID(String activerule_ID)
    {
        this.activerule_ID = activerule_ID;
    }
}
