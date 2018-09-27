package com.fx.secondbar.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.btten.bttenlibrary.util.DisplayUtil;
import com.fx.secondbar.R;

/**
 * function:分享对话框，从底部弹出
 * author: frj
 * modify date: 2018/9/9
 */
public class DialogShare
{

    private Context context;
    private Dialog dialog;

    private OnSharePlatformListener listener;

    public DialogShare(@NonNull Context context)
    {
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        ImageButton img_qq = view.findViewById(R.id.img_qq);
        ImageButton img_weibo = view.findViewById(R.id.img_weibo);
        ImageButton img_cycle = view.findViewById(R.id.img_cycle);
        ImageButton img_wechat = view.findViewById(R.id.img_wechat);

        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (listener == null)
                {
                    return;
                }
                if (R.id.img_qq == v.getId())
                {
                    listener.onShare(OnSharePlatformListener.PLATFORM_QQ);
                } else if (R.id.img_weibo == v.getId())
                {
                    listener.onShare(OnSharePlatformListener.PLATFORM_WEIBO);
                } else if (R.id.img_cycle == v.getId())
                {
                    listener.onShare(OnSharePlatformListener.PLATFORM_CYCLE);
                } else if (R.id.img_wechat == v.getId())
                {
                    listener.onShare(OnSharePlatformListener.PLATFORM_WECHAT);
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
            }
        };
        img_qq.setOnClickListener(onClickListener);
        img_weibo.setOnClickListener(onClickListener);
        img_cycle.setOnClickListener(onClickListener);
        img_wechat.setOnClickListener(onClickListener);
        return view;
    }

    /**
     * 设置分享点击回调
     *
     * @param listener
     */
    public DialogShare setOnSharePlatformListener(OnSharePlatformListener listener)
    {
        this.listener = listener;
        return this;
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

    public interface OnSharePlatformListener
    {

        /**
         * QQ
         */
        int PLATFORM_QQ = 1;

        /**
         * 微博
         */
        int PLATFORM_WEIBO = 2;

        /**
         * 朋友圈
         */
        int PLATFORM_CYCLE = 3;

        /**
         * 微信
         */
        int PLATFORM_WECHAT = 4;

        /**
         * 分享至平台
         *
         * @param platform 平台代号
         */
        void onShare(int platform);
    }
}
