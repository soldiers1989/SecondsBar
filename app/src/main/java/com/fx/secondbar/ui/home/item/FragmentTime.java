package com.fx.secondbar.ui.home.item;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.BannerBean;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.DynamicBean;
import com.fx.secondbar.bean.IndexTimeBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.AcInformationDetail;
import com.fx.secondbar.ui.home.item.adapter.AdTime;
import com.fx.secondbar.ui.mall.AcMallDetail;
import com.fx.secondbar.util.GlideLoad;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * function:首页-时间场
 * author: frj
 * modify date: 2018/9/7
 */
public class FragmentTime extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ConvenientBanner<BannerBean> convenientBanner;

    private AdTime adapter;

    @Override
    public void onStarShow()
    {
        if (convenientBanner != null)
        {
            if (!convenientBanner.isTurning())
            {
                convenientBanner.startTurning(2000);
            }
        }
        if (adapter != null && !VerificationUtil.noEmpty(adapter.getData()))
        {
            getDatas();
        }
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
        swipeRefreshLayout.setEnabled(false);
        adapter = new AdTime();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                if (isFastDoubleClick(view))
                {
                    return;
                }
                AdTime adTime = (AdTime) adapter;
                AdTime.TimeEntity entity = adTime.getItem(position);
                if (AdTime.TimeEntity.TYPE_COMMODITY == entity.getItemType())
                {   //商品
                    CommodityBean commodityBean = entity.getCommodityBean();
                    if (commodityBean != null)
                    {
                        jump(AcMallDetail.class, commodityBean.getMerchandise_ID());
                    }
                } else if (AdTime.TimeEntity.TYPE_MULTI_IMG == entity.getItemType() || AdTime.TimeEntity.TYPE_SINGLE_IMG == entity.getItemType())
                {
                    jump(AcInformationDetail.class, entity.getInfomationBean().getNews_ID(), false);
                }
            }
        });
        if (convenientBanner != null)
        {
            if (!convenientBanner.isTurning())
            {
                convenientBanner.startTurning(2000);
            }
        }
    }


    /**
     * 获取数据
     */
    private void getDatas()
    {
        HttpManager.getIndexTime(new Subscriber<IndexTimeBean>()
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
            public void onNext(IndexTimeBean indexTimeBean)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                adapter.removeAllHeaderView();
                if (VerificationUtil.getSize(indexTimeBean.getListBanner()) > 0)
                {
                    adapter.addHeaderView(createHeadView(indexTimeBean.getListBanner()));
                }
                adapter.setNewData(handleData(indexTimeBean));
            }
        });
    }

    /**
     * 处理数据
     *
     * @param bean
     * @return
     */
    private List<AdTime.TimeEntity> handleData(IndexTimeBean bean)
    {
        List<AdTime.TimeEntity> list = new ArrayList<>();
        if (bean != null)
        {
            //判断数据是否存在交易信息
            if (VerificationUtil.noEmpty(bean.getListOrder()))
            {
                list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_TITLE, "交易动态"));
                for (DynamicBean dynamicBean : bean.getListOrder())
                {
                    list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_DYNAMIC, dynamicBean));
                }
            }
            //判断数据是否存在名人申购信息
            if (VerificationUtil.noEmpty(bean.getListPurchase()))
            {
                list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_TITLE, "名人申购"));
                list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_PERSON, bean.getListPurchase()));
            }
            //判断数据是否存在名人商品信息
            if (VerificationUtil.noEmpty(bean.getListMerchandise()))
            {
                list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_TITLE, "名人商品"));
                for (CommodityBean commodityBean : bean.getListMerchandise())
                {
                    list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_COMMODITY, commodityBean));
                }
            }
            //判断数据是否存在名人资讯信息
            if (VerificationUtil.noEmpty(bean.getListNews()))
            {
                list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_TITLE, "名人资讯"));
                for (InfomationBean infomationBean : bean.getListNews())
                {
                    String pictures = infomationBean.getPictures();
                    String[] picture = null;
                    if (!TextUtils.isEmpty(pictures))
                    {
                        picture = pictures.split(",");
                    }
                    if (picture != null && picture.length > 1)
                    {
                        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_MULTI_IMG, infomationBean));
                    } else
                    {
                        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_SINGLE_IMG, infomationBean));
                    }
                }
            }
        }
        return list;
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
                return new LoadImageHolder(itemView);
            }

            @Override
            public int getLayoutId()
            {
                return R.layout.layout_banner;
            }
        }, bannerBeans).setPageIndicator(new int[]{R.mipmap.ic_indicator, R.mipmap.ic_indicator_sel});
        convenientBanner.startTurning(2000);
        return view;
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
                    convenientBanner.startTurning(2000);
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

    @Override
    public void onRefresh()
    {
        getDatas();
    }

    /**
     * 加载图片Holder
     */
    static class LoadImageHolder extends Holder<BannerBean>
    {

        private ImageView img;

        public LoadImageHolder(View itemView)
        {
            super(itemView);
        }

        @Override
        protected void initView(View itemView)
        {
            img = (ImageView) itemView;
        }

        @Override
        public void updateUI(BannerBean data)
        {
            if (img != null)
            {
                GlideLoad.load(img, data.getImg());
            }
        }
    }
}
