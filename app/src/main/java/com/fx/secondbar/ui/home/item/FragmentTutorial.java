package com.fx.secondbar.ui.home.item;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.TurialBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.AcTutorialDetail;
import com.fx.secondbar.ui.home.item.adapter.AdTutorial;

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
    private AdTutorial adapter;
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
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        adapter = new AdTutorial(R.layout.ad_tutorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getResources().getDimensionPixelSize(R.dimen.home_tutorial_item_space), false, true, true));
        adapter.bindToRecyclerView(recyclerView);
//        adapter.setNewData(getDatas());
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
                bundle.putParcelable(KEY, FragmentTutorial.this.adapter.getItem(position));
                jump(AcTutorialDetail.class, bundle, false);
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

}
