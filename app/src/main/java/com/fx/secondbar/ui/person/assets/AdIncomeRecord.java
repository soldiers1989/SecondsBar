package com.fx.secondbar.ui.person.assets;

import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.QCoinBean;

/**
 * function:收益记录列表适配器
 * author: frj
 * modify date: 2018/9/21
 */
public class AdIncomeRecord extends BaseQuickAdapter<QCoinBean, BaseViewHolder>
{
    public AdIncomeRecord()
    {
        super(R.layout.ad_income_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, QCoinBean item)
    {
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_money = helper.getView(R.id.tv_money);
        View v_bottom = helper.getView(R.id.v_bottom);

        VerificationUtil.setViewValue(tv_content, item.getDescription());
        VerificationUtil.setViewValue(tv_time, item.getDaily());
        VerificationUtil.setViewValue(tv_money, "+" + VerificationUtil.verifyDefault(item.getPrice(), "0"));

        if (helper.getLayoutPosition() == getData().size() - 1)
        {
            v_bottom.setVisibility(View.GONE);
        } else
        {
            v_bottom.setVisibility(View.VISIBLE);
        }
    }
}
