package com.btten.bttenlibrary.base.adapter;

import java.util.ArrayList;
import java.util.List;

import com.btten.bttenlibrary.util.ResourceHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 适配器基类
 *
 * @param <T>
 * @author frj
 */
public abstract class BtAdapter<T> extends BaseAdapter
{

    protected List<T> list;
    protected LayoutInflater mInflater;
    protected Context context;

    public BtAdapter(Context context)
    {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        if (list != null)
        {
            return list.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position)
    {
        if (list != null)
        {
            return list.get(position);
        } else
        {
            return null;
        }
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * 移除项
     *
     * @param position 项位置
     */
    public void remove(int position)
    {
        if (list != null)
        {
            if (list.size() > position)
            {
                list.remove(position);
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 添加数据
     *
     * @param t
     */
    public void add(T t)
    {
        if (t == null)
        {
            return;
        }
        if (list == null)
        {
            list = new ArrayList<T>();
        }
        list.add(t);
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clearList()
    {
        if (list != null)
        {
            list.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 添加集合对象
     *
     * @param list       集合对象
     * @param isLoadMore 表示是否是加载更多
     */
    public void addList(List<T> list, boolean isLoadMore)
    {
        if (list != null)
        {
            if (isLoadMore)
            {
                if (this.list == null)
                {
                    this.list = list;
                } else
                {
                    this.list.addAll(list);
                }
            } else
            {
                if (this.list == null)
                {
                    this.list = list;
                } else
                {
                    this.list.clear();
                    this.list.addAll(list);
                }
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 返回当前适配器的项的集合
     *
     * @return
     */
    public List<T> getList()
    {
        return list;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    /**
     * 跳转界面
     *
     * @param clazz    要跳转的界面
     * @param bundle   参数选项值
     * @param isFinish 是否关闭当前的界面
     */
    protected void jump(Class<?> clazz, Bundle bundle, boolean isFinish)
    {
        Intent intent = new Intent(context, clazz);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        // 屏幕右边进，屏幕内从左边移出 动画
        ((Activity) context).overridePendingTransition(ResourceHelper.getInstance(context).getAnimId("right_in_anim"),
                ResourceHelper.getInstance(context).getAnimId("left_out_anim"));
        if (isFinish)
        {
            ((Activity) context).finish();
        }
    }
}
