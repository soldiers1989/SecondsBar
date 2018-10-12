package com.fx.secondbar.ui.person;

import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.SystemIntroBean;

/**
 * function:交易规则项适配器
 * author: frj
 * modify date: 2018/10/11
 */
public class AdTransactionRule extends BaseQuickAdapter<SystemIntroBean, BaseViewHolder>
{
    public AdTransactionRule()
    {
        super(R.layout.ad_transaction_rule_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemIntroBean item)
    {
        TextView tv = helper.getView(R.id.tv);
        VerificationUtil.setViewValue(tv, item.getTitle());
    }
}
