package com.fx.secondbar.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.base.FragmentSupport;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.ActiveBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.DialogSign;
import com.fx.secondbar.ui.MainActivity;
import com.fx.secondbar.ui.home.adapter.AdPerson;
import com.fx.secondbar.ui.order.AcOrderManage;
import com.fx.secondbar.ui.person.AcAccountSet;
import com.fx.secondbar.ui.person.assets.AcAssets;
import com.fx.secondbar.ui.person.assets.AcIncomeRecord;
import com.fx.secondbar.ui.purchase.AcMyPurchase;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

import java.util.List;

import rx.Subscriber;

/**
 * function:个人中心页
 * author: frj
 * modify date: 2018/9/6
 */
public class FragmentPerson extends FragmentSupport
{
    private SelectableRoundedImageView img_avatar;
    private TextView tv_name;
    private TextView tv_account;
    private TextView tv_ste_value;
    private TextView tv_q_value;
    private TextView tv_today_q_value;

    private SelectableRoundedImageView img_get_q;
    private RecyclerView recyclerView;
    private AdPerson adapter;

    public static FragmentPerson newInstance()
    {
        return new FragmentPerson();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_person, container, false);
    }

    @Override
    protected void initView()
    {
        img_avatar = findView(R.id.img_avatar);
        tv_name = findView(R.id.tv_name);
        tv_account = findView(R.id.tv_account);
        tv_ste_value = findView(R.id.tv_ste_value);
        tv_q_value = findView(R.id.tv_q_value);
        tv_today_q_value = findView(R.id.tv_today_q_value);
        recyclerView = findView(R.id.recyclerView);
        img_get_q = findView(R.id.img_get_q);
        findView(R.id.tv_order).setOnClickListener(this);
        findView(R.id.tv_buy).setOnClickListener(this);
        findView(R.id.ib_set).setOnClickListener(this);
        findView(R.id.v_assets).setOnClickListener(this);
        findView(R.id.v_get_q).setOnClickListener(this);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(getContext(), 15), true, false, false));
        adapter = new AdPerson();
        adapter.bindToRecyclerView(recyclerView);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener()
        {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position)
            {
                ActiveBean bean = FragmentPerson.this.adapter.getItem(position);
                //签到
                if (String.valueOf(ActiveBean.TYPE_SIGN).equals(bean.getType()))
                {
                    new DialogSign(getContext()).show();
                } else if (String.valueOf(ActiveBean.TYPE_INVITE).equals(bean.getType()))
                {//邀请好友

                } else if (String.valueOf(ActiveBean.TYPE_SHARE).equals(bean.getType()))
                {//分享
                    //跳转至首页
                    ((MainActivity) getActivity()).jumpToHome();
                } else if (String.valueOf(ActiveBean.TYPE_BROWE).equals(bean.getType()))
                {//看新闻
                    //跳转至首页
                    ((MainActivity) getActivity()).jumpToHome();
                } else if (String.valueOf(ActiveBean.TYPE_OPEN_APP).equals(bean.getType()))
                {//打开App

                } else if (String.valueOf(ActiveBean.TYPE_WEB).equals(bean.getType()))
                {//网页

                }
            }
        });

        int width = DisplayUtil.getScreenSize(BtApplication.getApplication()).widthPixels;
        width -= (BtApplication.getApplication().getResources().getDimensionPixelSize(R.dimen.home_tutorial_plr) * 2);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img_get_q.getLayoutParams();
        if (params.width != width)
        {
            params.width = width;
            //宽高比例为23：8
            params.height = width * 8 / 23;
            img_get_q.setLayoutParams(params);
        }
        setPersonInfo();
        getData(0);

        IntentFilter filter = new IntentFilter(Constants.ACTION_REFRESH_PERSON_SHOW);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
    }

    /**
     * 设置个人信息显示
     */
    private void setPersonInfo()
    {
        GlideApp.with(img_get_q).asBitmap().load(R.mipmap.ic_get_q_img).centerCrop().into(img_get_q);

        GlideLoad.load(img_avatar, FxApplication.getInstance().getUserInfoBean().getImg(), true, R.mipmap.default_avatar, R.mipmap.default_avatar);
        VerificationUtil.setViewValue(tv_name, FxApplication.getInstance().getUserInfoBean().getNickname());
        VerificationUtil.setViewValue(tv_account, FxApplication.getInstance().getUserInfoBean().getAccount(), "请绑定手机号");
        VerificationUtil.setViewValue(tv_ste_value, FxApplication.getInstance().getUserInfoBean().getBalance().toString());
        VerificationUtil.setViewValue(tv_q_value, FxApplication.getInstance().getUserInfoBean().getQcoin().toString());
        VerificationUtil.setViewValue(tv_today_q_value, FxApplication.getInstance().getUserInfoBean().getTodayqcoin().toString());
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

//    private List<QIntroBean> getDatas()
//    {
//        List<QIntroBean> list = new ArrayList<>();
//        list.add(new QIntroBean("充值STE返Q", "每充值100STE返100Q", "去充值", "", ""));
//        list.add(new QIntroBean("签到得Q", "累计签到越多奖励越多", "去签到", "", ""));
//        list.add(new QIntroBean("玩游戏得Q", "玩游戏也能赚Q", "玩游戏", "", ""));
//        list.add(new QIntroBean("邀请好友得Q", "永久20%分成", "邀请好友", "", ""));
//        list.add(new QIntroBean("看新闻赚Q", "每阅读1小时赚100Q", "去瞅瞅", "", ""));
//        list.add(new QIntroBean("看视频奖Q", "每看3分钟奖励10Q", "瞄一下", "", ""));
//        list.add(new QIntroBean("秒吧先锋奖励", "最高奖励8888Q", "去完成", "", ""));
//        list.add(new QIntroBean("Q夺宝", "每日最高获得2888Q", "查看", "", ""));
//        list.add(new QIntroBean("分享微博获得Q", "成功分享一条得10Q", "去分享", "", ""));
//        list.add(new QIntroBean("每日打开App", "随机获得20-100Q", "已完成", "", ""));
//        return list;
//    }

    /**
     * 获取活动数据
     *
     * @param retry
     */
    private void getData(final int retry)
    {
        HttpManager.getActives(new Subscriber<List<ActiveBean>>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                e.printStackTrace();
                if (retry < 3)
                {
                    getData(retry + 1);
                }
            }

            @Override
            public void onNext(List<ActiveBean> activeBeans)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                adapter.setNewData(activeBeans);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ib_set:
                jump(AcAccountSet.class);
                break;
            case R.id.tv_order:
                jump(AcOrderManage.class);
                break;
            case R.id.tv_buy:
                jump(AcMyPurchase.class);
                break;
            case R.id.v_assets:
                jump(AcAssets.class);
                break;
            case R.id.v_get_q:
                jump(AcIncomeRecord.class);
                break;
        }
    }

    @Override
    public void onDestroy()
    {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onDestroy();
    }
}
