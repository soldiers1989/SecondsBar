package com.fx.secondbar.ui.home.item;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.ui.date.AcDateDetail;
import com.fx.secondbar.ui.home.item.adapter.AdDateTa;
import com.fx.secondbar.view.ViewPagerLayoutManager;

import java.util.ArrayList;

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
            if (adapter == null)
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
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getResources().getDimensionPixelSize(R.dimen.home_tutorial_item_space), false, true, true));
        adapter.bindToRecyclerView(recyclerView);
        ArrayList<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter.setNewData(list);

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
            }
        });
    }

    /**
     * 获取数据
     *
     * @param page 页码，从0开始
     */
    private void getData(final int page)
    {
//        HttpManager.getTurials(page, PAGE_NUM, new Subscriber<List<TurialBean>>()
//        {
//            @Override
//            public void onCompleted()
//            {
//
//            }
//
//            @Override
//            public void onError(Throwable e)
//            {
//                if (isNetworkCanReturn())
//                {
//                    return;
//                }
//                ShowToast.showToast(HttpManager.checkLoadError(e));
//            }
//
//            @Override
//            public void onNext(List<TurialBean> turialBeans)
//            {
//                if (isNetworkCanReturn())
//                {
//                    return;
//                }
//                currPage = page;
//                if (PAGE_START == page)
//                {
//                    adapter.setNewData(turialBeans);
//                } else
//                {
//                    adapter.addData(turialBeans);
//                }
//                if (turialBeans.size() >= PAGE_NUM)
//                {
//                    adapter.loadMoreComplete();
//                } else
//                {
//                    adapter.loadMoreEnd();
//                }
//            }
//        });
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
                jump(AcDateDetail.class);
                break;
        }
    }
}
