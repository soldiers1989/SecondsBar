package com.fx.secondbar.ui.purchase;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.MyPurchaseBean;

/**
 * function:我的申购列表
 * author: frj
 * modify date: 2018/9/21
 */
public class AdPurchase extends BaseQuickAdapter<MyPurchaseBean, BaseViewHolder>
{

    public AdPurchase()
    {
        super(R.layout.ad_purchase);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPurchaseBean item)
    {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_seconds = helper.getView(R.id.tv_seconds);
        TextView tv_status = helper.getView(R.id.tv_status);
        calItemSize(tv_name, tv_price, tv_seconds, tv_status);
        if (helper.getLayoutPosition() % 2 == 0)
        {
            tv_status.setTextColor(Color.parseColor("#03c086"));
        } else
        {
            tv_status.setTextColor(Color.parseColor("#e76e43"));
        }
        String name = item.getPeoplename();
        if (!TextUtils.isEmpty(item.getZjm()))
        {
            name += "(" + item.getZjm() + ")";
        }
        VerificationUtil.setViewValue(tv_name, name);
        VerificationUtil.setViewValue(tv_seconds, item.getAmount());
//        VerificationUtil.setViewValue(tv_status, item.getStatusname());
        setCommodityMoney(tv_price, item.getPrice());
    }

    /**
     * 设置商品价格
     *
     * @param tv
     * @param price
     */
    private void setCommodityMoney(TextView tv, String price)
    {
        if (tv != null)
        {
            String money = tv.getContext().getString(R.string.mall_detail_info_price);
            tv.setText(String.format(money, VerificationUtil.verifyDefault(price, "0")));
        }
    }

    /**
     * 计算项尺寸
     *
     * @param tvName
     * @param tvPrice
     * @param tvSeconds
     * @param tvStatus
     */
    private void calItemSize(TextView tvName, TextView tvPrice, TextView tvSeconds, TextView tvStatus)
    {
        if (tvName == null || tvPrice == null || tvSeconds == null || tvStatus == null)
        {
            return;
        }

        //比例为：1.5:1.5:1:1
        int width = DisplayUtil.getScreenSize(tvName.getContext()).widthPixels;
        width -= tvName.getContext().getResources().getDimensionPixelSize(R.dimen.mall_detail_info_plr) * 2;
        int oneWidth = width / 5;
        int bigWidth = (width - oneWidth * 2) / 2;
        LinearLayout.LayoutParams paramName = (LinearLayout.LayoutParams) tvName.getLayoutParams();
        if (paramName.width != bigWidth)
        {
            paramName.width = bigWidth;
            tvName.setLayoutParams(paramName);
        }
        LinearLayout.LayoutParams paramPrice = (LinearLayout.LayoutParams) tvPrice.getLayoutParams();
        if (paramPrice.width != bigWidth)
        {
            paramPrice.width = bigWidth;
            tvPrice.setLayoutParams(paramPrice);
        }
        LinearLayout.LayoutParams paramSeconds = (LinearLayout.LayoutParams) tvSeconds.getLayoutParams();
        if (paramSeconds.width != oneWidth)
        {
            paramSeconds.width = oneWidth;
            tvSeconds.setLayoutParams(paramSeconds);
        }
        LinearLayout.LayoutParams paramStatus = (LinearLayout.LayoutParams) tvStatus.getLayoutParams();
        if (paramStatus.width != oneWidth)
        {
            paramStatus.width = oneWidth;
            tvStatus.setLayoutParams(paramSeconds);
        }
    }
}
