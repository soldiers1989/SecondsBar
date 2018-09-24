package com.fx.secondbar.ui.home.adapter;

import android.widget.Button;
import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.ActiveBean;

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
    protected void convert(BaseViewHolder helper, final ActiveBean item)
    {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_intro = helper.getView(R.id.tv_intro);
        Button tv_option = helper.getView(R.id.tv_option);
        helper.addOnClickListener(R.id.tv_option);

        VerificationUtil.setViewValue(tv_title, item.getName());
        VerificationUtil.setViewValue(tv_intro, item.getContent());


        if (String.valueOf(ActiveBean.TYPE_SIGN).equals(item.getType()))
        {
            if (FxApplication.getInstance().getUserInfoBean().getIsCheckin() == 1)
            {
                VerificationUtil.setViewValue(tv_option, "已签到");
            } else
            {
                VerificationUtil.setViewValue(tv_option, item.getDescription());
            }
        } else
        {
            VerificationUtil.setViewValue(tv_option, item.getDescription());
        }
    }
}
