package com.fx.secondbar.ui;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.LogUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * function:
 * author: frj
 * modify date: 2018/9/24
 */
public class AcWebBrowse extends ActivitySupport
{
    private TextView tv_title;
    private ProgressBar progressBar1;
    private WebView webView;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_web_browse;
    }

    @Override
    protected void initView()
    {
        tv_title = findView(R.id.tv_title);
        progressBar1 = findView(R.id.progressBar1);
        webView = findView(R.id.webView);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        initWebView();
        VerificationUtil.setViewValue(tv_title, getIntent().getStringExtra(KEY_STR));
        String url = getIntent().getStringExtra(KEY);
        LogUtil.e(url);
        webView.loadUrl(url);
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
                } else
                {
                    progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar1.setProgress(i);//设置进度值
                }
            }
        });
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
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
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (webView != null)
        {
            webView.onPause();
        }
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
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
}
