package com.fx.secondbar.bean;

/**
 * function:定位信息实体
 * author: frj
 * modify date: 2018/10/24
 */
public class LocationBean
{
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //详细地址信息
    private String addr;
    //国家
    private String country;
    //省份
    private String province;
    //城市
    private String city;
    //区县
    private String district;
    //街道信息
    private String street;

    public LocationBean(double latitude, double longitude, String addr, String country, String province, String city, String district, String street)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.addr = addr;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getAddr()
    {
        return addr;
    }

    public void setAddr(String addr)
    {
        this.addr = addr;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

}
