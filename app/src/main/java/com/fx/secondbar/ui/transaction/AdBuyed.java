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
import com.fx.secondbar.bean.TradingBuyedBean;

/**
 * function:已购列表适配器
 * author: frj
 * modify date: 2018/9/28
 */
public class AdBuyed extends BaseQuickAdapter<TradingBuyedBean.EntityBean, BaseViewHolder>
{
    public AdBuyed()
    {
        super(R.layout.ad_transaction_buyed);
    }

    @Override
    protected void convert(BaseViewHolder helper, TradingBuyedBean.EntityBean item)
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

        StringBuilder sbPrice = new StringBuilder();
        sbPrice.append(VerificationUtil.verifyDefault(item.getNewprice(), "0"));
        sbPrice.append("/");
        sbPrice.append(VerificationUtil.verifyDefault(item.getPrice(), "0"));

        VerificationUtil.setViewValue(tv_price, sbPrice.toString());

        if (TextUtils.isEmpty(item.getSpreadprice()))
        {
            tv_profit_loss.setText("0");
        } else
        {
            if (item.getSpreadprice().indexOf("-") == 0)
            {
                tv_profit_loss.setText(item.getSpreadprice());
            } else
            {
                tv_profit_loss.setText("+" + item.getSpreadprice());
            }
        }

        if (helper.getLayoutPosition() == getData().size() - 1)
        {
            v_bottom.setVisibility(View.GONE);
        } else
        {
            v_bottom.setVisibility(View.VISIBLE);
        }
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
