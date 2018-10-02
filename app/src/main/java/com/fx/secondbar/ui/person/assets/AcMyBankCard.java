package com.fx.secondbar.ui.person.assets;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.BankBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.ProgressDialogUtil;

import java.util.List;

import rx.Subscriber;

/**
 * function:我的银行卡
 * author: frj
 * modify date: 2018/9/22
 */
public class AcMyBankCard extends ActivitySupport implements SwipeRefreshLayout.OnRefreshListener
{
    /**
     * 添加银行卡请求码
     */
    private static final int REQUEST_CODE_ADD_CARD = 10;

    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_add;
    private RecyclerView recyclerView;
    private AdMyBankCard adapter;

    private ProgressDialog dialog;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_my_bank_card;
    }

    @Override
    protected void initView()
    {
        swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
        btn_add = findView(R.id.btn_add);
        recyclerView = findView(R.id.recyclerView);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_add.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData()
    {
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 1), false, false, false));
        adapter = new AdMyBankCard();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener()
        {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position)
            {
                //删除银行卡
                AlertDialog.Builder builder = new AlertDialog.Builder(AcMyBankCard.this);
                builder.setMessage("您确定要删除该银行卡吗？").setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        deleteBank(AcMyBankCard.this.adapter.getItem(position).getBank_ID());
                    }
                }).setNegativeButton("取消", null).show();
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                //点击选中返回上一个页面
                Intent intent = new Intent();
                intent.putExtra(KEY_STR, AcMyBankCard.this.adapter.getItem(position).getBankno());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    /**
     * 获取我的银行卡数据
     */
    private void getData()
    {
        HttpManager.getMyBank(new Subscriber<List<BankBean>>()
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
                e.printStackTrace();
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(List<BankBean> bankBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                adapter.setNewData(bankBeans);
            }
        });
    }

    /**
     * 删除银行卡
     *
     * @param bankId 银行卡id
     */
    private void deleteBank(String bankId)
    {
        if (dialog != null && !dialog.isShowing())
        {
            dialog.show();
        }
        HttpManager.deleteBank(bankId, new Subscriber<ResponseBean>()
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
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                e.printStackTrace();
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(ResponseBean responseBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                ShowToast.showToast("删除成功");
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_add:
                jump(AcAddBankCard.class, REQUEST_CODE_ADD_CARD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_ADD_CARD == requestCode)
        {
            if (RESULT_OK == resultCode)
            {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        }
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
        getData();
    }
}
