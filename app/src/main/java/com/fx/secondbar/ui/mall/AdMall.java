package com.fx.secondbar.ui.mall;

import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

/**
 * function:商城列表适配器
 * author: frj
 * modify date: 2018/9/9
 */
public class AdMall extends BaseQuickAdapter<CommodityBean, BaseViewHolder>
{

    public AdMall()
    {
        super(R.layout.ad_mall);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityBean item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        AppCompatTextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_title = helper.getView(R.id.tv_title);

        GlideLoad.load(img, item.getImage(), true);
        VerificationUtil.setViewValue(tv_price, String.format(tv_price.getContext().getString(R.string.mall_detail_info_price), item.getPrice()));
        VerificationUtil.setViewValue(tv_title, item.getName());

    }
}
