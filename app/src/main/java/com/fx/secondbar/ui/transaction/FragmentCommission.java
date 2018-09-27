package com.fx.secondbar.ui.transaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.TransactionBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.ProgressDialogUtil;

import rx.Subscriber;

/**
 * function:委托界面
 * author: frj
 * modify date: 2018/9/25
 */
public class FragmentCommission extends FragmentTransactionItem implements SwipeRefreshLayout.OnRefreshListener
{
    /**
     * 当前委托
     */
    public static final int TYPE_CURR = 1;
    /**
     * 历史委托
     */
    public static final int TYPE_HISTORY = 2;


    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdCommission adapter;
    //当前类型
    private int type;

    private ProgressDialog dialog;

    public static FragmentCommission newInstance(int type)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, type);
        FragmentCommission fragment = new FragmentCommission();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_commission, container, false);
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
        dialog = ProgressDialogUtil.getProgressDialog(getActivity(), getString(R.string.progress_tips), true);
        type = getArguments().getInt(KEY);
        //是否将要刷新数据
        if (isPrepareRefresh)
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            isPrepareRefresh = false;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //是否将要刷新数据
        if (isPrepareRefresh)
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            isPrepareRefresh = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!isHidden())
        {
            //是否将要刷新数据
            if (isPrepareRefresh)
            {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
                isPrepareRefresh = false;
            }
        }
    }

    /**
     * 刷新数据
     */
    private void refreshData(String peopleId)
    {
//        if (dialog != null)
//        {
//            dialog.show();
//        }
        HttpManager.getTransactionCenter(peopleId, new Subscriber<TransactionBean>()
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
//                if (dialog != null)
//                {
//                    dialog.dismiss();
//                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(TransactionBean transactionBean)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
//                if (dialog != null)
//                {
//                    dialog.dismiss();
//                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                String personName = transactionBean.getName();
                if (!TextUtils.isEmpty(transactionBean.getZjm()))
                {
                    personName += "(" + transactionBean.getZjm() + ")";
                }
                adapter = new AdCommission(personName);
                adapter.bindToRecyclerView(recyclerView);
                if (TYPE_CURR == type)
                {
                    adapter.setNewData(transactionBean.getList_current());
                } else
                {
                    adapter.setNewData(transactionBean.getList_history());
                }
            }
        });
    }

    @Override
    public void onRefresh()
    {
        refreshData(peopleId);
    }
}
