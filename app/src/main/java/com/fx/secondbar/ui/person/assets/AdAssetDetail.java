package com.fx.secondbar.ui.person.assets;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.ConsumerBean;

/**
 * function:资产明细列表适配器
 * author: frj
 * modify date: 2018/9/21
 */
public class AdAssetDetail extends BaseQuickAdapter<ConsumerBean, BaseViewHolder>
{
    public AdAssetDetail()
    {
        super(R.layout.ad_asset_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsumerBean item)
    {
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_money = helper.getView(R.id.tv_money);
        View v_bottom = helper.getView(R.id.v_bottom);

        VerificationUtil.setViewValue(tv_content, item.getDescription());
        VerificationUtil.setViewValue(tv_time, item.getCreatetime());
        String str = "";
        if (item.getPaytype() == 1)
        {
            str = VerificationUtil.verifyDefault(item.getAmount(), "0");
            //是否是充值
            if ("1".equals(item.getType()))
            {
                tv_money.setTextColor(Color.parseColor("#03c086"));
                str = "+" + str;
            } else
            {
                tv_money.setTextColor(Color.parseColor("#E76E43"));
                str = "-" + str;
            }

        } else
        {
            tv_money.setTextColor(Color.parseColor("#E76E43"));
            str = VerificationUtil.verifyDefault(item.getQcoin(), "0");
            str = "-" + str;
        }
        VerificationUtil.setViewValue(tv_money, str, "");

    }
}
