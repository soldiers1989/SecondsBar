package com.btten.bttenlibrary.view;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.btten.bttenlibrary.R;
import com.btten.bttenlibrary.base.adapter.BtAdapter;
import com.btten.bttenlibrary.base.load.LoadManager;
import com.btten.bttenlibrary.view.xlist.XListView;

/**
 * function:封装的ListView控件，满足上拉加载与下拉刷新，同时满足加载时
 * author: Administrator
 * modify date: 2016/12/30
 */
public class BttenListView extends FrameLayout
{
    //加载图层管理器
    private LoadManager mLoadManager;
    //上拉加载与下拉刷新控件
    private XListView mListView;
    //列表适配器
    private BtAdapter mAdapter;

    public BttenListView(Context context)
    {
        super(context);
    }

    public BttenListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BttenListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BttenListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化
     */
    private void init()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.btten_layout_list, this);
        mLoadManager = new LoadManager(view);
        mListView = (XListView) view.findViewById(R.id.listView);
    }

    /**
     * 设置列表适配器
     *
     * @param adapter 列表适配器
     */
    public void setAdapter(BtAdapter adapter)
    {
        if (adapter == null)
        {
            return;
        }
        mAdapter = adapter;
        mAdapter.registerDataSetObserver(new BtDataSetObserve());
    }

    private class BtDataSetObserve extends DataSetObserver
    {

    }
}
