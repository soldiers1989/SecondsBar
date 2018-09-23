package com.fx.secondbar.ui.home.adapter;

import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.ActiveBean;
import com.fx.secondbar.bean.QIntroBean;

/**
 * function:如何获得Q的列表适配器
 * author: frj
 * modify date: 2018/9/20
 */
public class AdPerson extends BaseQuickAdapter<ActiveBean, BaseViewHolder>
{
    public AdPerson()
    {
        super(R.layout.ad_person);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActiveBean item)
    {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_intro = helper.getView(R.id.tv_intro);
        TextView tv_option = helper.getView(R.id.tv_option);

        VerificationUtil.setViewValue(tv_title, item.getName());
        VerificationUtil.setViewValue(tv_intro, item.getContent());
        VerificationUtil.setViewValue(tv_option, item.getDescription());
    }
}
