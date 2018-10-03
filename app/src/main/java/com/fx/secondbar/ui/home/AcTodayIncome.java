package com.fx.secondbar.ui.home;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.TodayIncomeBean;
import com.fx.secondbar.http.HttpManager;

import rx.Subscriber;

/**
 * function:今日阅读收益说明
 * author: frj
 * modify date: 2018/10/3
 */
public class AcTodayIncome extends ActivitySupport
{
    private TextView tv_today_details;
    private TextView tv_yesterday_details;
    private TextView tv_content;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_today_income;
    }

    @Override
    protected void initView()
    {
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_today_details = findView(R.id.tv_today_details);
        tv_yesterday_details = findView(R.id.tv_yesterday_details);
        tv_content = findView(R.id.tv_content);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        getData();
    }

    /**
     * 获取数据
     */
    private void getData()
    {
        HttpManager.getTodayReadIncome(new Subscriber<TodayIncomeBean>()
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
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(TodayIncomeBean todayIncomeBean)
            {
                if (isDestroy())
                {
                    return;
                }
                StringBuilder sbToday = new StringBuilder();
                sbToday.append("阅读时长");
                sbToday.append("<font color='#ffb528'>");
                sbToday.append(todayIncomeBean.getToDayQcoinTotal().getTotaltimes());
                sbToday.append("分钟</font>");
                sbToday.append("，获得");
                sbToday.append("<font color='#ffb528'>");
                sbToday.append(todayIncomeBean.getToDayQcoinTotal().getQcointotals());
                sbToday.append("Q");
                sbToday.append("</font>");
                tv_today_details.setText(Html.fromHtml(sbToday.toString()));


                StringBuilder sbYesterday = new StringBuilder();
                sbYesterday.append("阅读时长");
                sbYesterday.append("<font color='#ffb528'>");
                sbYesterday.append(todayIncomeBean.getYesterDayQcoinTotal().getTotaltimes());
                sbYesterday.append("分钟</font>");
                sbYesterday.append("，获得");
                sbYesterday.append("<font color='#ffb528'>");
                sbYesterday.append(todayIncomeBean.getYesterDayQcoinTotal().getQcointotals());
                sbYesterday.append("Q");
                sbYesterday.append("</font>");
                tv_yesterday_details.setText(Html.fromHtml(sbYesterday.toString()));

                tv_content.setText(VerificationUtil.verifyDefault(todayIncomeBean.getDescription(), "暂无说明"));
            }
        });
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
}
