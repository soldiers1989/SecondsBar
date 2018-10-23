package com.fx.secondbar.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.ResMall;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.MainActivity;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.util.RequestCode;

import rx.Subscriber;

/**
 * function:商城页各分类的子页
 * author: frj
 * modify date: 2018/9/9
 */
public class FragmentMallItem extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private AdMall adapter;

    private int currPage = -1;
    //栏目id
    private String type;

    public static FragmentMallItem newInstance(String type)
    {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STR, type);
        FragmentMallItem fragment = new FragmentMallItem();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home_time, container, false);
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
        type = getArguments().getString(KEY_STR);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getContext().getResources().getDimensionPixelSize(R.dimen.mall_item_space), false, false, false));
        adapter = new AdMall();
        adapter.bindToRecyclerView(recyclerView);
//        adapter.setNewData(getDatas());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                if (isFastDoubleClick(view))
                {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(KEY_STR, FragmentMallItem.this.adapter.getData().get(position).getMerchandise_ID());
                jump(AcMallDetail.class, bundle, RequestCode.REQUEST_CODE_TO_MALL_DETAIL);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1, type);
            }
        }, recyclerView);
        if (TextUtils.isEmpty(type))
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void onRefresh()
    {
        getData(PAGE_START, type);
    }

    @Override
    public void onStarShow()
    {
        if (currPage == -1)
        {
            if (swipeRefreshLayout != null)
            {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        }
    }

    /**
     * 获取商品列表
     *
     * @param page 页码
     * @param type 类型
     */
    private void getData(final int page, String type)
    {
        HttpManager.getMall(page, PAGE_NUM, type, new Subscriber<ResMall>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                e.printStackTrace();
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(ResMall resMall)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                currPage = page;
                if (page == PAGE_START)
                {
                    adapter.setNewData(resMall.getListMerchandise());
                } else
                {
                    adapter.addData(resMall.getListMerchandise());
                }
                if (VerificationUtil.getSize(resMall.getListMerchandise()) >= PAGE_NUM)
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (RequestCode.REQUEST_CODE_TO_MALL_DETAIL == requestCode && ActivitySupport.RESULT_OK == resultCode)
        {
            ((MainActivity) getActivity()).jumpToPersonal();
        }
    }
}
