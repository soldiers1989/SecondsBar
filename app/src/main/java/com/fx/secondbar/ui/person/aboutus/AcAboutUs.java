package com.fx.secondbar.ui.person.aboutus;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.AcWebBrowse;
import com.fx.secondbar.ui.person.AcOnlineCustomer;
import com.tencent.bugly.beta.Beta;

/**
 * function:关于我们
 * author: frj
 * modify date: 2018/9/21
 */
public class AcAboutUs extends ActivitySupport
{

    private TextView tv_version;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_about_us;
    }

    @Override
    protected void initView()
    {
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.tv_protocol).setOnClickListener(this);
        findView(R.id.tv_privacy).setOnClickListener(this);
        findView(R.id.tv_customer).setOnClickListener(this);
        findView(R.id.tv_evaluation).setOnClickListener(this);
        tv_version = findView(R.id.tv_version);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        tv_version.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        VerificationUtil.setViewValue(tv_version, getVersion(this));
    }

    /**
     * 获取版本名称
     *
     * @return 当前应用的版本号
     * @throws PackageManager.NameNotFoundException
     */
    String getVersion(Context context)
    {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try
        {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return "1.0";
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
            case R.id.tv_protocol:
                Bundle bundle = new Bundle();
                bundle.putString(KEY_STR, ((TextView) v).getText().toString());
//                bundle.putInt(KEY, AcProtocol.TYPE_USER);
//                jump(AcProtocol.class, bundle, false);
                bundle.putString(KEY, "http://www.feixingtech.com:8080/static/mb-front/getText.html?type=2");
                jump(AcWebBrowse.class, bundle, false);
                break;
            case R.id.tv_privacy:
                Bundle bundlePrivacy = new Bundle();
                bundlePrivacy.putString(KEY_STR, ((TextView) v).getText().toString());
//                bundlePrivacy.putInt(KEY, AcProtocol.TYPE_PRIVACY);
                bundlePrivacy.putString(KEY, "http://www.feixingtech.com:8080/static/mb-front/getText.html?type=1");
//                jump(AcProtocol.class, bundlePrivacy, false);
                jump(AcWebBrowse.class, bundlePrivacy, false);
                break;
            case R.id.tv_customer:
//                jump(AcCustomer.class);
                jump(AcOnlineCustomer.class);
                break;
            case R.id.tv_evaluation:
                ShowToast.showToast("敬请期待");
                break;
            case R.id.tv_version:
                //手动检查更新
                Beta.checkUpgrade();
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
}
