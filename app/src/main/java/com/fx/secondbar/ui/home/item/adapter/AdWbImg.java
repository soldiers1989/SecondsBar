package com.fx.secondbar.ui.home.item.adapter;

import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.util.GlideLoad;

import java.util.List;

/**
 * function:微博图片展示
 * author: frj
 * modify date: 2018/9/9
 */
public class AdWbImg extends BaseQuickAdapter<String, BaseViewHolder>
{

    private int width = 0;

    public AdWbImg(int width, @Nullable List<String> data)
    {
        super(R.layout.ad_wb_img_item, data);
        this.width = width;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        ImageView img = helper.getView(R.id.img);
        setImgSize(img);
        GlideLoad.load(img, item);
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
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) img.getLayoutParams();
        if (params.width != width)
        {
            params.width = width;
            //宽高比例为1：1
            params.height = width;
            img.setLayoutParams(params);
        }
    }
}
