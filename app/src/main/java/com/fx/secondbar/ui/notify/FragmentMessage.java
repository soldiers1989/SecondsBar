package com.fx.secondbar.ui.notify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;

/**
 * function:消息列表
 * author: frj
 * modify date: 2018/9/26
 */
public class FragmentMessage extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{
    /**
     * 已读
     */
    public static final int TYPE_READED = 1;
    /**
     * 未读
     */
    public static final int TYPE_UNREAD = 2;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdMessage adapter;

    /**
     * 系统消息或公告消息
     */
    private int type;
    /**
     * 已读消息或未读消息
     */
    private int typeRead;

    public static FragmentMessage newInstance(int type, int readType)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, type);
        bundle.putInt(KEY_STR, readType);
        FragmentMessage fragmentMessage = new FragmentMessage();
        fragmentMessage.setArguments(bundle);
        return fragmentMessage;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_message, container, false);
    }

    @Override
    public void onStarShow()
    {

    }

    @Override
    protected void initView()
    {
        swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
        recyclerView = findView(R.id.recyclerView);
    }

    @Override
    protected void initListener()
    {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData()
    {
        type = getArguments().getInt(KEY);
        typeRead = getArguments().getInt(KEY_STR);
        adapter = new AdMessage();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener()
        {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position)
            {
                jump(AcMessageDetail.class);
            }
        });
    }

    @Override
    public void onRefresh()
    {

    }
}
