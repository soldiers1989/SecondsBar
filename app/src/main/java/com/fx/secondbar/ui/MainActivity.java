package com.fx.secondbar.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.FragmentSupport;
import com.btten.bttenlibrary.util.LogUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.FragmentHome;
import com.fx.secondbar.ui.home.FragmentMall;
import com.fx.secondbar.ui.home.FragmentPerson;
import com.fx.secondbar.ui.home.FragmentQuotes;
import com.fx.secondbar.ui.home.FragmentTransaction;
import com.fx.secondbar.util.Constants;

import rx.Subscriber;

public class MainActivity extends ActivitySupport
{

    /**
     * 首页页索引
     */
    private static final int INDEX_HOME = 0;
    /**
     * 商城页索引
     */
    private static final int INDEX_MALL = 1;

    /**
     * 行情索引
     */
    private static final int INDEX_QUOTES = 2;

    /**
     * 交易索引
     */
    private static final int INDEX_TRANSACTION = 3;

    /**
     * 我的索引
     */
    private static final int INDEX_PERSON = 4;

    /**
     * 项数量
     */
    private static final int SIZE = 5;

    /**
     * 无效值
     */
    private static final int INVALID = -1;

    private TextView tv_home;           //首页
    private TextView tv_mall;           //商城
    private TextView tv_quotes;         //行情
    private TextView tv_transaction;    //交易
    private TextView tv_person;         //个人中心

    private TextView[] textViews;           //文本集合
    private FragmentSupport[] fragments;    //Fragment页面集合

    //当前选中索引
    private int currIndex = INVALID;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initView()
    {
        tv_home = findView(R.id.tv_home);
        tv_mall = findView(R.id.tv_mall);
        tv_quotes = findView(R.id.tv_quotes);
        tv_transaction = findView(R.id.tv_transaction);
        tv_person = findView(R.id.tv_person);
    }

    @Override
    protected void initListener()
    {
        tv_home.setOnClickListener(this);
        tv_mall.setOnClickListener(this);
        tv_quotes.setOnClickListener(this);
        tv_transaction.setOnClickListener(this);
        tv_person.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        initFragments();
        initItems();
        switchItem(tv_home);
        login(0);

        IntentFilter filter = new IntentFilter();
        //刷新用户信息
        filter.addAction(Constants.ACTION_REFRESH_USERINFO);
        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent == null)
            {
                return;
            }
            if (Constants.ACTION_REFRESH_USERINFO.equals(intent.getAction()))
            {
                login(0);
            }
        }
    };

    /**
     * 初始化Fragment集合
     */
    private void initFragments()
    {
        fragments = new FragmentSupport[SIZE];

        Bundle savedInstanceState = getSavedInstanceState();
        //判断是否是恢复的Activity，如果是，那么根据Tag取出对应Fragment，并判断Fragment是否为空，如果为空，那么重新创建
        if (savedInstanceState != null)
        {
            fragments[INDEX_HOME] = (FragmentSupport) getSupportFragmentManager().findFragmentByTag(String.valueOf(INDEX_HOME));
            fragments[INDEX_MALL] = (FragmentSupport) getSupportFragmentManager().findFragmentByTag(String.valueOf(INDEX_MALL));
            fragments[INDEX_QUOTES] = (FragmentSupport) getSupportFragmentManager().findFragmentByTag(String.valueOf(INDEX_QUOTES));
            fragments[INDEX_TRANSACTION] = (FragmentSupport) getSupportFragmentManager().findFragmentByTag(String.valueOf(INDEX_TRANSACTION));
            fragments[INDEX_PERSON] = (FragmentSupport) getSupportFragmentManager().findFragmentByTag(String.valueOf(INDEX_PERSON));
            if (fragments[INDEX_HOME] == null)
            {
                fragments[INDEX_HOME] = FragmentHome.newInstance();
            }
            if (fragments[INDEX_MALL] == null)
            {
                fragments[INDEX_MALL] = FragmentMall.newInstance();
            }
            if (fragments[INDEX_QUOTES] == null)
            {
                fragments[INDEX_QUOTES] = FragmentQuotes.newInstance();
            }
            if (fragments[INDEX_TRANSACTION] == null)
            {
                fragments[INDEX_TRANSACTION] = FragmentTransaction.newInstance();
            }
            if (fragments[INDEX_PERSON] == null)
            {
                fragments[INDEX_PERSON] = FragmentPerson.newInstance();
            }
        } else
        {
            fragments[INDEX_HOME] = FragmentHome.newInstance();
            fragments[INDEX_MALL] = FragmentMall.newInstance();
            fragments[INDEX_QUOTES] = FragmentQuotes.newInstance();
            fragments[INDEX_TRANSACTION] = FragmentTransaction.newInstance();
            fragments[INDEX_PERSON] = FragmentPerson.newInstance();
        }
    }

    /**
     * 初始化切换项
     */
    private void initItems()
    {
        tv_home.setTag(INDEX_HOME);
        tv_mall.setTag(INDEX_MALL);
        tv_quotes.setTag(INDEX_QUOTES);
        tv_transaction.setTag(INDEX_TRANSACTION);
        tv_person.setTag(INDEX_PERSON);

        textViews = new TextView[SIZE];
        textViews[INDEX_HOME] = tv_home;
        textViews[INDEX_MALL] = tv_mall;
        textViews[INDEX_QUOTES] = tv_quotes;
        textViews[INDEX_TRANSACTION] = tv_transaction;
        textViews[INDEX_PERSON] = tv_person;
    }

    /**
     * 切换显示
     *
     * @param view
     */
    private void switchItem(View view)
    {
        if (view == null)
        {
            return;
        }
        try
        {
            int index = (int) view.getTag();
            if (currIndex == index)
            {
                return;
            }
            if (textViews != null)
            {
                if (INVALID != currIndex)
                {
                    textViews[currIndex].setSelected(false);
                }
                textViews[index].setSelected(true);
                for (int i = 0; i < SIZE; i++)
                {
                    if (i != index && i != currIndex)
                    {
                        textViews[i].setSelected(false);
                    }
                }
            }
            switchFragment(index, currIndex);
            currIndex = index;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 切换Fragment
     *
     * @param targetIndex 目标Fragment索引
     * @param currIndex   当前Fragment索引
     */
    private void switchFragment(int targetIndex, int currIndex)
    {
        try
        {
            if (fragments != null)
            {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments[targetIndex].isAdded())
                {
                    transaction.show(fragments[targetIndex]);
                } else
                {
                    transaction.add(R.id.fl_content, fragments[targetIndex], String.valueOf(targetIndex));
                }
                if (currIndex != INVALID)
                {
                    transaction.hide(fragments[currIndex]);
                }
                transaction.commit();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 登录
     *
     * @param retry 重试次数
     */
    private void login(final int retry)
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
                LogUtil.e("api_login", e.toString());
                if (retry < 3)
                {
                    login(retry + 1);
                }
            }

            @Override
            public void onNext(UserInfoBean userInfoBean)
            {
                FxApplication.getInstance().setUserInfoBean(userInfoBean);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_home:
            case R.id.tv_mall:
            case R.id.tv_quotes:
            case R.id.tv_transaction:
            case R.id.tv_person:
                switchItem(v);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[]{R.string.permission_write_external_storage_tips};
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
