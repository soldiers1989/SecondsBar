package com.fx.secondbar.ui.home.item.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.WBBean;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * function:首页-微博列表适配器
 * author: frj
 * modify date: 2018/9/9
 */
public class AdWb extends BaseQuickAdapter<WBBean, BaseViewHolder>
{

    private ActivitySupport activity;

    public AdWb(ActivitySupport activity)
    {
        super(R.layout.ad_wb);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final WBBean item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        ImageButton img_share = helper.getView(R.id.img_share);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_content = helper.getView(R.id.tv_content);
        RecyclerView img_recycler = helper.getView(R.id.img_recycler);
        img_recycler.setFocusableInTouchMode(false);
        img_recycler.requestFocus();
        List<String> imgs = getImgs(item);
        int size = imgs.size();
//        img_recycler.setLayoutManager(new GridLayoutManager(img_recycler.getContext(), getRow(size)));
        img_recycler.setLayoutManager(new GridLayoutManager(img_recycler.getContext(), 3));
        AdWbImg adapter = new AdWbImg(getWidth(img_recycler.getContext(), size), imgs);
        adapter.bindToRecyclerView(img_recycler);
        if (img_recycler.getItemDecorationCount() == 0)
        {
            img_recycler.addItemDecoration(SpaceDecorationUtil.getDecoration(img.getContext().getResources().getDimensionPixelSize(R.dimen.home_wb_img_space), false, false, false));
        }
        img_recycler.setVisibility(size == 0 ? View.GONE : View.VISIBLE);

        GlideLoad.load(img, item.getAvatar(), true);
        VerificationUtil.setViewValue(tv_name, item.getUsername());
        VerificationUtil.setViewValue(tv_content, item.getContent());
        tv_content.setText(Html.fromHtml(item.getContent()));
        VerificationUtil.setViewValue(tv_time, item.getCreatedate());
        helper.addOnClickListener(R.id.img_share);
    }

    /**
     * 获取图片单列宽度
     *
     * @param size
     * @return
     */
    public int getWidth(Context context, int size)
    {
        if (size == 0)
        {
            return 0;
        }
        int width = DisplayUtil.getScreenSize(context).widthPixels;
        width = ((width - context.getResources().getDimensionPixelSize(R.dimen.home_wb_plr) * 2 - context.getResources().getDimensionPixelSize(R.dimen.home_wb_img_space)) / 3);
        return width;
    }

    /**
     * 获取图片集合
     *
     * @param bean
     * @return
     */
    private List<String> getImgs(WBBean bean)
    {
        if (bean != null)
        {
            if (VerificationUtil.noEmpty(bean.getPictures()))
            {
                String pictures = bean.getPictures();
                String[] picture = null;
                if (!TextUtils.isEmpty(pictures))
                {
                    picture = pictures.split(",");
                }
                if (picture == null)
                {
                    return Collections.emptyList();
                } else
                {
                    return Arrays.asList(picture);
                }
            } else
            {
                if (TextUtils.isEmpty(bean.getPicture()))
                {
                    return Collections.emptyList();
                }
                List<String> list = new ArrayList<>();
                list.add(bean.getPicture());
                return list;
            }
        }
        return Collections.emptyList();
    }

    /**
     * 获取图片列数
     *
     * @param size
     * @return
     */
    public int getRow(int size)
    {
        if (size == 0)
        {
            return 1;
        }
        int row = size < 3 ? size : 3;
        return row;
    }

}
