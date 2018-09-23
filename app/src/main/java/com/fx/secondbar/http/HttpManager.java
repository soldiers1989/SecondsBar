package com.fx.secondbar.http;

import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.http.HttpGetData;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.ActiveBean;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.IndexTimeBean;
import com.fx.secondbar.bean.OrderBean;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.bean.MyPurchaseBean;
import com.fx.secondbar.bean.ResConfigInfo;
import com.fx.secondbar.bean.ResMall;
import com.fx.secondbar.bean.ResQuote;
import com.fx.secondbar.bean.TurialBean;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.bean.WBBean;
import com.fx.secondbar.http.exception.ApiException;
import com.fx.secondbar.http.service.IService;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * function:网络请求管理
 * author: frj
 * modify date: 2017/6/26
 */

public class HttpManager
{

    private static HttpManager mManager;

    private IService mService;

    private HttpManager()
    {
        mService = HttpGetData.getRetrofit().create(IService.class);
    }

    /**
     * 获取当前类的唯一对象
     *
     * @return
     */
    public static HttpManager getInstance()
    {
        /*
            双重校验，保证多线程时，也唯一单例
         */
        if (mManager == null)
        {
            synchronized (HttpManager.class)
            {
                if (mManager == null)
                {
                    mManager = new HttpManager();
                }
            }
        }
        return mManager;
    }

    /**
     * 登录
     *
     * @param subscriber
     */
    public static void login(Subscriber<UserInfoBean> subscriber)
    {
        Observable<UserInfoBean> observable = getInstance().mService.login().map(new HttpResultFun<UserInfoBean>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 退出登录接口
     *
     * @param subscriber
     */
    public static void logout(Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.login().map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取首页资讯列表
     *
     * @param type       类型
     * @param subscriber
     */
    public static void getIndexInformation(int page, int pageSize, String type, Subscriber<IndexInformationBean> subscriber)
    {
        Observable<IndexInformationBean> observable = getInstance().mService.getIndexInformation(page, pageSize, type).map(new HttpResultFun<IndexInformationBean>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取首页时间场信息
     *
     * @param subscriber
     */
    public static void getIndexTime(Subscriber<IndexTimeBean> subscriber)
    {
        Observable<IndexTimeBean> observable = getInstance().mService.getIndexTime().map(new HttpResultFun<IndexTimeBean>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取微博列表
     *
     * @param page
     * @param pageSize
     * @param subscriber
     */
    public static void getWbs(int page, int pageSize, Subscriber<List<WBBean>> subscriber)
    {
        Observable<List<WBBean>> observable = getInstance().mService.getWbs(page, pageSize).map(new HttpResultFun<List<WBBean>>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取教程列表
     *
     * @param page
     * @param pageSize
     * @param subscriber
     */
    public static void getTurials(int page, int pageSize, Subscriber<List<TurialBean>> subscriber)
    {
        Observable<List<TurialBean>> observable = getInstance().mService.getTurials(page, pageSize).map(new HttpResultFun<List<TurialBean>>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取行情详情
     *
     * @param peopleId
     * @param subscriber
     */
    public static void getQuoteDetail(String peopleId, Subscriber<PersonBean> subscriber)
    {
        Observable<PersonBean> observable = getInstance().mService.getQuoteDetail(peopleId).map(new HttpResultFun<PersonBean>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取商品列表
     *
     * @param page
     * @param pageSize
     * @param categoryId 栏目id，值为空表示全部
     * @param subscriber
     */
    public static void getMall(int page, int pageSize, String categoryId, Subscriber<ResMall> subscriber)
    {
        Observable<ResMall> observable = getInstance().mService.getMall(page, pageSize, categoryId).map(new HttpResultFun<ResMall>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取商品详情
     *
     * @param goodsId    商品id
     * @param subscriber
     */
    public static void getMallDetail(String goodsId, Subscriber<CommodityBean> subscriber)
    {
        Observable<CommodityBean> observable = getInstance().mService.getMallDetail(goodsId).map(new HttpResultFun<CommodityBean>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 购买商品接口
     *
     * @param goodsId    商品id
     * @param subscriber
     */
    public static void buyCommodity(String goodsId, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.commodityBuy(goodsId).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取行情列表
     *
     * @param page
     * @param pageSize
     * @param categoryId 栏目id
     * @param subscriber
     */
    public static void getQuoteLis(int page, int pageSize, String categoryId, Subscriber<ResQuote> subscriber)
    {
        Observable<ResQuote> observable = getInstance().mService.getQuoteList(page, pageSize, categoryId).map(new HttpResultFun<ResQuote>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取订单列表
     *
     * @param page
     * @param pageSize
     * @param status     订单状态值
     * @param subscriber
     */
    public static void getOrderList(int page, int pageSize, int status, Subscriber<List<OrderBean>> subscriber)
    {
        Observable<List<OrderBean>> observable = getInstance().mService.getOrderList(page, pageSize, status).map(new HttpResultFun<List<OrderBean>>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取我的申购
     *
     * @param page
     * @param pageSize
     * @param subscriber
     */
    public static void getMyPurchase(int page, int pageSize, Subscriber<List<MyPurchaseBean>> subscriber)
    {
        Observable<List<MyPurchaseBean>> observable = getInstance().mService.getMyPurchase(page, pageSize).map(new HttpResultFun<List<MyPurchaseBean>>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取活动列表
     *
     * @param subscriber
     */
    public static void getActives(Subscriber<List<ActiveBean>> subscriber)
    {
        Observable<List<ActiveBean>> observable = getInstance().mService.getActives().map(new HttpResultFun<List<ActiveBean>>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 完成活动
     *
     * @param type
     * @param subscriber
     */
    public static void finishActive(String type, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.finishActive(type).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 添加自选
     *
     * @param peopleId   名人id
     * @param subscriber
     */
    public static void addCustomPerson(String peopleId, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.addCustomPerson(peopleId).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 删除自选
     *
     * @param peopleId   名人id
     * @param subscriber
     */
    public static void removeCustomPerson(String peopleId, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.removeCustomPerson(peopleId).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取配置信息
     *
     * @param subscriber
     */
    public static void getConfigInfo(Subscriber<ResConfigInfo> subscriber)
    {
        Observable<ResConfigInfo> observable = getInstance().mService.getConfigInfo().map(new HttpResultFun<ResConfigInfo>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 绑定订购者
     *
     * @param observable 可被观察者
     * @param subscriber 订购者
     */
    private void bindSubscriber(Observable observable, Subscriber subscriber)
    {
        if (observable == null || subscriber == null)
        {
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 检查异常原因
     *
     * @param t
     * @return
     */
    private String checkFail(Throwable t)
    {
        if (t instanceof SocketTimeoutException)
        {
            return FxApplication.getStr(HttpMsgTips.TIMEOUT_TIPS);
        } else if (t instanceof IllegalStateException || t instanceof JsonSyntaxException || t instanceof MalformedJsonException)
        {
            return FxApplication.getStr(HttpMsgTips.DATA_ERROR_TIPS);
        } else if (t instanceof HttpException)
        {
            HttpException httpException = (HttpException) t;
            //表示网络禁用或缓存不可用时
            if (httpException.code() == 504)
            {
                return FxApplication.getStr(HttpMsgTips.DEFAULT_MSG);
            } else
            {
                return FxApplication.getStr(HttpMsgTips.DATA_ERROR_TIPS);
            }
        } else if (t instanceof ApiException)
        {
            ApiException apiException = (ApiException) t;
            return apiException.getErrorMsg();
        } else
        {
            return FxApplication.getStr(HttpMsgTips.DEFAULT_MSG);
        }
    }

    /**
     * 检查加载失败错误原因
     *
     * @param t
     * @return
     */
    public static String checkLoadError(Throwable t)
    {
        return getInstance().checkFail(t);
    }

    /**
     * 返回结果预处理
     *
     * @param <T>
     */
    private static class HttpResultFun<T> implements Func1<ResponseBean<T>, T>
    {

        @Override
        public T call(ResponseBean<T> tResponseBean)
        {
            if (!tResponseBean.checkSuccess())
            {
                throw new ApiException(String.valueOf(tResponseBean.getCode()), tResponseBean.getMsg());
            }
            if (tResponseBean.getData() == null)
            {
                throw new ApiException(String.valueOf(tResponseBean.getCode()), FxApplication.getStr(HttpMsgTips.DATA_ERROR_TIPS));
            }
            return tResponseBean.getData();
        }
    }

    /**
     * 返回结果无Data时的处理
     *
     * @param <T>
     */
    private static class HttpNoDataResultFun<T extends ResponseBean> implements Func1<T, T>
    {

        @Override
        public T call(T t)
        {
            ResponseBean responseBean = t;
            if (!responseBean.checkSuccess())
            {
                throw new ApiException(String.valueOf(responseBean.getCode()), responseBean.getMsg());
            }
            return t;
        }
    }

    /**
     * 非通用返回结果
     *
     * @param <T>
     */
    private static class HttpNonGenericResultFun<T> implements Func1<T, T>
    {

        @Override
        public T call(T t)
        {
            return t;
        }
    }

}
