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
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.DynamicBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.ui.home.item.adapter.AdTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * function:首页-时间场
 * author: frj
 * modify date: 2018/9/7
 */
public class FragmentTime extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ConvenientBanner<Integer> convenientBanner;

    private AdTime adapter;

    @Override
    public void onStarShow()
    {
        if (convenientBanner != null)
        {
//            if (!convenientBanner.isTurning()) {
//                convenientBanner.startTurning();
//            }
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
        adapter = new AdTime(getDatas());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter.bindToRecyclerView(recyclerView);
        adapter.addHeaderView(createHeadView());
    }

    /**
     * 获取数据
     *
     * @return
     */
    private List<AdTime.TimeEntity> getDatas()
    {
        List<AdTime.TimeEntity> list = new ArrayList<>();
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_TITLE, "交易动态"));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_DYNAMIC, new DynamicBean(R.mipmap.test_dynamic_1, "了不起的盖茨比购买了 下午茶", "45分钟前")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_DYNAMIC, new DynamicBean(R.mipmap.test_dynamic_2, "郭大侠购买了 粉丝见面会", "48分钟前")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_DYNAMIC, new DynamicBean(R.mipmap.test_dynamic_3, "Tommy购买了 区块链", "1小时前")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_TITLE, "名人申购"));
        List<PersonBean> personBeans = new ArrayList<>();
        personBeans.add(new PersonBean(R.mipmap.test_person_item1, "0.60STE/秒", "雷晓军"));
        personBeans.add(new PersonBean(R.mipmap.test_person_item2, "1.26STE/秒", "于大宝"));
        personBeans.add(new PersonBean(R.mipmap.test_person_item3, "0.86STE/秒", "劳伦斯"));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_PERSON, personBeans));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_TITLE, "名人商品"));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_COMMODITY, new CommodityBean(R.mipmap.test_time_commodity_1, "周杰伦 粉丝见面演唱会", "3600.00STE", "时长：120分钟", "地点：深圳")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_COMMODITY, new CommodityBean(R.mipmap.test_time_commodity_2, "黄晓明 区块链的投资和投机", "980.00STE", "时长：90分钟", "地点：等待客服通知")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_COMMODITY, new CommodityBean(R.mipmap.test_time_commodity_3, "李宝亮 比特币交易演讲", "28600.00STE", "时长：180分钟", "地点：香港")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_TITLE, "名人资讯"));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_SINGLE_IMG, new InfomationBean(getImgList(R.mipmap.test_infomation_single_1), "中非合作论坛峰会开幕式宣传片《同心共筑  命运与共》", "央视新闻移动网", "128评")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_MULTI_IMG, new InfomationBean(getImgList(R.mipmap.test_information_multi_1_1, R.mipmap.test_information_multi_1_2, R.mipmap.test_information_multi_1_3), "分别总是在九月，回忆是思念的愁。青春无悔！这是你见过的最美退伍写真", "新浪网", "66评")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_SINGLE_IMG, new InfomationBean(getImgList(R.mipmap.test_infomation_single_3), "文在寅会前称赞金正恩：大胆又决断开启新时代即将来临", "央视新闻移动网", "128评")));
        list.add(new AdTime.TimeEntity(AdTime.TimeEntity.TYPE_MULTI_IMG, new InfomationBean(getImgList(R.mipmap.test_information_multi_2_1, R.mipmap.test_information_multi_2_2, R.mipmap.test_information_multi_2_3), "武汉今年年底再开通七号线和十一号线，市政府计划明年完成地铁规划新布局", "央视新闻移动网", "128评")));

        return list;
    }

    private List<Integer> getImgList(Integer... imgs)
    {
        return Arrays.asList(imgs);
    }

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
        return list;
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
                return new LoadImageHolder(itemView);
            }

            @Override
            public int getLayoutId()
            {
                return R.layout.layout_banner;
            }
        }, getBanners()).setPageIndicator(new int[]{R.mipmap.ic_indicator, R.mipmap.ic_indicator_sel});
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!isHidden())
        {
//            if (convenientBanner != null) {
//                convenientBanner.startTurning();
//            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
//        if (convenientBanner != null) {
//            convenientBanner.stopTurning();
//        }
    }

    @Override
    public void onRefresh()
    {

    }

    /**
     * 加载图片Holder
     */
    static class LoadImageHolder extends Holder<Integer>
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
        public void updateUI(Integer data)
        {
            if (img != null)
            {
                GlideApp.with(img).load(data).centerCrop().into(img);
            }
        }
    }
}
