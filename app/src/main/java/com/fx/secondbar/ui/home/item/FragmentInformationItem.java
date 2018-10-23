package com.fx.secondbar.ui.home.item;

import android.content.Intent;
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

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.BannerBean;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.MainActivity;
import com.fx.secondbar.ui.home.AcInformationDetail;
import com.fx.secondbar.ui.home.item.adapter.AdInfomation;
import com.fx.secondbar.ui.mall.AcMallDetail;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.RequestCode;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * function:资讯信息项
 * author: frj
 * modify date: 2018/9/24
 */
public class FragmentInformationItem extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private AdInfomation adapter;

    private ConvenientBanner<BannerBean> convenientBanner;

    private int currPage = -1;

    public static FragmentInformationItem newInstance(String type, boolean isFirst)
    {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STR, type);
        bundle.putBoolean(KEY, isFirst);
        FragmentInformationItem frament = new FragmentInformationItem();
        frament.setArguments(bundle);
        return frament;
    }

    @Override
    public void onStarShow()
    {
        if (adapter != null && !VerificationUtil.noEmpty(adapter.getData()))
        {
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
        return inflater.inflate(R.layout.f_information_item, container, false);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(1, false, false, false));
        adapter = new AdInfomation();
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
                AdInfomation.InfomationEntity entity = FragmentInformationItem.this.adapter.getItem(position);
                if (AdInfomation.InfomationEntity.TYPE_MULTI_IMG == entity.getItemType() || AdInfomation.InfomationEntity.TYPE_SINGLE_IMG == entity.getItemType())
                {
                    jump(AcInformationDetail.class, entity.getInfomationBean().getNews_ID(), false);
                } else if (AdInfomation.InfomationEntity.TYPE_COMMODITY == entity.getItemType())
                {
                    CommodityBean commodityBean = entity.getCommodityBean();
                    if (commodityBean != null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString(KEY_STR, commodityBean.getMerchandise_ID());
                        jump(AcMallDetail.class, bundle, RequestCode.REQUEST_CODE_TO_MALL_DETAIL);
                    }
                }
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1, getArguments().getString(KEY_STR));
            }
        }, recyclerView);
        //判断是否是第一项
        if (getArguments().getBoolean(KEY, false))
        {
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
     * @param type
     */
    private void getData(final int page, String type)
    {
        HttpManager.getIndexInformation(page, PAGE_NUM, type, new Subscriber<IndexInformationBean>()
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
            public void onNext(IndexInformationBean indexInformationBean)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                currPage = page;
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (PAGE_START == page)
                {
                    adapter.removeAllHeaderView();
                    if (VerificationUtil.getSize(indexInformationBean.getListBanner()) > 0)
                    {
                        adapter.addHeaderView(createHeadView(indexInformationBean.getListBanner()));
                    }
                    adapter.setNewData(handleData(indexInformationBean));
                } else
                {
                    adapter.addData(handleData(indexInformationBean));
                }
                if (VerificationUtil.getSize(indexInformationBean.getListNews()) >= PAGE_NUM)
                {
                    adapter.loadMoreComplete();
                } else
                {
                    adapter.loadMoreEnd();
                }
            }
        });
    }

    /**
     * 处理数据，使其可以支撑列表适配器使用
     *
     * @param indexInformationBean
     * @return
     */
    private List<AdInfomation.InfomationEntity> handleData(IndexInformationBean indexInformationBean)
    {
        List<AdInfomation.InfomationEntity> list = new ArrayList<>();
        if (indexInformationBean != null)
        {
            AdInfomation.InfomationEntity personEntity = null;
            if (VerificationUtil.noEmpty(indexInformationBean.getListPurchase()))
            {
                personEntity = new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_PERSON, indexInformationBean.getListPurchase());
            }
            AdInfomation.InfomationEntity commodityEntity = null;
            List<CommodityBean> commodityBeans = indexInformationBean.getListMerchandise();
            if (VerificationUtil.noEmpty(commodityBeans))
            {
                commodityEntity = new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_COMMODITY, commodityBeans.get(0));
            }
            List<InfomationBean> infomationBeans = indexInformationBean.getListNews();
            if (VerificationUtil.noEmpty(infomationBeans))
            {
                if (infomationBeans.size() >= 4)
                {
                    for (int i = 0; i < infomationBeans.size(); i++)
                    {
                        if (i == 2)
                        {
                            if (personEntity != null)
                            {
                                list.add(personEntity);
                            }
                        }
                        if (i == 4)
                        {
                            if (commodityEntity != null)
                            {
                                list.add(commodityEntity);
                            }
                        }
//                        List<String> pictures = infomationBeans.get(i).getPictures();
                        String pictures = infomationBeans.get(i).getPictures();
                        String[] picture = null;
                        if (!TextUtils.isEmpty(pictures))
                        {
                            picture = pictures.split(",");
                        }
                        AdInfomation.InfomationEntity infomationEntity = new AdInfomation.InfomationEntity((picture != null && picture.length != 1) ? AdInfomation.InfomationEntity.TYPE_MULTI_IMG : AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, infomationBeans.get(i));
                        list.add(infomationEntity);
                    }
                } else if (infomationBeans.size() >= 2)
                {
                    for (int i = 0; i < infomationBeans.size(); i++)
                    {
                        if (i == 2)
                        {
                            list.add(personEntity);
                            if (commodityEntity != null)
                            {
                                list.add(commodityEntity);
                            }
                        }
//                        List<String> pictures = infomationBeans.get(i).getPictures();
                        String pictures = infomationBeans.get(i).getPictures();
                        String[] picture = null;
                        if (!TextUtils.isEmpty(pictures))
                        {
                            picture = pictures.split(",");
                        }
                        AdInfomation.InfomationEntity infomationEntity = new AdInfomation.InfomationEntity((picture != null && picture.length != 1) ? AdInfomation.InfomationEntity.TYPE_MULTI_IMG : AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, infomationBeans.get(i));
                        list.add(infomationEntity);
                    }
                } else
                {
//                    List<String> pictures = infomationBeans.get(0).getPictures();
                    String pictures = infomationBeans.get(0).getPictures();
                    String[] picture = null;
                    if (!TextUtils.isEmpty(pictures))
                    {
                        picture = pictures.split(",");
                    }
                    AdInfomation.InfomationEntity infomationEntity = new AdInfomation.InfomationEntity((picture != null && picture.length != 1) ? AdInfomation.InfomationEntity.TYPE_MULTI_IMG : AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, infomationBeans.get(0));
                    list.add(infomationEntity);
                    if (personEntity != null)
                    {
                        list.add(personEntity);
                    }
                    if (commodityEntity != null)
                    {
                        list.add(commodityEntity);
                    }
                }

            }
        }
        return list;
    }

    @Override
    public void onRefresh()
    {
        getData(PAGE_START, getArguments().getString(KEY_STR));
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
