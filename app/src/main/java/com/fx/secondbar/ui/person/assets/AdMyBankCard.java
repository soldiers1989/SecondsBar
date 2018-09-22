package com.fx.secondbar.ui.person.assets;

import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.BankBean;

/**
 * function:我的银行卡列表适配器
 * author: frj
 * modify date: 2018/9/22
 */
public class AdMyBankCard extends BaseQuickAdapter<BankBean, BaseViewHolder>
{
    public AdMyBankCard()
    {
        super(R.layout.ad_my_bank_card);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankBean item)
    {
        TextView tv_bank_name = helper.getView(R.id.tv_bank_name);
        TextView tv_delete = helper.getView(R.id.tv_delete);
        TextView tv_card_num = helper.getView(R.id.tv_card_num);

        VerificationUtil.setViewValue(tv_bank_name, item.getName());
    }
}
