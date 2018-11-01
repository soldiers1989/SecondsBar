package com.fx.secondbar.ui.order;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.OrderBean;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

/**
 * function:订单列表适配器
 * author: frj
 * modify date: 2018/9/21
 */
public class AdOrder extends BaseQuickAdapter<OrderBean, BaseViewHolder>
{
    public AdOrder()
    {
        super(R.layout.ad_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_place = helper.getView(R.id.tv_place);
        TextView tv_option = helper.getView(R.id.tv_option);
        View v_line = helper.getView(R.id.v_line);
        LinearLayout ll_option = helper.getView(R.id.ll_option);
        TextView tv_option_refund = helper.getView(R.id.tv_option_refund);

        GlideLoad.load(img, item.getImg(), true);
        VerificationUtil.setViewValue(tv_status, item.getStatusname());
        VerificationUtil.setViewValue(tv_name, item.getName());

        //type=1表示STE支付
        setCommodityMoney(tv_price, item.getType() == 1 ? item.getPrice() : item.getQcoin(), item.getType() == 1);
        setCommodityPlace(tv_place, item.getAddress());
        setCommodityTime(tv_time, item.getTimelength());

        if (FOrderItem.TYPE_PERFORMANCING == item.getStatus().intValue())
        {   //履约中
            v_line.setVisibility(View.VISIBLE);
            ll_option.setVisibility(View.VISIBLE);
        } else
        {
            v_line.setVisibility(View.GONE);
            ll_option.setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.tv_option);
        helper.addOnClickListener(R.id.tv_option_refund);
    }

    /**
     * 设置商品价格
     *
     * @param tv
     * @param price
     */
    private void setCommodityMoney(TextView tv, String price, boolean isSTE)
    {
        if (tv != null)
        {
            String money = "";
            if (isSTE)
            {
                money = tv.getContext().getString(R.string.mall_detail_info_price);
            } else
            {
                money = tv.getContext().getString(R.string.mall_detail_info_q_price);
            }
            tv.setText(String.format(money, VerificationUtil.verifyDefault(price, "0")));
        }
    }

    /**
     * 设置商品地点
     *
     * @param tv
     * @param address
     */
    private void setCommodityPlace(TextView tv, String address)
    {
        if (tv != null)
        {
            String place = tv.getContext().getString(R.string.mall_detail_info_place);
            tv.setText(String.format(place, VerificationUtil.verifyDefault(address, "等待客服通知")));
        }
    }

    /**
     * 设置商品时长
     *
     * @param tv
     * @param timelength
     */
    private void setCommodityTime(TextView tv, String timelength)
    {
        if (tv != null)
        {
            String time = tv.getContext().getString(R.string.mall_detail_info_time);
            tv.setText(String.format(time, VerificationUtil.verifyDefault(timelength, "0")));
        }
    }
}
