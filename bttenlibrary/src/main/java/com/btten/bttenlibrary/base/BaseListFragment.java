package com.btten.bttenlibrary.base;

import android.view.View;
import android.widget.AdapterView;

import com.btten.bttenlibrary.base.adapter.BtAdapter;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.base.load.BaseLoadManager;
import com.btten.bttenlibrary.base.load.OnReloadListener;
import com.btten.bttenlibrary.http.CallBackData;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.btten.bttenlibrary.view.xlist.IXListViewListener;
import com.btten.bttenlibrary.view.xlist.XListView;

import java.util.List;

/**
 * function:基础的列表Fragment界面
 * author: frj
 * modify date: 2017/1/3
 */

public abstract class BaseListFragment<T, K> extends FragmentSupport implements IXListViewListener, CallBackData<T>
{
    //列表项点击监听
    private AdapterView.OnItemClickListener mOnItemClickListener;

    //表示当前页码 -1表示无效
    protected int currPage = -1;

    @Override
    protected void initListener()
    {
        if (getListView() == null)
        {
            throw new NullPointerException("You should implement the getListView () method and return a non-null object.");
        }
        getListView().setXListViewListener(this);
        getListView().setOnItemClickListener(this);
    }

    @Override
    protected void initData()
    {
        if (getListView() == null)
        {
            throw new NullPointerException("You should implement the getListView () method and return a non-null object.");
        }
        if (getAdapter() == null)
        {
            throw new NullPointerException("You should implement the getAdapter () method and return a non-null object.");
        }
        if (getLoadManager() == null)
        {
            throw new NullPointerException("You should implement the getLoadManager () method and return a non-null object.");
        }
        getListView().setAdapter(getAdapter());
        //开始加载
        getLoadManager().loadding();
        //请求数据
        request(PAGE_START);
    }

    /**
     * 获取当前界面的XListView控件对象
     *
     * @return XListView控件对象
     */
    protected abstract XListView getListView();

    /**
     * 获取当前界面列表的适配器对象
     *
     * @return 适配器对象
     */
    protected abstract BtAdapter<K> getAdapter();

    /**
     * 获取当前界面的加载器对象
     *
     * @return 加载器对象
     */
    protected abstract BaseLoadManager getLoadManager();

    /**
     * 设置项点击事件监听
     *
     * @param onItemClickListener 项点击事件监听实体
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 表示发起网络请求
     *
     * @param page 页码
     */
    protected abstract void request(int page);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        super.onItemClick(parent, view, position, id);
        position = position - 1;
        if (mOnItemClickListener != null)
        {
            mOnItemClickListener.onItemClick(parent, view, position, id);
        }
    }

    /**
     * 停止刷新与加载更多
     */
    protected void stopRefreshAndLoad()
    {
        getListView().stopRefresh();
        getListView().stopLoadMore();
    }

    @Override
    public void onRefresh()
    {
        request(PAGE_START);
    }

    @Override
    public void onLoadMore()
    {
        request(currPage + 1);
    }

    @Override
    public void onSuccess(ResponseBean<T> t)
    {
        /*
         * 当加载或刷新成功，但数据为空时，将会显示空的加载图层，此时currPage值将不会更新。
          * 当加载更多成功，但数据为空时，将会使加载更多不可用，当前的currPage是上一次加载成功并且有数据的结果，此时currPage值也将不会进行更新。
         */

        if (VerificationUtil.noEmpty(t.getData()))
        {
            if (t.getData() instanceof List)
            {
                List<K> list = (List<K>) t.getData();
                //根据集合大小，判断是否启用加载更多的功能
                getListView().setPullLoadEnable(PAGE_NUM == list.size());
                //根据当前的ListView的加载状态，判断是否是加载更多
                getAdapter().addList(list, getLoadManager().isLoadding());
                //刷新与加载成功后，当前的页码一律为PAGE_START；加载更多成功时，在currPage基础上+1
                currPage = getLoadManager().isLoadding() ? currPage + 1 : PAGE_START;
                if (getLoadManager().isLoadding())
                {
                    getLoadManager().loadSuccess();
                } else
                {
                    stopRefreshAndLoad();
                }
            } else
            {
                throw new IllegalArgumentException("Maybe <T> is not a valid List collection.");
            }
        } else
        {
            //如果加载或刷新过程中，出现空数据的情况，一律显示空界面
            if (getLoadManager().isLoadding() || getListView().ismPullRefreshing())
            {
                getLoadManager().loadEmpty("暂无数据");
            } else
            {
                getListView().setPullLoadEnable(false);
                stopRefreshAndLoad();
            }
        }
    }

    @Override
    public void onFail(String failMsg)
    {
        if (getLoadManager().isLoadding())
        {
            getLoadManager().loadFail(failMsg, new OnReloadListener()
            {
                @Override
                public void onReload()
                {
                    //由于加载图层只会在第一页的时候显示，因此，这里恒为PAGE_START
                    request(PAGE_START);
                }
            });
        } else
        {
            ShowToast.showToast(failMsg);
        }
    }
}
