package com.fx.secondbar.ui.purchase;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.util.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;

/**
 * function:我的申购列表
 * author: frj
 * modify date: 2018/9/21
 */
public class AdPurchase extends BaseQuickAdapter<String, BaseViewHolder>
{

    public AdPurchase()
    {
        super(R.layout.ad_purchase);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
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
