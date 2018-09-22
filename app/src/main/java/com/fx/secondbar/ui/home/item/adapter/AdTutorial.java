package com.fx.secondbar.ui.home.item.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.TurialBean;
import com.fx.secondbar.util.GlideLoad;

/**
 * function:首页-教程的适配器
 * author: frj
 * modify date: 2018/9/9
 */
public class AdTutorial extends BaseQuickAdapter<TurialBean, BaseViewHolder>
{

    public AdTutorial(int layoutResId)
    {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TurialBean item)
    {
        ImageView img = helper.getView(R.id.img);
        setImgSize(img);
        GlideLoad.load(img, item.getImg());
    }

    /**
     * 设置ImageView尺寸
     *
     * @param img
     */
    private void setImgSize(ImageView img)
    {
        if (img == null)
        {
            return;
        }
        int width = DisplayUtil.getScreenSize(BtApplication.getApplication()).widthPixels;
        width -= (BtApplication.getApplication().getResources().getDimensionPixelSize(R.dimen.home_tutorial_plr) * 2);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) img.getLayoutParams();
        if (params.width != width)
        {
            params.width = width;
            //宽高比例为23：8
            params.height = width * 8 / 23;
            img.setLayoutParams(params);
        }
    }
}
