package com.fx.secondbar.ui.person;

import android.text.Html;
import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.SystemIntroBean;

/**
 * function:帮助列表适配器
 * author: frj
 * modify date: 2018/10/11
 */
public class AdHelpList extends BaseQuickAdapter<SystemIntroBean, BaseViewHolder>
{
    public AdHelpList()
    {
        super(R.layout.ad_help_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemIntroBean item)
    {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_content = helper.getView(R.id.tv_content);

        VerificationUtil.setViewValue(tv_title, item.getTitle());
        tv_content.setText(Html.fromHtml(item.getContent()));
    }
}
