package com.fx.secondbar.http.service;


import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.fx.secondbar.bean.BankBean;
import com.fx.secondbar.bean.CommissionBean;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.ConsumerBean;
import com.fx.secondbar.bean.CustomerBean;
import com.fx.secondbar.bean.DateBean;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.IndexTimeBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.bean.InviteInfoBean;
import com.fx.secondbar.bean.LevelBean;
import com.fx.secondbar.bean.MarketValueBean;
import com.fx.secondbar.bean.MessageBean;
import com.fx.secondbar.bean.MineData;
import com.fx.secondbar.bean.MyPurchaseBean;
import com.fx.secondbar.bean.OrderBean;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.bean.PurchaseDetailBean;
import com.fx.secondbar.bean.QCoinBean;
import com.fx.secondbar.bean.QCoinRangeBean;
import com.fx.secondbar.bean.QuoteBean;
import com.fx.secondbar.bean.RechargeRecordBean;
import com.fx.secondbar.bean.ResConfigInfo;
import com.fx.secondbar.bean.ResMall;
import com.fx.secondbar.bean.ResQuote;
import com.fx.secondbar.bean.SigninBean;
import com.fx.secondbar.bean.SystemIntroBean;
import com.fx.secondbar.bean.TodayIncomeBean;
import com.fx.secondbar.bean.TradingBuyedBean;
import com.fx.secondbar.bean.TransactionBean;
import com.fx.secondbar.bean.TurialBean;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.bean.WBData;
import com.fx.secondbar.bean.WithdrawIntroBean;
import com.fx.secondbar.bean.WithdrawRecordBean;
import com.fx.secondbar.util.Constants;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * function:Http接口定义
 * author: frj
 * modify date: 2017/6/26
 */

public interface IService
{

    /**
     * 登录
     *
     * @return
     */
    @GET(Constants.API_LOGIN)
    Observable<ResponseBean<UserInfoBean>> login();

    /**
     * 退出登录
     *
     * @return
     */
    @GET(Constants.API_LOGOUT)
    Observable<ResponseBean> logout();

    /**
     * 首页-获取资讯信息实体
     *
     * @param type 资讯栏目
     * @return
     */
    @GET(Constants.API_INDEX_INFORMATION)
    Observable<ResponseBean<IndexInformationBean>> getIndexInformation(@Query("page") int page, @Query("count") int pageSize, @Query("cid") String type);

    /**
     * 首页-获取时间场信息
     *
     * @return
     */
    @GET(Constants.API_INDEX_TIMER)
    Observable<ResponseBean<IndexTimeBean>> getIndexTime();

    /**
     * 获取微博信息
     *
     * @param page     页码，从0开始
     * @param pageSize 页数据条目
     * @return
     */
    @GET(Constants.API_INDEX_WB)
    Observable<ResponseBean<WBData>> getWbs(@Query("page") int page, @Query("count") int pageSize);

    /**
     * 获取教程信息
     *
     * @param page     页码，从0开始
     * @param pageSize 页数据条目
     * @return
     */
    @GET(Constants.API_INDEX_TURIAL)
    Observable<ResponseBean<List<TurialBean>>> getTurials(@Query("page") int page, @Query("count") int pageSize);

    /**
     * 获取申购详情
     *
     * @param peopleId 名人id
     * @return
     */
    @GET(Constants.API_QUOTE_DETAIL)
    Observable<ResponseBean<PersonBean>> getQuoteDetail(@Query("peopleid") String peopleId);

    /**
     * 获取商城列表接口
     *
     * @param page
     * @param pageSize
     * @param categoryid
     * @return
     */
    @GET(Constants.API_MALL_LIST)
    Observable<ResponseBean<ResMall>> getMall(@Query("page") int page, @Query("count") int pageSize, @Query("categoryid") String categoryid);

    /**
     * 获取商品信息接口
     *
     * @param id 商品id
     * @return
     */
    @GET(Constants.API_MALL_INFO)
    Observable<ResponseBean<CommodityBean>> getMallDetail(@Query("goodsid") String id);

    /**
     * 购买商品接口
     *
     * @param id 商品id
     * @return
     */
    @GET(Constants.API_MALL_BUY)
    Observable<ResponseBean> commodityBuy(@Query("goodsid") String id);

    /**
     * 购买商品Q支付接口
     *
     * @param id
     * @return
     */
    @GET(Constants.API_MALL_Q_BUY)
    Observable<ResponseBean> commodityQBuy(@Query("goodsid") String id);

    /**
     * 获取行情列表
     *
     * @param page
     * @param pageSize
     * @param categoryId 栏目id
     * @return
     */
    @GET(Constants.API_QUOTE_LIST)
    Observable<ResponseBean<ResQuote>> getQuoteList(@Query("page") int page, @Query("count") int pageSize, @Query("categoryid") String categoryId);

    /**
     * 获取订单列表
     *
     * @param page
     * @param pageSize
     * @param status   订单状态值
     * @return
     */
    @GET(Constants.API_ORDER_LIST)
    Observable<ResponseBean<List<OrderBean>>> getOrderList(@Query("page") int page, @Query("count") int pageSize, @Query("status") int status);

    /**
     * 获取我的申购列表接口
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET(Constants.API_MY_PURCHASE)
    Observable<ResponseBean<List<MyPurchaseBean>>> getMyPurchase(@Query("page") int page, @Query("count") int pageSize);

    /**
     * 获取Q币活动列表
     *
     * @return
     */
    @GET(Constants.API_GET_ACTIVITY_Q)
    Observable<ResponseBean<MineData>> getActives();

    /**
     * 完成活动
     *
     * @param type @{ActiveBean.TYPE_}
     * @return
     */
    @GET(Constants.API_FINISH_ACTIVITY)
    Observable<ResponseBean<Double>> finishActive(@Query("type") String type);

    /**
     * 添加自选
     *
     * @param peopleId 名人id
     * @return
     */
    @GET(Constants.API_QUOTE_ADD_CUSTOM)
    Observable<ResponseBean> addCustomPerson(@Query("peopleid") String peopleId);

    /**
     * 删除自选
     *
     * @param peopleId 名人id
     * @return
     */
    @GET(Constants.API_QUOTE_REMOVE_CUSTOME)
    Observable<ResponseBean> removeCustomPerson(@Query("peopleid") String peopleId);

    /**
     * 获取配置信息
     *
     * @return
     */
    @GET(Constants.API_GET_CATEGORY)
    Observable<ResponseBean<ResConfigInfo>> getConfigInfo();

    /**
     * 上传头像
     *
     * @return
     */
    @POST(Constants.API_UPLOAD_AVATAR)
    Observable<ResponseBean> uploadAvatar(@Body RequestBody body);

    /**
     * 签到
     *
     * @return
     */
    @GET(Constants.API_SIGNIN)
    Observable<ResponseBean<SigninBean>> signin();

    /**
     * 设置昵称
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.API_SET_NICKNAME)
    Observable<ResponseBean> setNickName(@Field("nickname") String nickName);

    /**
     * 获取我的银行卡列表
     *
     * @return
     */
    @GET(Constants.API_MY_BANK_LIST)
    Observable<ResponseBean<List<BankBean>>> getMyBank();

    /**
     * 添加银行卡号
     *
     * @param bankNo   银行卡号
     * @param bankName 银行名称
     * @param address  开户行地址
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.API_ADD_BANK)
    Observable<ResponseBean> addBank(@Field("bankno") String bankNo, @Field("bankname") String bankName, @Field("address") String address);

    /**
     * 绑定手机号
     *
     * @param phone 手机号
     * @param code  短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.API_BIND_PHONE)
    Observable<ResponseBean> bindPhone(@Field("phoneno") String phone, @Field("code") String code);

    /**
     * 设置支付密码
     *
     * @param pwd
     * @param code 短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.API_SET_PAYPWD)
    Observable<ResponseBean> setPayPwd(@Field("paymentpassword") String pwd, @Field("phoneno") String phone, @Field("code") String code);

    /**
     * 修改支付密码
     *
     * @param pwd
     * @param code 短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.API_UPDATE_PAYPWD)
    Observable<ResponseBean> updatePayPwd(@Field("paymentpassword") String pwd, @Field("phoneno") String phone, @Field("code") String code);

    /**
     * 获取等级列表
     *
     * @return
     */
    @GET(Constants.API_LEVEL_LIST)
    Observable<ResponseBean<List<LevelBean>>> getLevelList();

    /**
     * 获取申购详情
     *
     * @param id
     * @return
     */
    @GET(Constants.API_PURCHASE_DETAIL)
    Observable<ResponseBean<PurchaseDetailBean>> getPurchaseDetail(@Query("purchaseid") String id);

    /**
     * 申购购买接口
     *
     * @param purchaseId 申购Id
     * @param seconds    秒数
     * @return
     */
    @GET(Constants.API_PURCHASE_BUY)
    Observable<ResponseBean> purchaseBuy(@Query("purchaseid") String purchaseId, @Query("seconds") String seconds);

    /**
     * 获取手机验证码
     *
     * @param phone 手机号
     * @return
     */
    @GET(Constants.API_GET_PHONE_CODE)
    Observable<ResponseBean> getPhoneCode(@Query("phoneno") String phone);

    /**
     * 名人时间购买
     *
     * @param seconds  秒数
     * @param price    价格
     * @param peopleId 名人id值
     * @return
     */
    @GET(Constants.API_TRANSACTION_BUY)
    Observable<ResponseBean> buyTransaction(@Query("seconds") String seconds, @Query("price") String price, @Query("peopleid") String peopleId);

    /**
     * 名人时间出售
     *
     * @param seconds  秒数
     * @param price    价格
     * @param peopleId 名人id值
     * @return
     */
    @GET(Constants.API_TRANSACTION_SALE)
    Observable<ResponseBean> saleTransaction(@Query("seconds") String seconds, @Query("price") String price, @Query("peopleid") String peopleId);

    /**
     * 获取Q收益明细
     *
     * @param page     页码
     * @param pageSize 页大小
     * @return
     */
    @GET(Constants.API_GET_Q_RECORD)
    Observable<ResponseBean<List<QCoinBean>>> getQRecords(@Query("page") int page, @Query("count") int pageSize);

    /**
     * 获取交易中心数据
     *
     * @param peopleId 名人id
     * @return
     */
    @GET(Constants.API_TRADING_CENTER)
    Observable<ResponseBean<TransactionBean>> getTransactionCenter(@Query("peopleid") String peopleId);

    /**
     * 获取支付宝充值字符串
     *
     * @param money 金额
     * @return
     */
    @POST(Constants.API_RECHARGE)
    Observable<ResponseBean<String>> getRechargeOrderInfo(@Query("money") String money);

    /**
     * 获取充值记录
     *
     * @param page     页码
     * @param pageSize 页大小
     * @return
     */
    @GET(Constants.API_RECHARGE_RECORD)
    Observable<ResponseBean<List<RechargeRecordBean>>> getRechargeRecord(@Query("page") int page, @Query("count") int pageSize);


    /**
     * 获取交易中心委托列表
     *
     * @param page
     * @param pageSize
     * @param type     1表示当前委托，2表示历史委托
     * @return
     */
    @GET(Constants.API_TRADING_CENTER_RECORD)
    Observable<ResponseBean<List<CommissionBean>>> getTradingCommission(@Query("page") int page, @Query("count") int pageSize, @Query("type") String type);

    /**
     * 删除银行卡
     *
     * @param bankId 银行卡id
     * @return
     */
    @GET(Constants.API_BANK_DELETE)
    Observable<ResponseBean> deleteBank(@Query("bankid") String bankId);

    /**
     * 获取消费明细
     *
     * @param page
     * @param pageSize
     * @param type     0表示全部
     *                 0	全部
     *                 1	充值
     *                 2	提现
     *                 3	购买名人商品
     *                 4	购买名人时间
     *                 5	卖出名人时间
     *                 6	购买名人申购
     * @return
     */
    @GET(Constants.API_CONSUMER_DETAILS)
    Observable<ResponseBean<List<ConsumerBean>>> getConsumerDetail(@Query("page") int page, @Query("count") int pageSize, @Query("type") String type);

    /**
     * 获取提现说明
     *
     * @return
     */
    @GET(Constants.API_WITHDRAW_INTRO)
    Observable<ResponseBean<WithdrawIntroBean>> getWithdrawIntro();

    /**
     * 提现
     *
     * @param amount 提现STE数量
     * @param bankno 银行卡号
     * @return
     */
    @POST(Constants.API_WITHDRAW)
    Observable<ResponseBean> withdraw(@Query("amount") String amount, @Query("bankno") String bankno);

    /**
     * 获取今日阅读收益说明
     *
     * @param type 4表示阅读收益
     * @return
     */
    @GET(Constants.API_TODAY_INCOME)
    Observable<ResponseBean<TodayIncomeBean>> getDayIncomeIntro(@Query("type") String type);

    /**
     * 实名认证
     *
     * @param idcard 身份证号
     * @param name   姓名
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.API_NAME_AUTH)
    Observable<ResponseBean> verifyName(@Field("idcard") String idcard, @Field("actualname") String name);

    /**
     * 获取Q夺宝排行
     */
    @GET(Constants.API_QCOIN_RANGE)
    Observable<ResponseBean<QCoinRangeBean>> getQcoinRank();

    /**
     * 获取邀请信息
     *
     * @return
     */
    @GET(Constants.API_INVITE_INFO)
    Observable<ResponseBean<InviteInfoBean>> getInviteInfo();

    /**
     * 获取交易中心已购数据
     *
     * @return
     */
    @GET(Constants.API_TRADING_BUYED)
    Observable<ResponseBean<TradingBuyedBean>> getBuyed(@Query("page") int page, @Query("count") int pageSize);

    /**
     * 订单申诉
     *
     * @param orderId 订单id
     * @return
     */
    @GET(Constants.API_ORDER_APPEAL)
    Observable<ResponseBean> orderAppeal(@Query("orderid") String orderId);

    /**
     * 订单履约
     *
     * @param orderId 订单id
     * @return
     */
    @GET(Constants.API_ORDER_PERFORMANCE)
    Observable<ResponseBean> orderPerformance(@Query("orderid") String orderId);

    /**
     * 获取客服信息
     *
     * @return
     */
    @GET(Constants.API_GET_CUSTOMER)
    Observable<ResponseBean<List<CustomerBean>>> getCustomerInfo();

    /**
     * 搜索行情名人
     *
     * @param name     关键字
     * @param page     页码
     * @param pageSize 页大小
     * @return
     */
    @GET(Constants.API_SEARCH_PEOPLE)
    Observable<ResponseBean<List<QuoteBean>>> searchQuotePerson(@Query("name") String name, @Query("page") int page, @Query("count") int pageSize);

    /**
     * 商城-商品搜索
     *
     * @param name     关键字
     * @param page     页码
     * @param pageSize 页大小
     * @return
     */
    @GET(Constants.API_SEARCH_MALL)
    Observable<ResponseBean<List<CommodityBean>>> searchMall(@Query("name") String name, @Query("page") int page, @Query("count") int pageSize);

    /**
     * 交易订单id
     *
     * @param id 订单id
     * @return
     */
    @GET(Constants.API_CANCEL_ORDER)
    Observable<ResponseBean> cancelOrder(@Query("transactionid") String id);

    /**
     * 获取消息列表
     *
     * @param type     1表示系统消息；2表示公告消息
     * @param page     页码
     * @param pageSize 页大小
     * @return
     */
    @GET(Constants.API_MESSAGE_LIST)
    Observable<ResponseBean<List<MessageBean>>> getMessageList(@Query("type") String type, @Query("page") int page, @Query("count") int pageSize);

    /**
     * 资讯搜索
     *
     * @param name     关键字
     * @param page     页码
     * @param pageSize 页大小
     * @return
     */
    @GET(Constants.API_SEARCH_INFORMATION)
    Observable<ResponseBean<List<InfomationBean>>> searchInformation(@Query("name") String name, @Query("page") int page, @Query("count") int pageSize);

    /**
     * 获取帮助、充值说明和交易规则说明接口
     *
     * @param type     2：帮助；3：交易规则；4：充值说明
     * @param page
     * @param pageSize
     * @return
     */
    @GET(Constants.API_SYSTEM_INTRO)
    Observable<ResponseBean<List<SystemIntroBean>>> getSystemIntro(@Query("type") String type, @Query("page") int page, @Query("count") int pageSize);

    /**
     * 获取约TA列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET(Constants.API_DATE_LIST)
    Observable<ResponseBean<List<DateBean>>> getDateList(@Query("page") int page, @Query("count") int pageSize);

    /**
     * 获取约TA详情
     *
     * @param id 约TA id
     * @return
     */
    @GET(Constants.API_DATE_DETAIL)
    Observable<ResponseBean<DateBean>> getDateDetail(@Query("strokeid") String id);

    /**
     * 约TA购买
     *
     * @param id 约TA id
     * @return
     */
    @GET(Constants.API_DATE_BUY)
    Observable<ResponseBean> buyDate(@Query("strokeid") String id);

    /**
     * 约TA购买
     *
     * @param id 约TA id
     * @return
     */
    @GET(Constants.API_DATE_BUY_Q)
    Observable<ResponseBean> buyQDate(@Query("strokeid") String id);

    /**
     * 获取市值明细记录
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET(Constants.API_MARKET_VALUE)
    Observable<ResponseBean<List<MarketValueBean>>> getMarketDetail(@Query("page") int page, @Query("count") int pageSize);

    /**
     * 获取提现记录列表
     *
     * @param page     页码
     * @param pageSize 页大小
     * @param status   //1	待审核
     *                 //2	汇款中
     *                 //3	提现成功
     *                 //4	被驳回
     *                 //0	全部
     * @return
     */
    @GET(Constants.API_WITHDRAW_RECORD)
    Observable<ResponseBean<List<WithdrawRecordBean>>> getWithdrawRecord(@Query("page") int page, @Query("count") int pageSize, @Query("status") String status);
}
