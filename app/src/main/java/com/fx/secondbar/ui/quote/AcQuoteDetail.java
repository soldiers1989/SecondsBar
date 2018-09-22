package com.fx.secondbar.ui.quote;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.glide.GlideApp;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.DialogShare;
import com.joooonho.SelectableRoundedImageView;

import me.next.tagview.TagCloudView;

/**
 * function:申购详情
 * author: frj
 * modify date: 2018/9/21
 */
public class AcQuoteDetail extends ActivitySupport
{

    private ImageView img_top;
    private SelectableRoundedImageView img_avatar;
    private TextView tv_person_name;
    private TextView tv_person_price;
    private TextView tv_person_position;
    private TextView tv_person_school;
    private TextView tv_use;
    private TagCloudView tagCloudView;
    private TextView tv_experience;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_quote_detail;
    }

    @Override
    protected void initView()
    {
        img_top = findView(R.id.img_top);
        img_avatar = findView(R.id.img_avatar);
        tv_person_name = findView(R.id.tv_person_name);
        tv_person_price = findView(R.id.tv_person_price);
        tv_person_position = findView(R.id.tv_person_position);
        tv_person_school = findView(R.id.tv_person_school);
        tv_use = findView(R.id.tv_use);
        tagCloudView = findView(R.id.tagCloudView);
        tv_experience = findView(R.id.tv_experience);
        findView(R.id.ib_share).setOnClickListener(this);
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.btn_buy).setOnClickListener(this);
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
        GlideApp.with(img_avatar).asBitmap().load(R.mipmap.test_person_item1).centerCrop().into(img_avatar);
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
            case R.id.ib_share:
                new DialogShare(this).show();
                break;
            case R.id.btn_buy:
                jump(AcQuoteBuyConfirm.class);
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
