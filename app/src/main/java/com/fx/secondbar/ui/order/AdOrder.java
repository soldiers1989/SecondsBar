package com.fx.secondbar.ui.order;

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

        GlideLoad.load(img, item.getImg(), true);
        VerificationUtil.setViewValue(tv_status, item.getStatusname());
        VerificationUtil.setViewValue(tv_name, item.getName());

        setCommodityMoney(tv_price, item.getPrice());
        setCommodityPlace(tv_place, item.getAddress());
        setCommodityTime(tv_time, item.getTimelength());

        if (FOrderItem.TYPE_PERFORMANCE == item.getStatus())
        {
//            tv_option.setText("取消订单，并退款");
            tv_option.setText("申诉");
        } else if (FOrderItem.TYPE_PERFORMANCING == item.getStatus())
        {
//            tv_option.setText("查看商品");
            tv_option.setText("申诉");
        } else if (FOrderItem.TYPE_REFUND == item.getStatus())
        {
//            tv_option.setText("取消退款");
            tv_option.setText("申诉");
        } else
        {
            tv_option.setText("申诉");
        }
        helper.addOnClickListener(R.id.tv_option);
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
