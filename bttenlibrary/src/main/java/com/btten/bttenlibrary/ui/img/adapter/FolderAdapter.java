package com.btten.bttenlibrary.ui.img.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.ui.img.bean.Folder;
import com.btten.bttenlibrary.util.ResourceHelper;
import com.btten.bttenlibrary.util.glide.GlideUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹Adapter Created by Nereo on 2015/4/7.
 */
public class FolderAdapter extends BaseAdapter
{

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Folder> mFolders = new ArrayList<Folder>();

    int mImageSize;

    int lastSelected = 0;

    public FolderAdapter(Context context)
    {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageSize = mContext.getResources().getDimensionPixelOffset(ResourceHelper
                .getInstance(BtApplication.getApplication().getApplicationContext()).getDimenId("folder_cover_size"));
    }

    /**
     * 设置数据集
     *
     * @param folders
     */
    public void setData(List<Folder> folders)
    {
        if (folders != null && folders.size() > 0)
        {
            mFolders = folders;
        } else
        {
            mFolders.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return mFolders.size() + 1;
    }

    @Override
    public Folder getItem(int i)
    {
        if (i == 0)
            return null;
        return mFolders.get(i - 1);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder holder;
        if (view == null)
        {
            view = mInflater.inflate(ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext())
                    .getLayoutId("list_item_folder"), viewGroup, false);
            holder = new ViewHolder(view);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        if (holder != null)
        {
            if (i == 0)
            {
                holder.name.setText("所有图片");
                holder.size.setText(getTotalImageSize() + "张");
                if (mFolders.size() > 0)
                {
                    Folder f = mFolders.get(0);
//                    RequestOptions options = new RequestOptions();
//                    options.placeholder(R.drawable.ic_default_adimage)
//                            .error(R.drawable.ic_default_adimage);
//                    Glide.with(mContext).load(new File(f.cover.path)).apply(options).into(holder.cover);
                    GlideUtils.load(mContext,new File(f.cover.path),holder.cover);
                }
            } else
            {
                holder.bindData(getItem(i));
            }
            if (lastSelected == i)
            {
                holder.indicator.setVisibility(View.VISIBLE);
            } else
            {
                holder.indicator.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    private int getTotalImageSize()
    {
        int result = 0;
        if (mFolders != null && mFolders.size() > 0)
        {
            for (Folder f : mFolders)
            {
                result += f.images.size();
            }
        }
        return result;
    }

    public void setSelectIndex(int i)
    {
        if (lastSelected == i)
            return;

        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex()
    {
        return lastSelected;
    }

    class ViewHolder
    {
        ImageView cover;
        TextView name;
        TextView size;
        ImageView indicator;

        ViewHolder(View view)
        {
            cover = (ImageView) view.findViewById(
                    ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getId("cover"));
            name = (TextView) view.findViewById(
                    ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getId("name"));
            size = (TextView) view.findViewById(
                    ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getId("size"));
            indicator = (ImageView) view.findViewById(ResourceHelper
                    .getInstance(BtApplication.getApplication().getApplicationContext()).getId("indicator"));
            view.setTag(this);
        }

        void bindData(Folder data)
        {
            name.setText(data.name);
            size.setText(data.images.size() + "张");
            // 显示图片
//            RequestOptions options = new RequestOptions();
//            options.placeholder(R.drawable.ic_default_adimage)
//                    .error(R.drawable.ic_default_adimage);
//            Glide.with(mContext).load(new File(data.cover.path)).apply(options).into(cover);
            GlideUtils.load(mContext,new File(data.cover.path),cover);
        }
    }

}
