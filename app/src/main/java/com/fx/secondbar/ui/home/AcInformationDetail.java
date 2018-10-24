package com.fx.secondbar.ui.home;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.Arithmetic;
import com.btten.bttenlibrary.util.LogUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.ActiveBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.DeviceUuidFactory;
import com.fx.secondbar.view.IncomeProgressView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;

/**
 * function:资讯详情页面
 * author: frj
 * modify date: 2018/10/1
 */
public class AcInformationDetail extends ActivitySupport
{
    private ProgressBar progressBar1;
    private WebView webView;
    private IncomeProgressView incomeProgressView;
    private LinearLayout ll_income;

    /**
     * 进度阈值，即开始增长该值进度后，将取消增长
     */
    private static final int Threshold = 10;
    //开始进度值
    private int startPercent = 0;

    private ScaleAnimation mShowAction;
    private ScaleAnimation mHiddenAction;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_information_detail;
    }

    @Override
    protected void initView()
    {
        progressBar1 = findView(R.id.progressBar1);
        webView = findView(R.id.webView);
        incomeProgressView = findView(R.id.incomeProgressView);
        ll_income = findView(R.id.ll_income);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        incomeProgressView.setOnProgressChangeListener(new IncomeProgressView.OnProgressChangeListener()
        {
            @Override
            public void onProgress(int progress)
            {
                if (progress == 100)
                {
                    //完成一次收益
                    finishIncomeTask();
//                    ll_income.setAnimation(mShowAction);
                    ll_income.setVisibility(View.VISIBLE);
                    ll_income.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
//                            ll_income.setAnimation(mHiddenAction);
                            ll_income.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
                if (Math.abs(progress - startPercent) < Threshold)
                {
                } else
                {
                    incomeProgressView.stop();
                }
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (MotionEvent.ACTION_DOWN == event.getAction())
                {
                    if (!progressBar1.isShown())
                    {
                        //每次开始时的进度值
                        startPercent = incomeProgressView.getPercent();
                        LogUtil.e("startPercent:" + startPercent);
                        incomeProgressView.start();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData()
    {
        initAnimations();
        //设置起始进度值
        incomeProgressView.setStartProgress(FxApplication.getInstance().getIncomeProgress());
        initWebView();
        String url = String.format(Constants.URL_INFORMATION, getIntent().getStringExtra(KEY_STR), new DeviceUuidFactory(this).getDeviceUuid().toString(), String.valueOf(Math.random()));
        LogUtil.e(url);
        webView.loadUrl(url);
    }

    /**
     * 初始化动画
     */
    private void initAnimations()
    {
        mShowAction = new ScaleAnimation(0f, 1f, 0, 1, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(200);
        mHiddenAction = new ScaleAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(200);
    }


    /**
     * 初始化webview
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView()
    {
        webView.setWebViewClient(client);
        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView webView, int i)
            {
                super.onProgressChanged(webView, i);
                if (i == 100)
                {
                    progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                    incomeProgressView.setVisibility(View.VISIBLE);
                    //每次开始时的进度值
                    startPercent = incomeProgressView.getPercent();
                    LogUtil.e("startPercent:" + startPercent);
                    incomeProgressView.start();
                } else
                {
                    progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar1.setProgress(i);//设置进度值
                    incomeProgressView.setVisibility(View.GONE);
                    incomeProgressView.stop();
                }
            }
        });
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
    }

    private WebViewClient client = new WebViewClient()
    {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return false;
        }


        @Override
        public void onPageFinished(WebView webView, String s)
        {
            super.onPageFinished(webView, s);
        }
    };


    @Override
    protected void onResume()
    {
        super.onResume();
        if (webView != null)
        {
            webView.onResume();
        }
        if (!progressBar1.isShown())
        {
            //每次开始时的进度值
            startPercent = incomeProgressView.getPercent();
            LogUtil.e("startPercent:" + startPercent);
            incomeProgressView.start();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (webView != null)
        {
            webView.onPause();
        }
        incomeProgressView.stop();
    }


    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
        }
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[0];
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[0];
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (KeyEvent.KEYCODE_BACK == keyCode)
        {
            if (webView.canGoBack())
            {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy()
    {
        if (incomeProgressView != null)
        {
            FxApplication.getInstance().setIncomeProgress(incomeProgressView.getPercent());
            incomeProgressView.destroy();
        }
        super.onDestroy();
        if (webView != null)
        {
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setVisibility(View.GONE);
            long timeout = ViewConfiguration.getZoomControlsTimeout();//timeout ==3000
            new Timer().schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            webView.destroy();
                        }
                    });
                }
            }, timeout);
        }
    }

    @Override
    public void finish()
    {
        FxApplication.refreshUserInfoBroadCast();
        super.finish();
    }

    /**
     * 完成收益任务
     */
    private void finishIncomeTask()
    {
        HttpManager.finishActive(String.valueOf(ActiveBean.TYPE_BROWE), new Subscriber<Double>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onNext(Double value)
            {
                FxApplication.refreshUserInfoBroadCast();
                if (value != null)
                {
                    jump(AcIncomeDialog.class, Arithmetic.doubleToStr(value));
                }
            }
        });
    }
}
