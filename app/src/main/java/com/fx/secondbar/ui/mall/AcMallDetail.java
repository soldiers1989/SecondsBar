package com.fx.secondbar.ui.mall;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.DialogShare;

/**
 * function:商城详情页
 * author: frj
 * modify date: 2018/9/9
 */
public class AcMallDetail extends ActivitySupport {

    private Toolbar toolbar;
    private ImageButton img_back;
    private ImageButton img_forward;
    private Button btn_buy;
    private ImageView img;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_place;
    private TextView tv_price;
    private TextView tv_intro;


    @Override
    protected int getLayoutResId() {
        return R.layout.ac_mall_detail;
    }

    @Override
    protected void initView() {
        toolbar = findView(R.id.toolbar);
        img_back = findView(R.id.img_back);
        img_forward = findView(R.id.img_forward);
        btn_buy = findView(R.id.btn_buy);
        img = findView(R.id.img);
        tv_title = findView(R.id.tv_title);
        tv_time = findView(R.id.tv_time);
        tv_place = findView(R.id.tv_place);
        tv_price = findView(R.id.tv_price);
        tv_intro = findView(R.id.tv_intro);
    }

    @Override
    protected void initListener() {
        img_back.setOnClickListener(this);
        img_forward.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
        params.width = DisplayUtil.getScreenSize(this).widthPixels;
        //宽高比为30:17
        params.height = params.width * 17 / 30;
        img.setLayoutParams(params);
        GlideApp.with(img).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536518822428&di=db154d34662ee841cac7fc1277e15820&imgtype=0&src=http%3A%2F%2Fpic.xiudodo.com%2Ffigure%2F00%2F00%2F33%2F16%2F73%2F1655bda6abbcd26.jpg").centerCrop().into(img);
    }

    @Override
    protected String[] getPermissionArrays() {
        return new String[0];
    }

    @Override
    protected int[] getPermissionInfoTips() {
        return new int[0];
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.img_forward:
                new DialogShare(this).show();
                break;
            case R.id.btn_buy:
                new DialogBuy(this).show();
                break;
        }
    }
}
