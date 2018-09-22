package com.fx.secondbar.util;

/**
 * function:常量
 * author: frj
 * modify date: 2018/9/7
 */
public class Constants
{
    /**
     * 根URL
     */
    public static final String ROOT_URL = "http://www.feixingtech.com:8080/";

    public static final String DEBUG_URL = ROOT_URL + "api/";
    public static final String RELEASE_URL = ROOT_URL + "api/";

    public static final String ROOT_DIR = "FxSecondsBar";

    /**
     * 登录接口
     */
    public static final String API_LOGIN = "login";
    /**
     * 退出登录接口
     */
    public static final String API_LOGOUT = "down";
    /**
     * 首页时间场接口
     */
    public static final String API_INDEX_TIMER = "index/timer";
    /**
     * 首页资讯接口
     */
    public static final String API_INDEX_INFORMATION = "index/news";

    /**
     * 首页微博接口
     */
    public static final String API_INDEX_WB = "index/weibo";
    /**
     * 首页教程接口
     */
    public static final String API_INDEX_TURIAL = "index/course";
    /**
     * 商城列表接口
     */
    public static final String API_MALL_LIST = "mall";
    /**
     * 商品信息接口
     */
    public static final String API_MALL_INFO = "mall/info";
    /**
     * 商品购买接口
     */
    public static final String API_MALL_BUY = "mall/buy";
    /**
     * 行情列表接口
     */
    public static final String API_QUOTE_LIST = "people/list";
    /**
     * 交易中心购买接口
     */
    public static final String API_TRANSACTION_BUY = "people/buy";
    /**
     * 交易中心卖出接口
     */
    public static final String API_TRANSACTION_SALE = "people/sell";
    /**
     * 行情-添加自选
     */
    public static final String API_QUOTE_ADD_CUSTOM = "people/collection";
    /**
     * 行情-删除自选接口
     */
    public static final String API_QUOTE_REMOVE_CUSTOME = "people/collection/delete";
    /**
     * 我的订单列表接口
     */
    public static final String API_ORDER_LIST = "order/list";
    /**
     * 我的申购列表接口
     */
    public static final String API_MY_PURCHASE = "transaction/list";
}
