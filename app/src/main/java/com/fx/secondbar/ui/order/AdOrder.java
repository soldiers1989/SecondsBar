package com.fx.secondbar.ui.order;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.joooonho.SelectableRoundedImageView;

/**
 * function:订单列表适配器
 * author: frj
 * modify date: 2018/9/21
 */
public class AdOrder extends BaseQuickAdapter<String, BaseViewHolder>
{
    public AdOrder()
    {
        super(R.layout.ad_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_place = helper.getView(R.id.tv_place);
        TextView tv_option = helper.getView(R.id.tv_option);
    }
}
