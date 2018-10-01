package com.fx.secondbar.ui.purchase;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.MyPurchaseBean;

/**
 * function:订单详情
 * author: frj
 * modify date: 2018/9/21
 */
public class AcPurchaseOrderDetail extends ActivitySupport
{

    private ImageView img_status;
    private TextView tv_status;
    private TextView tv_person_name;
    private TextView tv_mondy;
    private TextView tv_seconds;
    private TextView tv_pay_price;
    private TextView tv_order_num;
    private TextView tv_order_time;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_order_detail;
    }

    @Override
    protected void initView()
    {
        img_status = findView(R.id.img_status);
        tv_status = findView(R.id.tv_status);
        tv_person_name = findView(R.id.tv_person_name);
        tv_mondy = findView(R.id.tv_mondy);
        tv_seconds = findView(R.id.tv_seconds);
        tv_pay_price = findView(R.id.tv_pay_price);
        tv_order_num = findView(R.id.tv_order_num);
        tv_order_time = findView(R.id.tv_order_time);
        findView(R.id.ib_back).setOnClickListener(this);
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
        bindData((MyPurchaseBean) getIntent().getParcelableExtra(KEY));
    }

    /**
     * 绑定数据
     *
     * @param myPurchaseBean
     */
    private void bindData(MyPurchaseBean myPurchaseBean)
    {
        if (myPurchaseBean != null)
        {
            String price = myPurchaseBean.getPrice();
            VerificationUtil.setViewValue(tv_mondy, String.format(getString(R.string.order_detail_order_money), VerificationUtil.verifyDefault(price, "0")));
            VerificationUtil.setViewValue(tv_seconds, String.format(getString(R.string.order_detail_order_seconds), VerificationUtil.verifyDefault(myPurchaseBean.getAmount(), "0")));
            VerificationUtil.setViewValue(tv_pay_price, String.format(getString(R.string.order_detail_order_pay_price), VerificationUtil.verifyDefault(myPurchaseBean.getTotalmoney(), "0")));
            VerificationUtil.setViewValue(tv_order_time, String.format(getString(R.string.order_detail_order_time), VerificationUtil.verifyDefault(myPurchaseBean.getCreatetime(), "0")));

        }
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
