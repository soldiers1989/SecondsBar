package com.fx.secondbar.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.SignRuleBean;
import com.fx.secondbar.bean.SigninBean;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.http.HttpManager;

import java.util.List;

import rx.Subscriber;

/**
 * function:签到对话框
 * author: frj
 * modify date: 2018/9/24
 */
public class DialogSign
{

    private Context context;
    private Dialog dialog;

    private ImageView img_mark;
    private Button btn_sign;
    private LinearLayout ll_success;
    private TextView tv_days;
    private TextView tv_rule_des;
    private LinearLayout ll_gold;

    public DialogSign(@NonNull Context context)
    {
        this.context = context;
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        dialog = new Dialog(context, R.style.dialog_sign);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(createView(context));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(R.style.dialog_sign);
    }

    /**
     * 创建控件
     *
     * @param context
     * @return
     */
    private View createView(Context context)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sign, null);
        img_mark = view.findViewById(R.id.img_mark);
        btn_sign = view.findViewById(R.id.btn_sign);
        ll_gold = view.findViewById(R.id.ll_gold);
        LinearLayout ll_days = view.findViewById(R.id.ll_days);
        LinearLayout ll_reward = view.findViewById(R.id.ll_reward);
        ll_success = view.findViewById(R.id.ll_success);
        tv_days = view.findViewById(R.id.tv_days);
        tv_rule_des = view.findViewById(R.id.tv_rule_des);
        initSignConfig(ll_gold, ll_days, ll_reward, btn_sign, ll_success, img_mark, tv_days);
        btn_sign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signin();
                btn_sign.setText("签到中");
            }
        });

        return view;
    }

    /**
     * 初始化签到配置
     *
     * @param llGold
     * @param llDays
     * @param llReward
     */
    private void initSignConfig(LinearLayout llGold, LinearLayout llDays, LinearLayout llReward, Button btnSign, LinearLayout llSuccess, ImageView imgMark, TextView tvDays)
    {
        List<SignRuleBean> list = FxApplication.getInstance().getConfigInfo().getListSigninRule();
        if (VerificationUtil.noEmpty(list))
        {
            UserInfoBean bean = FxApplication.getInstance().getUserInfoBean();
            //判断是否签到，1表示已签到
            if (1 == bean.getIsCheckin())
            {
                btnSign.setVisibility(View.GONE);
                llSuccess.setVisibility(View.VISIBLE);
                imgMark.setImageResource(R.mipmap.ic_sign_img_success);
            } else
            {
                btnSign.setVisibility(View.VISIBLE);
                llSuccess.setVisibility(View.GONE);
                imgMark.setImageResource(R.mipmap.ic_sign_img);
            }
            tvDays.setText(String.format(FxApplication.getStr(R.string.sign_cotinuous_day), String.valueOf(bean.getCheckinDays())));
            for (int i = 0; i < list.size(); i++)
            {
                boolean isSigned = false;
                if (1 == bean.getIsCheckin() && i + 1 <= bean.getCheckinDays())
                {
                    isSigned = true;
                } else
                {
                    isSigned = false;
                }
                llGold.addView(createGoldImg(llGold.getContext(), isSigned));
                llDays.addView(createTips(llDays.getContext(), list.get(i).getLevel()));
                llReward.addView(createReward(llReward.getContext(), list.get(i).getAmount()));
            }
        }
    }

    /**
     * 创建金币ImageView
     *
     * @return
     */
    private View createGoldImg(Context context, boolean isSigned)
    {
        ImageView img = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, DensityUtil.dip2px(context, 39));
        params.weight = 1;
        img.setLayoutParams(params);
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        img.setImageResource(isSigned ? R.mipmap.ic_sign_success : R.mipmap.ic_sign_gold);
        return img;
    }

    /**
     * 创建提示
     *
     * @param context
     * @param level
     * @return
     */
    private View createTips(Context context, String level)
    {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(12);
        textView.setTextColor(Color.parseColor("#9d9d9d"));
        textView.setText("第" + level + "天");
        return textView;
    }

    /**
     * 创建奖励文本
     *
     * @param context
     * @param reward
     * @return
     */
    private View createReward(Context context, String reward)
    {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(14);
        textView.setTextColor(context.getResources().getColor(R.color.font_yellow_color));
        textView.setText(VerificationUtil.verifyDefault(reward, ""));
        return textView;
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
     * 签到
     */
    private void signin()
    {
        HttpManager.signin(new Subscriber<SigninBean>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (dialog == null || !dialog.isShowing())
                {
                    return;
                }
                e.printStackTrace();
                ShowToast.showToast("签到失败");
            }

            @Override
            public void onNext(SigninBean bean)
            {
                if (dialog == null || !dialog.isShowing())
                {
                    return;
                }
                ShowToast.showToast("签到成功");
                if (btn_sign != null)
                {
                    btn_sign.setVisibility(View.GONE);
                }
                if (ll_success != null)
                {
                    ll_success.setVisibility(View.VISIBLE);
                }
                if (img_mark != null)
                {
                    img_mark.setImageResource(R.mipmap.ic_sign_img_success);
                }
                if (tv_days != null)
                {
                    tv_days.setText(String.format(FxApplication.getStr(R.string.sign_cotinuous_day), String.valueOf(bean.getDays())));
                }
                if (ll_gold != null)
                {
                    for (int i = 0; i < ll_gold.getChildCount(); i++)
                    {
                        ImageView img = (ImageView) ll_gold.getChildAt(i);
                        if (i + 1 <= bean.getDays())
                        {
                            img.setImageResource(R.mipmap.ic_sign_success);
                        } else
                        {
                            img.setImageResource(R.mipmap.ic_sign_gold);
                        }
                    }
                }
                //发送更新用户信息广播
                FxApplication.refreshUserInfoBroadCast();
            }
        });
    }

}
