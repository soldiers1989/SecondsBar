package com.fx.secondbar.ui.home.item.adapter;

import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.PurchaseInfoBean;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

/**
 * function:推荐的名人项
 * author: frj
 * modify date: 2018/9/19
 */
public class AdPersonItem extends BaseQuickAdapter<PurchaseInfoBean, BaseViewHolder>
{
    public AdPersonItem()
    {
        super(R.layout.ad_infomation_person);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaseInfoBean item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_name = helper.getView(R.id.tv_name);

        GlideLoad.load(img, item.getPeopleimg(), true);
        setPrice(tv_price, item.getPrice());
        VerificationUtil.setViewValue(tv_name, item.getPeoplename());
    }

    /**
     * 设置价格
     *
     * @param tv
     * @param price
     */
    private void setPrice(TextView tv, String price)
    {
        if (tv != null)
        {
            String text = tv.getContext().getResources().getString(R.string.purchase_money_text);
            tv.setText(String.format(text, VerificationUtil.verifyDefault(price, "0")));
        }
    }
}
