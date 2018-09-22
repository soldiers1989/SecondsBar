package com.fx.secondbar.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.base.FragmentSupport;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.QIntroBean;
import com.fx.secondbar.ui.home.adapter.AdPerson;
import com.fx.secondbar.ui.order.AcOrderManage;
import com.fx.secondbar.ui.person.AcAccountSet;
import com.fx.secondbar.ui.person.assets.AcAssets;
import com.fx.secondbar.ui.person.assets.AcIncomeRecord;
import com.fx.secondbar.ui.purchase.AcMyPurchase;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * function:个人中心页
 * author: frj
 * modify date: 2018/9/6
 */
public class FragmentPerson extends FragmentSupport
{
    private SelectableRoundedImageView img_avatar;
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
        adapter.setNewData(getDatas());
        adapter.bindToRecyclerView(recyclerView);

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
        GlideApp.with(img_get_q).asBitmap().load(R.mipmap.ic_get_q_img).centerCrop().into(img_get_q);
        GlideApp.with(img_avatar).asBitmap().load(R.mipmap.test_dynamic_1).centerCrop().into(img_avatar);
    }

    private List<QIntroBean> getDatas()
    {
        List<QIntroBean> list = new ArrayList<>();
        list.add(new QIntroBean("充值STE返Q", "每充值100STE返100Q", "去充值", "", ""));
        list.add(new QIntroBean("签到得Q", "累计签到越多奖励越多", "去签到", "", ""));
        list.add(new QIntroBean("玩游戏得Q", "玩游戏也能赚Q", "玩游戏", "", ""));
        list.add(new QIntroBean("邀请好友得Q", "永久20%分成", "邀请好友", "", ""));
        list.add(new QIntroBean("看新闻赚Q", "每阅读1小时赚100Q", "去瞅瞅", "", ""));
        list.add(new QIntroBean("看视频奖Q", "每看3分钟奖励10Q", "瞄一下", "", ""));
        list.add(new QIntroBean("秒吧先锋奖励", "最高奖励8888Q", "去完成", "", ""));
        list.add(new QIntroBean("Q夺宝", "每日最高获得2888Q", "查看", "", ""));
        list.add(new QIntroBean("分享微博获得Q", "成功分享一条得10Q", "去分享", "", ""));
        list.add(new QIntroBean("每日打开App", "随机获得20-100Q", "已完成", "", ""));
        return list;
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

}
