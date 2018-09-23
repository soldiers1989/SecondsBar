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
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.IndexInformationBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.AcWebBrowse;
import com.fx.secondbar.ui.home.item.adapter.AdInfomation;
import com.fx.secondbar.util.Constants;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * function:资讯
 * author: frj
 * modify date: 2018/9/7
 */
public class FragmentInfomation extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private AdInfomation adapter;

    private ConvenientBanner<Integer> convenientBanner;

    @Override
    public void onStarShow()
    {
        if (!VerificationUtil.noEmpty(adapter.getData()))
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_home_infomation, container, false);
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

    }

    @Override
    protected void initData()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(1, false, false, false));
        adapter = new AdInfomation();
        adapter.addHeaderView(getHeaderView());
        adapter.addHeaderView(createHeadView());
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                AdInfomation.InfomationEntity entity = FragmentInfomation.this.adapter.getItem(position);
                if (AdInfomation.InfomationEntity.TYPE_MULTI_IMG == entity.getItemType() || AdInfomation.InfomationEntity.TYPE_SINGLE_IMG == entity.getItemType())
                {
                    Bundle bundle = new Bundle();
                    if (entity.getInfomationBean() != null)
                    {
                        bundle.putString(KEY, Constants.URL_INFORMATION + entity.getInfomationBean().getNews_ID());
                    }
                    bundle.putString(KEY_STR, "秒吧头条");
                    jump(AcWebBrowse.class, bundle, false);
                }
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    /**
     * 获取头控件
     *
     * @return
     */
    private View getHeaderView()
    {
        String[] arrays = getResources().getStringArray(R.array.home_infoation_types);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_infomation_head, null);
        TextView tv_head_item1 = header.findViewById(R.id.tv_head_item1);
        TextView tv_head_item2 = header.findViewById(R.id.tv_head_item2);
        TextView tv_head_item3 = header.findViewById(R.id.tv_head_item3);
        TextView tv_head_item4 = header.findViewById(R.id.tv_head_item4);
        TextView tv_head_item5 = header.findViewById(R.id.tv_head_item5);
        tv_head_item1.setText(arrays[0]);
        tv_head_item2.setText(arrays[1]);
        tv_head_item3.setText(arrays[2]);
        tv_head_item4.setText(arrays[3]);
        tv_head_item5.setText(arrays[4]);
        tv_head_item1.setSelected(true);
        OnTabClickListener listener = new OnTabClickListener(this, new TextView[]{tv_head_item1, tv_head_item2, tv_head_item3, tv_head_item4, tv_head_item5});
        tv_head_item1.setOnClickListener(listener);
        tv_head_item2.setOnClickListener(listener);
        tv_head_item3.setOnClickListener(listener);
        tv_head_item4.setOnClickListener(listener);
        tv_head_item5.setOnClickListener(listener);
        return header;
    }

    /**
     * 创建头部控件
     *
     * @return
     */
    private View createHeadView()
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
        }, getBanners()).setPageIndicator(new int[]{R.mipmap.ic_indicator, R.mipmap.ic_indicator_sel});
        return view;
    }

//    private List<AdInfomation.InfomationEntity> getData()
//    {
//        List<AdInfomation.InfomationEntity> data = new ArrayList<>();
//        data.add(new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, new InfomationBean(getImgList(R.mipmap.test_infomation_single_1), "中非合作论坛峰会开幕式宣传片《同心共筑  命运与共》", "央视新闻移动网", "128评")));
//        data.add(new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_MULTI_IMG, new InfomationBean(getImgList(R.mipmap.test_information_multi_1_1, R.mipmap.test_information_multi_1_2, R.mipmap.test_information_multi_1_3), "分别总是在九月，回忆是思念的愁。青春无悔！这是你见过的最美退伍写真", "新浪网", "66评")));
//        List<PersonBean> personBeans = new ArrayList<>();
//        personBeans.add(new PersonBean(R.mipmap.test_person_item1, "0.60STE/秒", "雷晓军"));
//        personBeans.add(new PersonBean(R.mipmap.test_person_item2, "1.26STE/秒", "于大宝"));
//        personBeans.add(new PersonBean(R.mipmap.test_person_item3, "0.86STE/秒", "劳伦斯"));
//        data.add(new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_PERSON, personBeans));
//        data.add(new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, new InfomationBean(getImgList(R.mipmap.test_infomation_single_2), "文在寅首场会谈结束 金正恩称朝美对话会有进展 双方有望达成一致", "央视新闻移动网", "128评")));
//        data.add(new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_MULTI_IMG, new InfomationBean(getImgList(R.mipmap.test_information_multi_2_1, R.mipmap.test_information_multi_2_2, R.mipmap.test_information_multi_2_3), "分别总是在九月，回忆是思念的愁。青春无悔！这是你见过的最美退伍写真", "新浪网", "66评")));
//        data.add(new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_COMMODITY, new CommodityBean(R.mipmap.test_time_commodity_2, "黄晓明 区块链的投资和投机", "980.00STE", "时长：90分钟", "地点：等待客服通知")));
//        data.add(new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, new InfomationBean(getImgList(R.mipmap.test_infomation_single_3), "文在寅会前称赞金正恩：大胆又决断开启新时代即将来临", "央视新闻移动网", "128评")));
//        data.add(new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_MULTI_IMG, new InfomationBean(getImgList(R.mipmap.test_information_multi_3_1, R.mipmap.test_information_multi_3_2, R.mipmap.test_information_multi_3_3), "金正恩带文在寅看宾馆 朝鲜条件不好但会诚意接待 朝韩关系开始缓和", "新浪网", "66评")));
//        return data;
//    }

//    private List<Integer> getImgList(Integer... imgs)
//    {
//        return Arrays.asList(imgs);
//    }

    /**
     * 获取图片链接
     *
     * @return
     */
    private List<Integer> getBanners()
    {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.test_banner_1);
        list.add(R.mipmap.test_bannner_2);
        list.add(R.mipmap.test_banner_3);
//        list.add(R.mipmap.test_infomation_banner_3);
        return list;
    }

    /**
     * 获取数据
     *
     * @param type
     */
    private void getData(int page, String type)
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
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                adapter.setNewData(handleData(indexInformationBean));
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
            AdInfomation.InfomationEntity personEntity = new AdInfomation.InfomationEntity(AdInfomation.InfomationEntity.TYPE_PERSON, indexInformationBean.getListPeople());
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
                            list.add(personEntity);
                        }
                        if (i == 4)
                        {
                            if (commodityEntity != null)
                            {
                                list.add(commodityEntity);
                            }
                        }
                        List<String> pictures = infomationBeans.get(i).getPictures();
                        AdInfomation.InfomationEntity infomationEntity = new AdInfomation.InfomationEntity(VerificationUtil.noEmpty(pictures) ? AdInfomation.InfomationEntity.TYPE_MULTI_IMG : AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, infomationBeans.get(i));
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
                        List<String> pictures = infomationBeans.get(i).getPictures();
                        AdInfomation.InfomationEntity infomationEntity = new AdInfomation.InfomationEntity(VerificationUtil.noEmpty(pictures) ? AdInfomation.InfomationEntity.TYPE_MULTI_IMG : AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, infomationBeans.get(i));
                        list.add(infomationEntity);
                    }
                } else
                {
                    List<String> pictures = infomationBeans.get(0).getPictures();
                    AdInfomation.InfomationEntity infomationEntity = new AdInfomation.InfomationEntity(VerificationUtil.noEmpty(pictures) ? AdInfomation.InfomationEntity.TYPE_MULTI_IMG : AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, infomationBeans.get(0));
                    list.add(infomationEntity);
                    list.add(personEntity);
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
        getData(PAGE_START, "1");
    }

    /**
     * 切换类型，刷新类型数据
     *
     * @param type
     */
    private void refresh(String type)
    {
//        swipeRefreshLayout.setRefreshing(true);
//        onRefresh();
    }


    /**
     * Tab点击切换监听事件
     */
    public static class OnTabClickListener implements View.OnClickListener
    {

        private FragmentInfomation fragment;
        private TextView[] textViews;

        public OnTabClickListener(FragmentInfomation fragment, TextView[] textViews)
        {
            this.fragment = fragment;
            this.textViews = textViews;
        }

        @Override
        public void onClick(View v)
        {
            if (v.isSelected())
            {
                return;
            }
            for (TextView tv : textViews)
            {
                tv.setSelected(v == tv);
            }
            fragment.refresh((String) v.getTag());
        }
    }
}
