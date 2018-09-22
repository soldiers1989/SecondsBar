package com.fx.secondbar.ui.home.adapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.QuoteBean;
import com.joooonho.SelectableRoundedImageView;

/**
 * function:行情列表适配器
 * author: frj
 * modify date: 2018/9/10
 */
public class AdQuote extends BaseQuickAdapter<QuoteBean, BaseViewHolder>
{

    public AdQuote()
    {
        super(R.layout.ad_quote);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuoteBean item)
    {
        LinearLayout ll_name = helper.getView(R.id.ll_name);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_up = helper.getView(R.id.tv_up_down);
        TextView tv_percent = helper.getView(R.id.tv_percent);
        calSize(ll_name, tv_price, tv_up, tv_percent);

        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_name = helper.getView(R.id.tv_name);

        GlideApp.with(img).load(item.getAvatar()).centerCrop().into(img);
        VerificationUtil.setViewValue(tv_name, item.getName());
        VerificationUtil.setViewValue(tv_price, item.getPrice());
        VerificationUtil.setViewValue(tv_up, item.getUpsAndDowns());
        VerificationUtil.setViewValue(tv_percent, item.getPercent());

        if (item.isUp())
        {
            tv_up.setTextColor(tv_up.getContext().getResources().getColor(R.color.quote_item_title_item_up));
            tv_percent.setTextColor(tv_up.getContext().getResources().getColor(R.color.quote_item_title_item_up));
        } else
        {
            tv_up.setTextColor(tv_up.getContext().getResources().getColor(R.color.quote_item_title_item_down));
            tv_percent.setTextColor(tv_up.getContext().getResources().getColor(R.color.quote_item_title_item_down));
        }
    }

    /**
     * 计算尺寸
     *
     * @param ll
     * @param tvPrice
     * @param tvUp
     * @param tvPercent
     */
    private void calSize(LinearLayout ll, TextView tvPrice, TextView tvUp, TextView tvPercent)
    {
        int width = DisplayUtil.getScreenSize(BtApplication.getApplication()).widthPixels;
        width -= BtApplication.getApplication().getResources().getDimensionPixelSize(R.dimen.quote_item_title_plr) * 2;
//        比例为：2.4:1:1:1
        //比例为：2:1:1:1
        int oneWith = (int) (width / 5);
        LinearLayout.LayoutParams paramsLL = (LinearLayout.LayoutParams) ll.getLayoutParams();
        paramsLL.width = width - oneWith * 3;
        ll.setLayoutParams(paramsLL);

        LinearLayout.LayoutParams paramsPrice = (LinearLayout.LayoutParams) tvPrice.getLayoutParams();
        paramsPrice.width = oneWith;
        tvPrice.setLayoutParams(paramsPrice);

        LinearLayout.LayoutParams paramsUp = (LinearLayout.LayoutParams) tvUp.getLayoutParams();
        paramsUp.width = oneWith;
        tvUp.setLayoutParams(paramsUp);

        LinearLayout.LayoutParams paramsPercent = (LinearLayout.LayoutParams) tvPercent.getLayoutParams();
        paramsPercent.width = oneWith;
        tvPercent.setLayoutParams(paramsPercent);
    }
}
