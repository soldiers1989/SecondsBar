package com.fx.secondbar.ui.home.item;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.BannerBean;
import com.fx.secondbar.bean.WBBean;
import com.fx.secondbar.bean.WBData;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.AcShareDialog;
import com.fx.secondbar.ui.home.item.adapter.AdWb;
import com.fx.secondbar.util.Constants;

import java.util.List;

import rx.Subscriber;

/**
 * function:微博页
 * author: frj
 * modify date: 2018/9/7
 */
public class FragmentWb extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    private TextView tv_wb;
    private TextView tv_live;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdWb adapter;

    private ConvenientBanner<BannerBean> convenientBanner;

    private int currPage = -1;

    @Override
    public void onStarShow()
    {
        if (currPage == -1)
        {
            if (swipeRefreshLayout == null)
            {
                return;
            }
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }

        if (convenientBanner != null)
        {
            if (!convenientBanner.isTurning())
            {
                convenientBanner.startTurning(Constants.DURATION);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home_wb, container, false);
    }


    @Override
    protected void initView()
    {
        swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
        recyclerView = findView(R.id.recyclerView);
        tv_wb = findView(R.id.tv_wb);
        tv_live = findView(R.id.tv_live);
    }

    @Override
    protected void initListener()
    {
        tv_wb.setOnClickListener(this);
        tv_live.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdWb((ActivitySupport) getActivity());
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1);
            }
        }, recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener()
        {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position)
            {
                if (isFastDoubleClick(view))
                {
                    return;
                }
                WBBean item = FragmentWb.this.adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(AcShareDialog.KEY_TYPE, AcShareDialog.TYPE_POSTER_CONTENT);
                bundle.putString(AcShareDialog.KEY_TITLE, item.getTitle());
                bundle.putString(AcShareDialog.KEY_CONTENT, item.getContent());
                bundle.putString(AcShareDialog.KEY_URL, "");
                jump(AcShareDialog.class, bundle, false);
            }
        });

        if (convenientBanner != null)
        {
            if (!convenientBanner.isTurning())
            {
                convenientBanner.startTurning(Constants.DURATION);
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!isHidden())
        {
            if (convenientBanner != null)
            {
                if (!convenientBanner.isTurning())
                {
                    convenientBanner.startTurning(Constants.DURATION);
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (isHidden())
        {
            if (convenientBanner != null)
            {
                if (convenientBanner.isTurning())
                {
                    convenientBanner.stopTurning();
                }
            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (convenientBanner != null)
        {
            if (convenientBanner.isTurning())
            {
                convenientBanner.stopTurning();
            }
        }
    }


    /**
     * 创建头部控件
     *
     * @return
     */
    private View createHeadView(List<BannerBean> bannerBeans)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_time_head, recyclerView, false);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        //宽高比为25:12
        params.height = DisplayUtil.getScreenSize(getContext()).widthPixels * 12 / 25;
        view.setLayoutParams(params);
        convenientBanner = view.findViewById(R.id.convenientBanner);
        convenientBanner.setPages(new CBViewHolderCreator()
        {
            @Override
            public Holder createHolder(View itemView)
            {
                return new FragmentTime.LoadImageHolder(itemView);
            }

            @Override
            public int getLayoutId()
            {
                return R.layout.layout_banner;
            }
        }, bannerBeans).setPageIndicator(new int[]{R.mipmap.ic_indicator, R.mipmap.ic_indicator_sel});
        convenientBanner.startTurning(Constants.DURATION);
        return view;
    }

    /**
     * 获取数据
     *
     * @param page
     */
    private void getData(final int page)
    {
        HttpManager.getWbs(page, PAGE_NUM, new Subscriber<WBData>()
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
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(WBData wbData)
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
                if (PAGE_START == page)
                {
                    adapter.removeAllHeaderView();
                    if (VerificationUtil.getSize(wbData.getListBanner()) > 0)
                    {
                        adapter.addHeaderView(createHeadView(wbData.getListBanner()));
                    }
                    adapter.setNewData(wbData.getListNews());
                } else
                {
                    adapter.addData(wbData.getListNews());
                }
                if (VerificationUtil.getSize(wbData.getListNews()) >= PAGE_NUM)
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
    public void onRefresh()
    {
        getData(PAGE_START);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_wb:
                break;
            case R.id.tv_live:
                ShowToast.showToast("敬请期待");
                break;
        }
    }
}
