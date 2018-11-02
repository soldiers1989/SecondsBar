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
     * 轮播图时间
     */
    public static final int DURATION = 5000;

    /**
     * 资讯详情访问地址
     */
    public static final String URL_INFORMATION = ROOT_URL + "/static/mb-front/news.html?id=%s&deviceid=%s&random=%s";

    /**
     * 分享链接
     */
    public static final String URL_SHARE = ROOT_URL + "/static/mb-front/getAward.html?id=%s&random=%s";

    /**
     * 更新用户数据
     */
    public static final String ACTION_REFRESH_USERINFO = "action_refresh_userinfo";

    /**
     * 更新个人中心显示
     */
    public static final String ACTION_REFRESH_PERSON_SHOW = "action_refresh_person_show";

    /**
     * 分享成功广播Action
     */
    public static final String ACTION_SHARE_SUCCESS = "action_share_success";
    /**
     * 收到推送，发出刷新广播红点提示Action
     */
    public static final String ACTION_REFRESH_NOTIFY_TIPS = "action_refresh_notify_tips";

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
     * 商品Q支付接口
     */
    public static final String API_MALL_Q_BUY = "mall/qbuy";
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
    public static final String API_MY_PURCHASE = "purchase/mylist";
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
     * 申购购买接口
     */
    public static final String API_PURCHASE_BUY = "purchase/buy";

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

    /**
     * 获取收益明细记录
     */
    public static final String API_GET_Q_RECORD = "member/Qcoin/list";
    /**
     * 交易中心数据接口
     */
    public static final String API_TRADING_CENTER = "trading/center";

    /**
     * 充值
     */
    public static final String API_RECHARGE = "member/payment";

    /**
     * 充值记录
     */
    public static final String API_RECHARGE_RECORD = "rechargerecord/list";
    /**
     * 交易中心-当前委托和历史委托接口
     */
    public static final String API_TRADING_CENTER_RECORD = "trading/center/intrust";

    /**
     * 删除银行卡接口
     */
    public static final String API_BANK_DELETE = "member/bank/del";

    /**
     * 消费明细
     */
    public static final String API_CONSUMER_DETAILS = "member/expensesrecord/list";

    /**
     * 提现说明
     */
    public static final String API_WITHDRAW_INTRO = "member/withdraw/explain";

    /**
     * 提现
     */
    public static final String API_WITHDRAW = "member/withdraw";
    /**
     * 提现列表
     */
    public static final String API_WITHDRAW_LIST = "member/withdraw/list";
    /**
     * 提现详情
     */
    public static final String API_WITHDRAW_INFO = "member/withdraw/info";

    /**
     * 每日Q币收益记录
     */
    public static final String API_TODAY_INCOME = "member/qcoin/total";

    /**
     * 实名认证
     */
    public static final String API_NAME_AUTH = "member/auth";

    /**
     * 会员夺宝排行榜
     */
    public static final String API_QCOIN_RANGE = "member/qcoin/rank";

    /**
     * 邀请信息
     */
    public static final String API_INVITE_INFO = "member/invite/info";

    /**
     * 行情已购接口
     */
    public static final String API_TRADING_BUYED = "trading/center/buyed";

    /**
     * 订单申诉
     */
    public static final String API_ORDER_APPEAL = "order/appeal";

    /**
     * 订单履约
     */
    public static final String API_ORDER_PERFORMANCE = "order/performance";

    /**
     * 获取客服信息
     */
    public static final String API_GET_CUSTOMER = "index/customerserver";

    /**
     * 行情-名人搜索接口
     */
    public static final String API_SEARCH_PEOPLE = "people/search";
    /**
     * 商城-商品搜索接口
     */
    public static final String API_SEARCH_MALL = "mall/search";
    /**
     * 交易撤单
     */
    public static final String API_CANCEL_ORDER = "transaction/reback";

    /**
     * 消息列表
     */
    public static final String API_MESSAGE_LIST = "index/messgae";

    /**
     * 资讯搜索
     */
    public static final String API_SEARCH_INFORMATION = "index/news/search";

    /**
     * 帮助、充值说明和交易规则说明接口
     */
    public static final String API_SYSTEM_INTRO = "system/configure";
    /**
     * 约TA列表
     */
    public static final String API_DATE_LIST = "stroke/list";
    /**
     * 约TA详情
     */
    public static final String API_DATE_DETAIL = "stroke/info";
    /**
     * 约TA购买
     */
    public static final String API_DATE_BUY = "stroke/buy";
    /**
     * 约TA购买，Q支付
     */
    public static final String API_DATE_BUY_Q = "stroke/qbuy";

    /**
     * 持仓市值明细
     */
    public static final String API_MARKET_VALUE = "member/market/total";

    /**
     * Html字符串拼接成完整的Html文本
     */
    public static final String START_TAGS = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\"><meta http-equlv=\"Content-Type\" content=\"text/html;charset=utf-8\"></head><body>";
    /**
     * 结束标签
     */
    public static final String END_TAGS = "</body></html>";

}
