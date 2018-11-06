package com.fx.secondbar.ui.date;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DensityUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fx.secondbar.R;

/**
 * function:添加图片列表适配器
 * author: frj
 * modify date: 2018/10/11
 */
public class AdAddImg extends BaseMultiItemQuickAdapter<AdAddImg.Entity, BaseViewHolder>
{

    private Context context;
    //项宽度
    private int itemWidth;
    //Img宽度
    private int imgWidth;

    public AdAddImg(Context context, int itemWidth)
    {
        super(null);
        addItemType(Entity.TYPE_ADD, R.layout.ad_add_img);
        addItemType(Entity.TYPE_IMG, R.layout.ad_send_img_item);
        this.context = context;
        this.itemWidth = itemWidth;
        imgWidth = itemWidth - DensityUtil.dip2px(context, 17) / 2;
    }

    @Override
    protected void convert(BaseViewHolder helper, Entity item)
    {
        FrameLayout content = helper.getView(R.id.content);
        ImageView img = helper.getView(R.id.img);
        setViewSize(itemWidth, content);
        setViewSize(imgWidth, img);
        if (Entity.TYPE_ADD == item.getItemType())
        {

        } else if (Entity.TYPE_IMG == item.getItemType())
        {
            helper.addOnClickListener(R.id.ib_delete);
            GlideApp.with(context).load(item.getPath()).centerCrop().into(img);
        }
    }

    /**
     * 设置控件大小
     *
     * @param width 控件尺寸，宽高相等
     * @param view  控件
     */
    private void setViewSize(int width, View view)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params.width != width || params.height != width)
        {
            params.width = width;
            params.height = width;
            view.setLayoutParams(params);
        }
    }

    /**
     * 类型实体信息
     */
    public static class Entity implements MultiItemEntity
    {
        /**
         * 类型：新增按钮
         */
        public static final int TYPE_ADD = 0;
        /**
         * 类型：图片预览
         */
        public static final int TYPE_IMG = 1;

        private int type;
        private String path;


        public Entity(int type)
        {
            this.type = type;
        }

        public Entity(int type, String path)
        {
            this.type = type;
            this.path = path;
        }

        public int getType()
        {
            return type;
        }

        public void setType(int type)
        {
            this.type = type;
        }

        public String getPath()
        {
            return path;
        }

        public void setPath(String path)
        {
            this.path = path;
        }

        @Override
        public int getItemType()
        {
            return type;
        }
    }
}
