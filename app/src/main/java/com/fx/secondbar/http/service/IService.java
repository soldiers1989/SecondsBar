package com.fx.secondbar.http.service;


import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.fx.secondbar.bean.ActiveBean;
import com.fx.secondbar.bean.BankBean;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.IndexTimeBean;
import com.fx.secondbar.bean.LevelBean;
import com.fx.secondbar.bean.MyPurchaseBean;
import com.fx.secondbar.bean.OrderBean;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.bean.PurchaseDetailBean;
import com.fx.secondbar.bean.QCoinBean;
import com.fx.secondbar.bean.RechargeRecordBean;
import com.fx.secondbar.bean.ResConfigInfo;
import com.fx.secondbar.bean.ResMall;
import com.fx.secondbar.bean.ResQuote;
import com.fx.secondbar.bean.SigninBean;
import com.fx.secondbar.bean.TransactionBean;
import com.fx.secondbar.bean.TurialBean;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.bean.WBBean;
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
    Observable<ResponseBean<List<WBBean>>> getWbs(@Query("page") int page, @Query("count") int pageSize);

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
    Observable<ResponseBean<List<ActiveBean>>> getActives();

    /**
     * 完成活动
     *
     * @param type @{ActiveBean.TYPE_}
     * @return
     */
    @GET(Constants.API_FINISH_ACTIVITY)
    Observable<ResponseBean> finishActive(@Query("type") String type);

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
    @FormUrlEncoded
    @POST(Constants.API_LEVEL_LIST)
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
     * @param seconds 秒数
     * @param price   价格
     * @return
     */
    @GET(Constants.API_TRANSACTION_BUY)
    Observable<ResponseBean> buyTransaction(@Query("seconds") String seconds, @Query("price") String price);

    /**
     * 名人时间出售
     *
     * @param seconds 秒数
     * @param price   价格
     * @return
     */
    @GET(Constants.API_TRANSACTION_SALE)
    Observable<ResponseBean> saleTransaction(@Query("seconds") String seconds, @Query("price") String price);

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
}
