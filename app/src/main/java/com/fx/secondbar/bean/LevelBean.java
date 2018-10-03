package com.fx.secondbar.bean;

/**
 * function:级别实体信息
 * author: frj
 * modify date: 2018/9/25
 */
public class LevelBean
{
    private String name;//    //等级名称
    private String level;//                 // 等级
    private String startnum;//        //Q币金额开始范围
    private String endnum;//   //Q币金额结束范围
    private String description;//等级描述
    private String levelrule_ID;//等级id
    private String prizeqcoin;  //先锋奖励Q

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getStartnum()
    {
        return startnum;
    }

    public void setStartnum(String startnum)
    {
        this.startnum = startnum;
    }

    public String getEndnum()
    {
        return endnum;
    }

    public void setEndnum(String endnum)
    {
        this.endnum = endnum;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLevelrule_ID()
    {
        return levelrule_ID;
    }

    public void setLevelrule_ID(String levelrule_ID)
    {
        this.levelrule_ID = levelrule_ID;
    }

    public String getPrizeqcoin()
    {
        return prizeqcoin;
    }

    public void setPrizeqcoin(String prizeqcoin)
    {
        this.prizeqcoin = prizeqcoin;
    }
}
