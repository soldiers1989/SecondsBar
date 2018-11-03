package com.fx.secondbar.ui.person.assets;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.WithdrawRecordBean;

/**
 * function:提现记录列表适配器
 * author: frj
 * modify date: 2018/11/3
 */
public class AdWithdrawRecord extends BaseQuickAdapter<WithdrawRecordBean, BaseViewHolder>
{

    public AdWithdrawRecord()
    {
        super(R.layout.ad_withdraw_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawRecordBean item)
    {
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_bank_card = helper.getView(R.id.tv_bank_card);
        TextView tv_reason = helper.getView(R.id.tv_reason);
        View v_bottom = helper.getView(R.id.v_bottom);

        VerificationUtil.setViewValue(tv_bank_card, String.format(tv_bank_card.getResources().getString(R.string.withdraw_record_bankcard_text), getBankNoEnd(item.getBankno())));
        VerificationUtil.setViewValue(tv_content, String.format(tv_content.getResources().getString(R.string.withdraw_record_content_text), VerificationUtil.verifyDefault(item.getMoney(), "0"), VerificationUtil.verifyDefault(item.getFee(), "0")));
        VerificationUtil.setViewValue(tv_time, item.getCreatetime());
        VerificationUtil.setViewValue(tv_name, String.format(tv_name.getResources().getString(R.string.withdraw_record_name_text), VerificationUtil.verifyDefault(item.getName(), "")));
        if (TextUtils.isEmpty(item.getDescription()))
        {
            tv_reason.setVisibility(View.GONE);
        } else
        {
            VerificationUtil.setViewValue(tv_reason, String.format(tv_reason.getResources().getString(R.string.withdraw_record_reason_text), VerificationUtil.verifyDefault(item.getDescription(), "")));
            tv_reason.setVisibility(View.VISIBLE);
        }
        VerificationUtil.setViewValue(tv_status, item.getStatusname());
        if (helper.getLayoutPosition() == getItemCount() - 1)
        {
            v_bottom.setVisibility(View.INVISIBLE);
        } else
        {
            v_bottom.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取银行卡尾号
     *
     * @param bankNo
     * @return
     */
    private String getBankNoEnd(String bankNo)
    {
        if (TextUtils.isEmpty(bankNo))
        {
            return "";
        }
        if (bankNo.length() > 4)
        {
            return bankNo.substring(bankNo.length() - 4);
        }
        return bankNo;
    }
}
