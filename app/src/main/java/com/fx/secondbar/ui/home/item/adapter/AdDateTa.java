package com.fx.secondbar.ui.home.item.adapter;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.util.GlideLoad;

/**
 * function:约Ta列表适配器
 * author: frj
 * modify date: 2018/10/22
 */
public class AdDateTa extends BaseQuickAdapter<String, BaseViewHolder>
{
    private int height;

    public AdDateTa(int height)
    {
        super(R.layout.ad_date_ta);
        this.height=height;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        ConstraintLayout content = helper.getView(R.id.content);
        ImageView img_avatar = helper.getView(R.id.img_avatar);
        ImageView img_yue = helper.getView(R.id.img_yue);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_account = helper.getView(R.id.tv_account);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_des = helper.getView(R.id.tv_des);
        ImageView img = helper.getView(R.id.img);

        ViewGroup.LayoutParams params = content.getLayoutParams();
        params.height = height;
        content.setLayoutParams(params);

        GlideLoad.loadCicle(img_avatar, item, R.mipmap.default_avatar, R.mipmap.default_avatar);
        GlideLoad.loadRound(img, "http://img4.imgtn.bdimg.com/it/u=967395617,3601302195&fm=26&gp=0.jpg", R.drawable.ic_default_adimage, R.drawable.ic_default_adimage, DensityUtil.dip2px(img.getContext(), 5));
    }
}
