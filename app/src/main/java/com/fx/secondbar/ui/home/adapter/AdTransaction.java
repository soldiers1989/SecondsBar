package com.fx.secondbar.ui.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;

import java.util.List;

/**
 * function:交易页-当前委托列表适配器
 * author: frj
 * modify date: 2018/9/12
 */
public class AdTransaction extends BaseQuickAdapter<String, BaseViewHolder> {
    public AdTransaction() {
        super(R.layout.ad_transaction_entrust_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
