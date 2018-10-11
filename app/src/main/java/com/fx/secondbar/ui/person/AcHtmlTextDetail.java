package com.fx.secondbar.ui.person;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.util.Constants;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * function:显示Html文本
 * author: frj
 * modify date: 2018/10/11
 */
public class AcHtmlTextDetail extends ActivitySupport
{
    private TextView tv_title;
    private WebView webView;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_html_text_detail;
    }

    @Override
    protected void initView()
    {
        tv_title = findView(R.id.tv_title);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        findView(R.id.webView);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        initWebView();
        tv_title.setText(getIntent().getStringExtra(KEY_STR));
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.START_TAGS);
        sb.append(VerificationUtil.verifyDefault(getIntent().getStringExtra(KEY), ""));
        sb.append(Constants.END_TAGS);
        webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);
    }

    /**
     * 初始化webview
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView()
    {
        webView.setWebViewClient(client);
        webView.setDownloadListener(new DownloadListener()
        {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l)
            {

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
}
