package com.btten.bttenlibrary.base.load;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.R;
import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.view.LoadRelativeLayout;
import com.bumptech.glide.Glide;


/**
 * function:加载图层管理器
 * author: frj
 * modify date: 2016/12/30
 */
public class LoadManager extends BaseLoadManager
{
    private View view; // 加载图层附加的根图层

    private LoadRelativeLayout layout_load; // 加载图层
    private LinearLayout ll_load_fail; // 加载失败图层
    private LinearLayout ll_loading; // 正在加载图层
    private LinearLayout ll_empty; // 没有任何数据图层
    private TextView tv_load_empty; // 内容为空图层

    private ImageView img_loadding; // 加载中
    private Button btn_reload; // 重新加载
    private TextView tv_load_fail; // 加载失败
    private ImageView img_network_error; // 网络错误显示图标
    private ImageView img_load_empty;   //数据为空时显示
//    private TextView tv_server_error;// 服务端错误时显示

    /**
     * 构造器
     *
     * @param rootView 加载图层附加的根View
     */
    public LoadManager(View rootView)
    {
        if (rootView == null)
        {
            throw new NullPointerException("The rootView cannot be null");
        }
        view = rootView;
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        layout_load = (LoadRelativeLayout) view.findViewById(R.id.layout_load);
        ll_load_fail = (LinearLayout) view.findViewById(R.id.ll_load_fail);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        ll_empty = (LinearLayout) view.findViewById(R.id.ll_empty);
        tv_load_empty = (TextView) view.findViewById(R.id.tv_load_empty);
        img_loadding = (ImageView) view.findViewById(R.id.img_loadding);
        btn_reload = (Button) view.findViewById(R.id.btn_reload);
        tv_load_fail = (TextView) view.findViewById(R.id.tv_load_fail);
        img_network_error = (ImageView) view.findViewById(R.id.img_network_error);
        img_load_empty = (ImageView) view.findViewById(R.id.img_load_empty);
//        tv_server_error = (TextView) view.findViewById(R.id.tv_server_error);

        loadding();
    }

    @Override
    public void loadding()
    {
        super.loadding();
        if (!layout_load.isShown())
        {
            layout_load.setVisibility(View.VISIBLE);
        }
        ll_loading.setVisibility(View.VISIBLE);
        ll_load_fail.setVisibility(View.GONE);
        tv_load_empty.setVisibility(View.GONE);
        ll_empty.setVisibility(View.GONE);
        // 加载动画
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.loading_dialog_anim);
        // 使用ImageView显示动画
//        img_loadding.startAnimation(hyperspaceJumpAnimation);
        Glide.with(BtApplication.getApplication()).load(R.raw.ic_load_anim).into(img_loadding);
    }

    @Override
    public void loadFail(String text, final OnReloadListener listener)
    {
        super.loadFail(text, listener);
        if (!layout_load.isShown())
        {
            layout_load.setVisibility(View.VISIBLE);
        }
        ll_load_fail.setVisibility(View.VISIBLE);
        ll_loading.setVisibility(View.GONE);
        Glide.with(BtApplication.getApplication()).clear(img_loadding);
        ll_empty.setVisibility(View.GONE);
        tv_load_empty.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text))
        {
//            if (networkTips.contains(text)) {
//                img_network_error.setVisibility(View.VISIBLE);
//                tv_server_error.setVisibility(View.GONE);
//            } else {
            img_network_error.setVisibility(View.GONE);
//            tv_server_error.setVisibility(View.VISIBLE);
//            }
            tv_load_fail.setText(text);
        } else
        {
            img_network_error.setVisibility(View.VISIBLE);
//            tv_server_error.setVisibility(View.GONE);
        }
        if (listener != null)
        {
            btn_reload.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    loadding();
                    listener.onReload();
                }
            });
        } else
        {
            btn_reload.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadEmpty(String text)
    {
        super.loadEmpty(text);
        if (!layout_load.isShown())
        {
            layout_load.setVisibility(View.VISIBLE);
        }
        ll_empty.setVisibility(View.VISIBLE);
        tv_load_empty.setVisibility(View.VISIBLE);
        ll_load_fail.setVisibility(View.GONE);
        ll_loading.setVisibility(View.GONE);
        Glide.with(BtApplication.getApplication()).clear(img_loadding);
        if (!TextUtils.isEmpty(text))
        {
            tv_load_empty.setText(text);
        }
    }

    /**
     * 加载空数据时显示
     *
     * @param text 空文本提示
     * @param res  空数据时，提示图片资源地址
     */
    public void loadEmpty(String text, @DrawableRes int res)
    {
        loadEmpty(text);
        img_load_empty.setImageResource(res);
    }

    @Override
    public void loadSuccess()
    {
        super.loadSuccess();
        layout_load.setVisibility(View.GONE);
        tv_load_empty.setVisibility(View.GONE);
        ll_empty.setVisibility(View.GONE);
        ll_load_fail.setVisibility(View.GONE);
        ll_loading.setVisibility(View.GONE);
        Glide.with(BtApplication.getApplication()).clear(img_loadding);
    }

    /**
     * 设置LoaManager与Window解绑监听器
     *
     * @param loadDettachListener
     */
    public void setLoadDettachListener(ILoadDettachListener loadDettachListener)
    {
        layout_load.setLoadDettachListener(loadDettachListener);
    }

    /**
     * 获取加载图层布局
     *
     * @return
     */
    public View getLoadRootView()
    {
        return layout_load;
    }
}
