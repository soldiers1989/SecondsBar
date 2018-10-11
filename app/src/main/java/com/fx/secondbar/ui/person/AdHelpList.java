package com.fx.secondbar.ui.person;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;

/**
 * function:帮助列表适配器
 * author: frj
 * modify date: 2018/10/11
 */
public class AdHelpList extends BaseQuickAdapter<String, BaseViewHolder>
{
    public AdHelpList()
    {
        super(R.layout.ad_help_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_content = helper.getView(R.id.tv_content);
    }
}
