package com.fx.secondbar.ui.mall;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.fx.secondbar.bean.DateBean;
import com.fx.secondbar.ui.AcWebBrowse;

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
    private DateBean dateBean;
    private OnBuyListener onBuyListener;

    public DialogBuy(@NonNull Context context, CommodityBean commodityBean)
    {
        this.context = context;
        this.commodityBean = commodityBean;
        init();
    }

    public DialogBuy(@NonNull Context context, DateBean dateBean)
    {
        this.context = context;
        this.dateBean = dateBean;
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
    private View createView(final Context context)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_buy, null);
        final TextView tv_price = view.findViewById(R.id.tv_price);
        final TextView tv_q_price = view.findViewById(R.id.tv_q_price);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_time = view.findViewById(R.id.tv_time);
        final TextView tv_protocol = view.findViewById(R.id.tv_protocol);
        TextView tv_date_rule = view.findViewById(R.id.tv_date_rule);
        TextView tv_pay_intro = view.findViewById(R.id.tv_pay_intro);
        Button btn_buy = view.findViewById(R.id.btn_buy);

//        String protocol = context.getString(R.string.dialog_mall_buy_protocol_content);
//        String tips = String.format(context.getString(R.string.dialog_mall_buy_protocol_tips), protocol);
//        SpannableString spannableString = new SpannableString(tips);
//        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.home_tabs_mark_color)), tips.indexOf(protocol), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv_protocol.setText(spannableString);

        if (commodityBean != null)
        {
            VerificationUtil.setViewValue(tv_price, String.format(context.getString(R.string.mall_detail_info_price), commodityBean.getPrice()));
            VerificationUtil.setViewValue(tv_name, commodityBean.getName());
            VerificationUtil.setViewValue(tv_time, commodityBean.getTimelength() + "分钟");
            VerificationUtil.setViewValue(tv_q_price, String.format(context.getString(R.string.mall_detail_info_q_price), VerificationUtil.verifyDefault(commodityBean.getQcoin(), "0")));
            //人民币支付
            if (0 == commodityBean.getPaytype())
            {
                tv_price.setVisibility(View.VISIBLE);
                tv_q_price.setVisibility(View.GONE);
            } else
            {
                tv_price.setVisibility(View.GONE);
                tv_q_price.setVisibility(View.VISIBLE);
            }
        } else if (dateBean != null)
        {
            VerificationUtil.setViewValue(tv_price, String.format(context.getString(R.string.mall_detail_info_price), dateBean.getPrice()));
            VerificationUtil.setViewValue(tv_name, dateBean.getName());
            VerificationUtil.setViewValue(tv_time, dateBean.getTimelength() + "分钟");
            VerificationUtil.setViewValue(tv_q_price, String.format(context.getString(R.string.mall_detail_info_q_price), VerificationUtil.verifyDefault(dateBean.getQcoin(), "0")));
            //人民币支付
            if (0 == dateBean.getPaytype())
            {
                tv_price.setVisibility(View.VISIBLE);
                tv_q_price.setVisibility(View.GONE);
            } else
            {
                tv_price.setVisibility(View.GONE);
                tv_q_price.setVisibility(View.VISIBLE);
            }
        }

        tv_price.setSelected(true);

        tv_protocol.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView tv = (TextView) v;
                tv.setSelected(!tv.isSelected());
            }
        });
        tv_pay_intro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, AcWebBrowse.class);
                intent.putExtra("activity_str", "秒吧支付说明协议");
                intent.putExtra("activity_num", "http://www.feixingtech.com:8080/static/mb-front/getText.html?type=13");
                context.startActivity(intent);
            }
        });
        tv_date_rule.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, AcWebBrowse.class);
                intent.putExtra("activity_str", "约见规则");
                intent.putExtra("activity_num", "http://www.feixingtech.com:8080/static/mb-front/getText.html?type=21");
                context.startActivity(intent);
            }
        });
        tv_price.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv_q_price.setSelected(false);
                tv_price.setSelected(true);
            }
        });
        tv_q_price.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv_q_price.setSelected(true);
                tv_price.setSelected(false);
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
                if (onBuyListener != null)
                {
                    if (commodityBean != null)
                    {
                        onBuyListener.onBuy(commodityBean.getMerchandise_ID(), tv_price.isSelected());
                    } else if (dateBean != null)
                    {
                        onBuyListener.onBuy(dateBean.getStroke_ID(), tv_price.isSelected());
                    }
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
         * @param goodsId  商品id
         * @param isStePay 是否是STE支付，true表示是，false表示Q支付
         */
        void onBuy(String goodsId, boolean isStePay);
    }

}
