package com.fx.secondbar.ui.person.assets;

import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.BankBean;

/**
 * function:开户银行列表适配器
 * author: frj
 * modify date: 2018/9/22
 */
public class AdSelectBank extends BaseQuickAdapter<BankBean, BaseViewHolder>
{
    public AdSelectBank()
    {
        super(R.layout.ad_select_bank_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankBean item)
    {
        TextView tv_bank = helper.getView(R.id.tv_bank);
        VerificationUtil.setViewValue(tv_bank, item.getName());
        tv_bank.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
    }
}
