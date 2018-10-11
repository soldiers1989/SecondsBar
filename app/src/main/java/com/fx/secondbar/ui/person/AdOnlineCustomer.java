package com.fx.secondbar.ui.person;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;

/**
 * function:在线客服列表适配器
 * author: frj
 * modify date: 2018/10/11
 */
public class AdOnlineCustomer extends BaseQuickAdapter<String, BaseViewHolder>
{
    public AdOnlineCustomer()
    {
        super(R.layout.ad_online_customer);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        TextView tv = helper.getView(R.id.tv);
    }
}
