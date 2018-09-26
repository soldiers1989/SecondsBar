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
     * 资讯详情访问地址
     */
    public static final String URL_INFORMATION = ROOT_URL + "/static/mb-front/news.html?id=";

    /**
     * 更新用户数据
     */
    public static final String ACTION_REFRESH_USERINFO = "action_refresh_userinfo";

    /**
     * 更新个人中心显示
     */
    public static final String ACTION_REFRESH_PERSON_SHOW = "action_refresh_person_show";

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
     * 名人详情
     */
    public static final String API_QUOTE_DETAIL = "people/info";
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
    /**
     * 获取Q币活动接口
     */
    public static final String API_GET_ACTIVITY_Q = "activity/list";
    /**
     * 完成Q币活动接口
     */
    public static final String API_FINISH_ACTIVITY = "activity/finish";

    /**
     * 签到接口
     */
    public static final String API_SIGNIN = "signin";

    /**
     * 申购列表接口
     */
    public static final String API_PURCHASE_LIST = "purchase/list";
    /**
     * 申购详情接口
     */
    public static final String API_PURCHASE_DETAIL = "purchase/info";

    /**
     * 获取栏目接口（配置）
     */
    public static final String API_GET_CATEGORY = "category/all";
    /**
     * 上传头像接口
     */
    public static final String API_UPLOAD_AVATAR = "member/uploadImg";

    /**
     * 设置昵称接口
     */
    public static final String API_SET_NICKNAME = "member/setNickname";

    /**
     * 我的银行卡列表
     */
    public static final String API_MY_BANK_LIST = "member/bank/list";

    /**
     * 添加银行卡
     */
    public static final String API_ADD_BANK = "member/bank/add";
    /**
     * 绑定手机号
     */
    public static final String API_BIND_PHONE = "member/setPhoneNo";
    /**
     * 设置支付密码
     */
    public static final String API_SET_PAYPWD = "member/setPaymentPassword";
    /**
     * 更新支付密码
     */
    public static final String API_UPDATE_PAYPWD = "member/editPaymentPassword";
    /**
     * 会员等级列表
     */
    public static final String API_LEVEL_LIST = "member/level/list";
    /**
     * 获取手机验证码
     */
    public static final String API_GET_PHONE_CODE = "member/getPhoneCode";
}
