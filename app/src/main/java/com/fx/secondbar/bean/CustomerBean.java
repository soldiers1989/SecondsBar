package com.fx.secondbar.bean;

/**
 * function:客服实体信息
 * author: frj
 * modify date: 2018/10/4
 */
public class CustomerBean
{
    private String phone;//电话
    private String mobile;//手机号
    private String name;//名称
    private String description;//秒数

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
