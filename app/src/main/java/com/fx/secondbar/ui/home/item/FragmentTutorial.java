package com.fx.secondbar.ui.home.item;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.DateBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.AcWebBrowse;
import com.fx.secondbar.ui.MainActivity;
import com.fx.secondbar.ui.date.AcDateDetail;
import com.fx.secondbar.ui.home.item.adapter.AdDateTa;
import com.fx.secondbar.util.RequestCode;
import com.fx.secondbar.view.ViewPagerLayoutManager;

import java.util.List;

import rx.Subscriber;

/**
 * function:教程
 * author: frj
 * modify date: 2018/9/7
 */
public class FragmentTutorial extends FragmentViewPagerBase
{

    private RecyclerView recyclerView;
    private AdDateTa adapter;
    //当前页码
    private int currPage = -1;

    @Override
    public void onStarShow()
    {
        if (currPage == -1)
        {
            if (adapter == null && getActivity() != null)
            {
                initView();
                initListener();
                initData();
            }
            getData(PAGE_START);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home_tutorial, container, false);
    }

    @Override
    protected void initView()
    {
        recyclerView = findView(R.id.recyclerView);
        findView(R.id.ib_send).setOnClickListener(this);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        int height = DisplayUtil.getScreenSize(FxApplication.getInstance()).heightPixels;
        height -= FxApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.toolbar_height);
        height -= FxApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.home_tabs_h);
        height -= FxApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.main_bottom_h);
        height -= FxApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.main_bottom_line);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            height -= DisplayUtil.getStatusBarHeight(FxApplication.getInstance());
        }
        adapter = new AdDateTa(height);
        recyclerView.getLayoutParams().height = height;
        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.bindToRecyclerView(recyclerView);

        layoutManager.setOnViewPagerListener(new ViewPagerLayoutManager.OnViewPagerListener()
        {
            @Override
            public void onInitComplete()
            {

            }

            @Override
            public void onPageRelease(boolean b, int i)
            {

            }

            @Override
            public void onPageSelected(int i, boolean b)
            {

            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1);
            }
        }, recyclerView);
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
                bundle.putString(KEY_STR, FragmentTutorial.this.adapter.getItem(position).getStroke_ID());
                jump(AcDateDetail.class, bundle, RequestCode.REQUEST_CODE_TO_DATE_DETAIL);
            }
        });
        getData(PAGE_START);
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!isHidden())
        {
            if (adapter != null)
            {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (adapter != null)
        {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取数据
     *
     * @param page 页码，从0开始
     */
    private void getData(final int page)
    {
        HttpManager.getDateList(page, PAGE_NUM, new Subscriber<List<DateBean>>()
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
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(List<DateBean> turialBeans)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                currPage = page;
                if (PAGE_START == page)
                {
                    adapter.setNewData(turialBeans);
                } else
                {
                    adapter.addData(turialBeans);
                }
                if (turialBeans.size() >= PAGE_NUM)
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
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.ib_send:
                Bundle bundle = new Bundle();
                bundle.putString(KEY_STR, "发布说明");
                bundle.putString(KEY, "http://www.feixingtech.com:8080/static/mb-front/getText.html?type=20");
                jump(AcWebBrowse.class, bundle, false);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (RequestCode.REQUEST_CODE_TO_DATE_DETAIL == requestCode && ActivitySupport.RESULT_OK == resultCode)
        {
            ((MainActivity) getActivity()).jumpToPersonal();
        }
    }
}
