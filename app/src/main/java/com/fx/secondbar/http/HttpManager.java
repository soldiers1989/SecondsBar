package com.fx.secondbar.http;

import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.http.HttpGetData;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.ActiveBean;
import com.fx.secondbar.bean.BankBean;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.IndexTimeBean;
import com.fx.secondbar.bean.LevelBean;
import com.fx.secondbar.bean.OrderBean;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.bean.MyPurchaseBean;
import com.fx.secondbar.bean.PurchaseDetailBean;
import com.fx.secondbar.bean.QCoinBean;
import com.fx.secondbar.bean.ResConfigInfo;
import com.fx.secondbar.bean.ResMall;
import com.fx.secondbar.bean.ResQuote;
import com.fx.secondbar.bean.SigninBean;
import com.fx.secondbar.bean.TransactionBean;
import com.fx.secondbar.bean.TurialBean;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.bean.WBBean;
import com.fx.secondbar.http.exception.ApiException;
import com.fx.secondbar.http.service.IService;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        Observable<ResponseBean> observable = getInstance().mService.logout().map(new HttpNoDataResultFun<>());
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
     * 上传头像
     *
     * @param file       头像文件
     * @param subscriber
     */
    public static void uploadAvatar(File file, Subscriber<ResponseBean> subscriber)
    {
//        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        if (file == null)
        {
            return;
        }
        RequestBody requestBody = null;
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM);
        build.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        requestBody = build.build();
        Observable<ResponseBean> observable = getInstance().mService.uploadAvatar(requestBody).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 签到
     *
     * @param subscriber
     */
    public static void signin(Subscriber<SigninBean> subscriber)
    {
        Observable<SigninBean> observable = getInstance().mService.signin().map(new HttpResultFun<SigninBean>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 设置昵称
     *
     * @param nickName   昵称
     * @param subscriber
     */
    public static void setNickName(String nickName, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.setNickName(nickName).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取我的银行卡
     *
     * @param subscriber
     */
    public static void getMyBank(Subscriber<List<BankBean>> subscriber)
    {
        Observable<List<BankBean>> observable = getInstance().mService.getMyBank().map(new HttpResultFun<List<BankBean>>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 添加银行卡
     *
     * @param bankNo     银行卡号
     * @param bankName   银行名称
     * @param address    开户行
     * @param subscriber
     */
    public static void addBank(String bankNo, String bankName, String address, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.addBank(bankNo, bankName, address).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 绑定手机号
     *
     * @param phone      手机号
     * @param code       短信验证码
     * @param subscriber
     */
    public static void bindPhone(String phone, String code, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.bindPhone(phone, code).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 设置6位支付密码
     *
     * @param pwd        密码
     * @param subscriber
     */
    public static void setPayPwd(String pwd, String phone, String code, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.setPayPwd(pwd, phone, code).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 修改6位支付密码
     *
     * @param pwd        密码
     * @param subscriber
     */
    public static void updatePayPwd(String pwd, String phone, String code, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.updatePayPwd(pwd, phone, code).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取等级列表
     *
     * @param subscriber
     */
    public static void getLevelList(Subscriber<List<LevelBean>> subscriber)
    {
        Observable<List<LevelBean>> observable = getInstance().mService.getLevelList().map(new HttpResultFun<List<LevelBean>>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取申购详情
     *
     * @param id
     * @param subscriber
     */
    public static void getPurchaseDetail(String id, Subscriber<PurchaseDetailBean> subscriber)
    {
        Observable<PurchaseDetailBean> observable = getInstance().mService.getPurchaseDetail(id).map(new HttpResultFun<PurchaseDetailBean>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 申购购买接口
     *
     * @param purchaseId 申购id
     * @param seconds    秒数
     * @param subscriber
     */
    public static void purchaseBuy(String purchaseId, String seconds, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.purchaseBuy(purchaseId, seconds).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取短信验证码
     *
     * @param phone      手机号
     * @param subscriber
     */
    public static void getPhoneCode(String phone, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.getPhoneCode(phone).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 购买名人时间
     *
     * @param price      价格
     * @param seconds    秒数
     * @param subscriber
     */
    public static void buyTransaction(String price, String seconds, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.buyTransaction(seconds, price).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 卖出名人时间
     *
     * @param price      价格
     * @param seconds    秒数
     * @param subscriber
     */
    public static void saleTransaction(String price, String seconds, Subscriber<ResponseBean> subscriber)
    {
        Observable<ResponseBean> observable = getInstance().mService.saleTransaction(seconds, price).map(new HttpNoDataResultFun<>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取Q的收益记录
     *
     * @param page       页码
     * @param pageSize   页大小
     * @param subscriber
     */
    public static void getQRecords(int page, int pageSize, Subscriber<List<QCoinBean>> subscriber)
    {
        Observable<List<QCoinBean>> observable = getInstance().mService.getQRecords(page, pageSize).map(new HttpResultFun<List<QCoinBean>>());
        getInstance().bindSubscriber(observable, subscriber);
    }

    /**
     * 获取交易中心数据
     *
     * @param peopleId   名人id
     * @param subscriber
     */
    public static void getTransactionCenter(String peopleId, Subscriber<TransactionBean> subscriber)
    {
        Observable<TransactionBean> observable = getInstance().mService.getTransactionCenter(peopleId).map(new HttpResultFun<TransactionBean>());
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
