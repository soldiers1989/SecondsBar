package com.fx.secondbar.http.service;


import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.IndexTimeBean;
import com.fx.secondbar.bean.OrderBean;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.bean.PurchaseBean;
import com.fx.secondbar.bean.ResMall;
import com.fx.secondbar.bean.TurialBean;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.bean.WBBean;
import com.fx.secondbar.util.Constants;

import java.util.List;

import retrofit2.http.GET;
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
    Observable<ResponseBean<List<PurchaseBean>>> getMyPurchase(@Query("page") int page, @Query("count") int pageSize);

}
