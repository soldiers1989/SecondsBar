package com.fx.secondbar.ui.person.aboutus;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.person.AcOnlineCustomer;

/**
 * function:关于我们
 * author: frj
 * modify date: 2018/9/21
 */
public class AcAboutUs extends ActivitySupport
{
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
                bundle.putInt(KEY, AcProtocol.TYPE_USER);
                jump(AcProtocol.class, bundle, false);
                break;
            case R.id.tv_privacy:
                Bundle bundlePrivacy = new Bundle();
                bundlePrivacy.putString(KEY_STR, ((TextView) v).getText().toString());
                bundlePrivacy.putInt(KEY, AcProtocol.TYPE_PRIVACY);
                jump(AcProtocol.class, bundlePrivacy, false);
                break;
            case R.id.tv_customer:
//                jump(AcCustomer.class);
                jump(AcOnlineCustomer.class);
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
