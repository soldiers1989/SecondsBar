package com.fx.secondbar.ui.notify;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;

/**
 * function:消息列表适配器
 * author: frj
 * modify date: 2018/9/26
 */
public class AdMessage extends BaseQuickAdapter<String, BaseViewHolder>
{
    public AdMessage()
    {
        super(R.layout.ad_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_detail = helper.getView(R.id.tv_detail);
        TextView tv_content = helper.getView(R.id.tv_content);
        helper.addOnClickListener(R.id.tv_detail);
    }
}
