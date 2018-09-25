package com.fx.secondbar.ui.transaction;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;

/**
 * function:委托列表适配器
 * author: frj
 * modify date: 2018/9/25
 */
public class AdCommission extends BaseQuickAdapter<String, BaseViewHolder>
{
    public AdCommission()
    {
        super(R.layout.ad_commission);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_seconds = helper.getView(R.id.tv_seconds);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_status = helper.getView(R.id.tv_status);
        View v_bottom = helper.getView(R.id.v_bottom);

        calSize(tv_name, tv_seconds, tv_price, tv_status);
        if (helper.getLayoutPosition() % 2 == 0)
        {
            tv_status.setTextColor(Color.parseColor("#03c086"));
        } else
        {
            tv_status.setTextColor(Color.parseColor("#ff3b63"));
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
     * @param tvStatus
     */
    private void calSize(TextView tvName, TextView tvSeconds, TextView tvPrice, TextView tvStatus)
    {
        int width = DisplayUtil.getRealScreenSize(FxApplication.getInstance()).widthPixels;
        width -= DensityUtil.dip2px(FxApplication.getInstance(), 15) * 2;
        //比例为1.5：1：1：1
        int one = (int) (width / 4.5);
        setTextWidth(tvName, width - one * 3);
        setTextWidth(tvSeconds, one);
        setTextWidth(tvPrice, one);
        setTextWidth(tvStatus, one);
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
