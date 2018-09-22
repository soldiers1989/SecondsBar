package com.fx.secondbar.ui.home.item.adapter;

import android.widget.TextView;

import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

/**
 * function:推荐的名人项
 * author: frj
 * modify date: 2018/9/19
 */
public class AdPersonItem extends BaseQuickAdapter<PersonBean, BaseViewHolder>
{
    public AdPersonItem()
    {
        super(R.layout.ad_infomation_person);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_name = helper.getView(R.id.tv_name);

        GlideLoad.load(img, item.getPicture(), true);
        VerificationUtil.setViewValue(tv_price, item.getPrice());
        VerificationUtil.setViewValue(tv_name, item.getName());
    }
}
