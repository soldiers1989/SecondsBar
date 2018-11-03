package com.fx.secondbar.ui.person.assets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.AcWebBrowse;
import com.fx.secondbar.util.Constants;

import rx.Subscriber;

/**
 * function:我的资产
 * author: frj
 * modify date: 2018/9/21
 */
public class AcAssets extends ActivitySupport
{

    private TextView tv_balance;
    private TextView tv_q_value;
    private TextView tv_q_intro;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_assets;
    }

    @Override
    protected void initView()
    {
        tv_balance = findView(R.id.tv_balance);
        tv_q_value = findView(R.id.tv_q_value);
        tv_q_intro = findView(R.id.tv_q_intro);
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.tv_detail).setOnClickListener(this);
        findView(R.id.tv_recharge).setOnClickListener(this);
        findView(R.id.tv_withdraw).setOnClickListener(this);
        findView(R.id.ll_conversion).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        tv_q_intro.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        IntentFilter filter = new IntentFilter(Constants.ACTION_REFRESH_PERSON_SHOW);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
        setPersonInfo();
        tv_q_intro.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        login();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent != null)
            {
                if (Constants.ACTION_REFRESH_PERSON_SHOW.equals(intent.getAction()))
                {
                    setPersonInfo();
                }
            }
        }
    };

    /**
     * 更新信息
     */
    private void setPersonInfo()
    {
        VerificationUtil.setViewValue(tv_balance, FxApplication.getInstance().getUserInfoBean().getBalance().toString());
        VerificationUtil.setViewValue(tv_q_value, FxApplication.getInstance().getUserInfoBean().getQcoin().toString());
    }

    /**
     * 更新用户信息
     */
    private void login()
    {
        HttpManager.login(new Subscriber<UserInfoBean>()
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
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(UserInfoBean userInfoBean)
            {
                if (isDestroy())
                {
                    return;
                }
                FxApplication.getInstance().setUserInfoBean(userInfoBean);
                FxApplication.refreshPersonShowBroadCast();
            }
        });
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
            case R.id.tv_detail:
                jump(AcAssetDetail.class);
                break;
            case R.id.tv_recharge:
                jump(AcRecharge.class);
                break;
            case R.id.tv_withdraw:
                jump(AcWithdraw.class);
                break;
            case R.id.ll_conversion:
                ShowToast.showToast("暂未开放");
                break;
            case R.id.tv_q_intro:
//                jump(AcQIntro.class);
                Bundle bundle = new Bundle();
                bundle.putString(KEY_STR, "Q说明");
                bundle.putString(KEY, "http://www.feixingtech.com:8080/static/mb-front/getText.html?type=17");
                jump(AcWebBrowse.class, bundle, false);
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
    public void onDestroy()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

}
