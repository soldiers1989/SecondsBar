package com.fx.secondbar.ui.person;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;

/**
 * function:交易规则项适配器
 * author: frj
 * modify date: 2018/10/11
 */
public class AdTransactionRule extends BaseQuickAdapter<String, BaseViewHolder>
{
    public AdTransactionRule()
    {
        super(R.layout.ad_transaction_rule_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        TextView tv = helper.getView(R.id.tv);
    }
}
