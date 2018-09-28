package com.fx.secondbar.ui.quote;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.DeviceUuidFactory;
import com.fx.secondbar.util.ProgressDialogUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;

/**
 * function:名人详情
 * author: frj
 * modify date: 2018/9/21
 */
public class AcQuoteDetail extends ActivitySupport
{

    private static final String URL = Constants.ROOT_URL + "/static/mb-front/market.html?id=%s&deviceid=%s";

    /**
     * 跳转至交易中心界面
     */
    public static final int RESULT_CODE_TRANSACTION = 10;

    private TextView tv_title;
    private ProgressBar progressBar1;
    private WebView webView;
    private Button btn_add_custom;

    private ProgressDialog progressDialog;

    //判断是否自选，1表示自选
    private int type;
    //名人id值
    private String peopleId;
    //判断回到上个页面之后是否需要刷新数据
    private boolean isNeedRefresh = false;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_quote_detail;
    }

    @Override
    protected void initView()
    {
        tv_title = findView(R.id.tv_title);
        progressBar1 = findView(R.id.progressBar1);
        webView = findView(R.id.webView);
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.btn_transaction).setOnClickListener(this);
        btn_add_custom = findView(R.id.btn_add_custom);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_add_custom.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        initWebView();
        progressDialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
        VerificationUtil.setViewValue(tv_title, getIntent().getStringExtra(KEY_STR));

        peopleId = getIntent().getStringExtra("id");


        webView.loadUrl(String.format(URL, peopleId, new DeviceUuidFactory(this).getDeviceUuid()));

        //获取是否自选值
        type = getIntent().getIntExtra(KEY, 0);
        //1表示自选
        if (1 == type)
        {
            btn_add_custom.setText("删除自选");
        } else
        {
            btn_add_custom.setText("添加自选");
        }
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

    /**
     * 添加自选
     *
     * @param peopleId
     */
    private void addCustom(String peopleId)
    {
        if (progressDialog != null)
        {
            progressDialog.show();
        }
        HttpManager.addCustomPerson(peopleId, new Subscriber<ResponseBean>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isDestroy())
                {
                    return;
                }
                e.printStackTrace();
                if (progressDialog != null)
                {
                    progressDialog.dismiss();
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(ResponseBean responseBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (progressDialog != null)
                {
                    progressDialog.dismiss();
                }
                ShowToast.showToast("添加成功");
                btn_add_custom.setText("删除自选");
                type = 1;
                isNeedRefresh = true;
            }
        });
    }

    /**
     * 删除自选
     *
     * @param peopleId
     */
    private void removeCustom(String peopleId)
    {
        if (progressDialog != null)
        {
            progressDialog.show();
        }
        HttpManager.addCustomPerson(peopleId, new Subscriber<ResponseBean>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isDestroy())
                {
                    return;
                }
                e.printStackTrace();
                if (progressDialog != null)
                {
                    progressDialog.dismiss();
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(ResponseBean responseBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (progressDialog != null)
                {
                    progressDialog.dismiss();
                }
                ShowToast.showToast("删除成功");
                btn_add_custom.setText("添加自选");
                type = 0;
                isNeedRefresh = true;
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ib_back:
                if (isNeedRefresh)
                {
                    setResult(RESULT_OK);
                }
                finish();
                break;

            case R.id.btn_transaction://交易中心
                Intent intent = new Intent();
                intent.putExtra(KEY, peopleId);
                intent.putExtra(KEY_STR, getTextView(tv_title));
                setResult(RESULT_CODE_TRANSACTION, intent);
                finish();
                break;
            case R.id.btn_add_custom://添加自选
                if (type == 1)
                {
                    removeCustom(peopleId);
                } else
                {
                    addCustom(peopleId);
                }
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
            } else
            {
                if (isNeedRefresh)
                {
                    setResult(RESULT_OK);
                }
                finish();
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
}
