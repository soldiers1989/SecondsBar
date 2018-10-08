package com.fx.secondbar.ui.notify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.MessageBean;
import com.fx.secondbar.http.HttpManager;

import java.util.List;

import rx.Subscriber;

/**
 * function:消息列表；直接展示列表数据，不显示已读和未读
 * author: frj
 * modify date: 2018/10/8
 */
public class AcMessageListItem extends ActivitySupport implements SwipeRefreshLayout.OnRefreshListener
{

    /**
     * 系统消息类型
     */
    public static final int TYPE_SYSTEM = 0;
    /**
     * 公告消息类型
     */
    public static final int TYPE_ANNO = 1;

    private TextView tv_title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdMessage adapter;

    //当前界面类型
    private int type;
    //当前页码
    private int currPage;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_message_list_item;
    }

    @Override
    protected void initView()
    {
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_title = findView(R.id.tv_title);
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
        type = getIntent().getIntExtra(KEY, TYPE_SYSTEM);
        if (TYPE_ANNO == type)
        {
            tv_title.setText("公告消息");
        } else
        {
            tv_title.setText("系统消息");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 12), true, false, true));
        adapter = new AdMessage();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener()
        {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position)
            {
                if (isFastDoubleClick(view))
                {
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY, AcMessageListItem.this.adapter.getItem(position));
                jump(AcMessageDetail.class, bundle, false);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                //2表示公告消息；1表示系统消息
                getData(currPage + 1, TYPE_ANNO == type ? "2" : "1");
            }
        }, recyclerView);
        adapter.setEmptyView(createEmpty());
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    /**
     * 创建空页面
     *
     * @return
     */
    private View createEmpty()
    {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty, null);
        return view;
    }

    /**
     * 获取数据
     *
     * @param page 页码
     * @param type 类型，1表示系统消息；2表示公告消息
     */
    private void getData(final int page, String type)
    {
        HttpManager.getMessageList(type, page, PAGE_NUM, new Subscriber<List<MessageBean>>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isDestroy())
                {
                    return;
                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                e.printStackTrace();
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(List<MessageBean> messageBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                currPage = page;
                if (PAGE_START == page)
                {
                    adapter.setNewData(messageBeans);
                } else
                {
                    adapter.addData(messageBeans);
                }
                if (VerificationUtil.getSize(messageBeans) >= PAGE_NUM)
                {
                    adapter.loadMoreComplete();
                } else
                {
                    adapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        initData();
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[0];
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[0];
    }

    @Override
    public void onRefresh()
    {
        //2表示公告消息；1表示系统消息
        getData(PAGE_START, TYPE_ANNO == type ? "2" : "1");
    }

    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
