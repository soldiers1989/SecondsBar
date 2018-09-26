package com.fx.secondbar.ui.mall;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommodityBean;

/**
 * function:立即购买对话框
 * author: frj
 * modify date: 2018/9/10
 */
public class DialogBuy
{

    private Context context;
    private Dialog dialog;
    private CommodityBean commodityBean;
    private OnBuyListener onBuyListener;

    public DialogBuy(@NonNull Context context, CommodityBean commodityBean)
    {
        this.context = context;
        this.commodityBean = commodityBean;
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        dialog = new Dialog(context, R.style.dialog_share);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(createView(context));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DisplayUtil.getRealScreenSize(context).widthPixels;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(R.style.dialog_share);
    }

    /**
     * 创建控件
     *
     * @param context
     * @return
     */
    private View createView(Context context)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_buy, null);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_time = view.findViewById(R.id.tv_time);
        final TextView tv_protocol = view.findViewById(R.id.tv_protocol);
        Button btn_buy = view.findViewById(R.id.btn_buy);

        String protocol = context.getString(R.string.dialog_mall_buy_protocol_content);
        String tips = String.format(context.getString(R.string.dialog_mall_buy_protocol_tips), protocol);
        SpannableString spannableString = new SpannableString(tips);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.home_tabs_mark_color)), tips.indexOf(protocol), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_protocol.setText(spannableString);

        if (commodityBean != null)
        {
            VerificationUtil.setViewValue(tv_price, String.format(context.getString(R.string.mall_detail_info_price), commodityBean.getPrice()));
            VerificationUtil.setViewValue(tv_name, commodityBean.getName());
            VerificationUtil.setViewValue(tv_time, commodityBean.getTimelength() + "分钟");
        }

        tv_protocol.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView tv = (TextView) v;
                tv.setSelected(!tv.isSelected());
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!tv_protocol.isSelected())
                {
                    ShowToast.showToast("请先阅读秒吧支付说明协议");
                    return;
                }
                if (onBuyListener != null && commodityBean != null)
                {
                    onBuyListener.onBuy(commodityBean.getMerchandise_ID());
                }
            }
        });
        return view;
    }

    public void setOnBuyListener(OnBuyListener onBuyListener)
    {
        this.onBuyListener = onBuyListener;
    }

    /**
     * 显示对话框
     */
    public void show()
    {
        if (dialog != null && !dialog.isShowing())
        {
            dialog.show();
        }
    }

    /**
     * 隐藏对话框
     */
    public void dismiss()
    {
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }

    /**
     * 购买监听
     */
    public interface OnBuyListener
    {
        /**
         * 立即购买
         *
         * @param goodsId 商品id
         */
        void onBuy(String goodsId);
    }

}
