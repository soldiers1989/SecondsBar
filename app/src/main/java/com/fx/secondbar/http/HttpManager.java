package com.fx.secondbar.http;

import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.http.HttpGetData;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.IndexTimeBean;
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
    public static void getIndexInformation(String type, Subscriber<IndexInformationBean> subscriber)
    {
        Observable<IndexInformationBean> observable = getInstance().mService.getIndexInformation(type).map(new HttpResultFun<IndexInformationBean>());
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
