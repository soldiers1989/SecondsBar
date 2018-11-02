package com.fx.secondbar.ui.transaction;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.MarketValueBean;

/**
 * function:持仓收益详情列表适配器
 * author: frj
 * modify date: 2018/11/2
 */
public class AdMarketValue extends BaseQuickAdapter<MarketValueBean, BaseViewHolder>
{
    public AdMarketValue()
    {
        super(R.layout.ad_transaction_buyed);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketValueBean item)
    {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_seconds = helper.getView(R.id.tv_seconds);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_profit_loss = helper.getView(R.id.tv_profit_loss);
        View v_bottom = helper.getView(R.id.v_bottom);
        calSize(tv_name, tv_seconds, tv_price, tv_profit_loss);

        String name = item.getName();
        if (!TextUtils.isEmpty(item.getZjm()))
        {
            name += "(" + item.getZjm() + ")";
        }
        VerificationUtil.setViewValue(tv_name, name);
        VerificationUtil.setViewValue(tv_price, item.getPrice());
        VerificationUtil.setViewValue(tv_seconds, item.getAmount());
        VerificationUtil.setViewValue(tv_profit_loss, item.getTotal());
    }

    /**
     * 计算项大小
     *
     * @param tvName
     * @param tvSeconds
     * @param tvPrice
     * @param tvProfitLoss
     */
    private void calSize(TextView tvName, TextView tvSeconds, TextView tvPrice, TextView tvProfitLoss)
    {
        int width = DisplayUtil.getRealScreenSize(FxApplication.getInstance()).widthPixels;
        width -= DensityUtil.dip2px(FxApplication.getInstance(), 15) * 2;
        //比例为1：1：1.5：1
        int one = (int) (width / 4.5);
        setTextWidth(tvName, one);
        setTextWidth(tvSeconds, one);
        setTextWidth(tvPrice, width - one * 3);
        setTextWidth(tvProfitLoss, one);
    }

    /**
     * 设置TextView宽度
     *
     * @param tv
     * @param width
     */
    private void setTextWidth(TextView tv, int width)
    {
        if (tv != null)
        {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
            if (params.width != width)
            {
                params.width = width;
                tv.setLayoutParams(params);
            }
        }
    }
}
