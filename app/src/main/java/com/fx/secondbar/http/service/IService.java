package com.fx.secondbar.http.service;


import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.IndexTimeBean;
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
    Observable<ResponseBean<IndexInformationBean>> getIndexInformation(@Query("category_ID") String type);

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
}
