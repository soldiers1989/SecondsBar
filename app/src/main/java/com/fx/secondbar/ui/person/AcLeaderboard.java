package com.fx.secondbar.ui.person;

import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.QCoinRangeBean;
import com.fx.secondbar.bean.RangeBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.GlideLoad;
import com.fx.secondbar.util.ProgressDialogUtil;
import com.joooonho.SelectableRoundedImageView;

import rx.Subscriber;

/**
 * function:排行榜界面
 * author: frj
 * modify date: 2018/10/3
 */
public class AcLeaderboard extends ActivitySupport
{

    private TextView tv_reward_intro;   //奖励规则
    private TextView tv_today_tab;      //今日收益排行榜
    private TextView tv_yesterday_tab;  //昨日收益排行榜
    private RecyclerView recyclerViewToday; //今日收益排行榜列表
    private RecyclerView recyclerViewYesterday; //昨日收益排行榜列表
    private SelectableRoundedImageView img_avatar;  //当前用户头像
    private TextView tv_nickname;                   //当前用户昵称
    private TextView tv_invite_friends;             //邀请好友
    private TextView tv_today_income;               //今日收益
    private TextView tv_today_range;                //今日排行

    private ProgressDialog dialog;

    private AdLeaderBoard adToday;
    private AdLeaderBoard adYesterday;

    private RangeBean todayRange;
    private RangeBean yesterdayRange;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_leader_board;
    }

    @Override
    protected void initView()
    {
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_reward_intro = findView(R.id.tv_reward_intro);
        tv_today_tab = findView(R.id.tv_today_tab);
        tv_yesterday_tab = findView(R.id.tv_yesterday_tab);
        recyclerViewToday = findView(R.id.recyclerViewToday);
        recyclerViewYesterday = findView(R.id.recyclerViewYesterday);
        img_avatar = findView(R.id.img_avatar);
        tv_nickname = findView(R.id.tv_nickname);
        tv_invite_friends = findView(R.id.tv_invite_friends);
        tv_today_income = findView(R.id.tv_today_income);
        tv_today_range = findView(R.id.tv_today_range);
    }

    @Override
    protected void initListener()
    {
        tv_today_tab.setOnClickListener(this);
        tv_yesterday_tab.setOnClickListener(this);
        tv_invite_friends.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        switchTab(true);
        recyclerViewToday.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewYesterday.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewToday.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 10), false, false, true));
        recyclerViewYesterday.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 10), false, false, true));
        adToday = new AdLeaderBoard();
        adYesterday = new AdLeaderBoard();

        adToday.bindToRecyclerView(recyclerViewToday);
        adYesterday.bindToRecyclerView(recyclerViewYesterday);

        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);

        GlideLoad.load(img_avatar, FxApplication.getInstance().getUserInfoBean().getImg(), true, R.mipmap.default_avatar, R.mipmap.default_avatar);
        VerificationUtil.setViewValue(tv_nickname, FxApplication.getInstance().getUserInfoBean().getNickname());
        getData();
    }

    /**
     * 切换Tab
     *
     * @param toToday 表示是否切换至今日
     */
    private void switchTab(boolean toToday)
    {
        if (toToday)
        {
            tv_today_tab.setSelected(true);
            tv_yesterday_tab.setSelected(false);
            recyclerViewToday.setVisibility(View.VISIBLE);
            recyclerViewYesterday.setVisibility(View.GONE);
        } else
        {
            tv_today_tab.setSelected(false);
            tv_yesterday_tab.setSelected(true);
            recyclerViewToday.setVisibility(View.GONE);
            recyclerViewYesterday.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取数据
     */
    private void getData()
    {
        if (dialog != null && !dialog.isShowing())
        {
            dialog.show();
        }
        HttpManager.getCoinRank(new Subscriber<QCoinRangeBean>()
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
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                e.printStackTrace();

            }

            @Override
            public void onNext(QCoinRangeBean qCoinRangeBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                adToday.setNewData(qCoinRangeBean.getListToDayQcoinRank());
                adYesterday.setNewData(qCoinRangeBean.getListYesterDayQcoinRank());
                todayRange = qCoinRangeBean.getToDayQcoinRank();
                yesterdayRange = qCoinRangeBean.getYesterDayQcoinRank();

                if (todayRange != null)
                {
                    VerificationUtil.setViewValue(tv_today_income, "今日收益：" + todayRange.getAmt() + "Q");
                    VerificationUtil.setViewValue(tv_today_range, "今日排行：" + todayRange.getLevel());
                }
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
                finish();
                break;
            case R.id.tv_today_tab:
                switchTab(true);
                if (todayRange != null)
                {
                    VerificationUtil.setViewValue(tv_today_income, "今日收益：" + todayRange.getAmt() + "Q");
                    VerificationUtil.setViewValue(tv_today_range, "今日排行：" + todayRange.getLevel());
                }
                break;
            case R.id.tv_yesterday_tab:
                switchTab(false);
                if (yesterdayRange != null)
                {
                    VerificationUtil.setViewValue(tv_today_income, "昨日收益：" + yesterdayRange.getAmt() + "Q");
                    VerificationUtil.setViewValue(tv_today_range, "昨日排行：" + yesterdayRange.getLevel());
                }
                break;
            case R.id.tv_invite_friends:
                jump(AcInviteFriends.class);
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
