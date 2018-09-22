package com.fx.secondbar.ui.home.item.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.DynamicBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.bean.PersonBean;
import com.joooonho.SelectableRoundedImageView;

import java.util.List;

/**
 * function:首页-时间场适配器
 * author: frj
 * modify date: 2018/9/8
 */
public class AdTime extends BaseMultiItemQuickAdapter<AdTime.TimeEntity, BaseViewHolder>
{


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AdTime(List<TimeEntity> data)
    {
        super(data);
        addItemType(TimeEntity.TYPE_TITLE, R.layout.ad_time_title);
        addItemType(TimeEntity.TYPE_DYNAMIC, R.layout.ad_time_dynamic);
        addItemType(TimeEntity.TYPE_PURCHASE, R.layout.ad_time_purchase);
        addItemType(TimeEntity.TYPE_PERSON, R.layout.ad_time_person);
        addItemType(TimeEntity.TYPE_COMMODITY, R.layout.ad_time_commodity);
        addItemType(TimeEntity.TYPE_SINGLE_IMG, R.layout.ad_time_single_img);
        addItemType(TimeEntity.TYPE_MULTI_IMG, R.layout.ad_infomation_multi_img);
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeEntity item)
    {
        if (AdTime.TimeEntity.TYPE_DYNAMIC == item.getItemType())
        {
            handlerDynamic(helper, item);
        } else if (AdTime.TimeEntity.TYPE_PURCHASE == item.getItemType())
        {
            handlerPurchase(helper, item);
        } else if (AdTime.TimeEntity.TYPE_TITLE == item.getItemType())
        {
            handlerTitle(helper, item);
        } else if (TimeEntity.TYPE_PERSON == item.getItemType())
        {
            handlerPerson(helper, item);
        } else if (AdTime.TimeEntity.TYPE_COMMODITY == item.getItemType())
        {
            handlerCommodity(helper, item);
        } else if (AdTime.TimeEntity.TYPE_SINGLE_IMG == item.getItemType())
        {
            handlerSingleImg(helper, item);
        } else if (AdTime.TimeEntity.TYPE_MULTI_IMG == item.getItemType())
        {
            handlerMultiImg(helper, item);
        }
    }

    /**
     * 处理交易动态数据
     *
     * @param helper
     * @param item
     */
    private void handlerDynamic(BaseViewHolder helper, TimeEntity item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_dynamic = helper.getView(R.id.tv_dynamic);
        if (item.getDynamicBean() != null)
        {
            GlideApp.with(img).asBitmap().load(item.getDynamicBean().getAvatar()).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_time, item.getDynamicBean().getTime());
            VerificationUtil.setViewValue(tv_dynamic, item.getDynamicBean().getContent());
        } else
        {
            GlideApp.with(img).asBitmap().load(0).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_time, "");
            VerificationUtil.setViewValue(tv_dynamic, "");
        }

    }

    /**
     * 处理申购数据
     *
     * @param helper
     * @param item
     */
    private void handlerPurchase(BaseViewHolder helper, TimeEntity item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_content = helper.getView(R.id.tv_content);

        GlideApp.with(img).load("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1206/11/c0/11958819_1339405691583.jpg").centerCrop().into(img);
    }

    /**
     * 处理标题数据显示
     *
     * @param helper
     * @param item
     */
    private void handlerTitle(BaseViewHolder helper, TimeEntity item)
    {
        TextView tv_title = helper.getView(R.id.tv_title);
        VerificationUtil.setViewValue(tv_title, item.title);
    }

    /**
     * 处理名人显示
     *
     * @param helper
     * @param item
     */
    private void handlerPerson(BaseViewHolder helper, TimeEntity item)
    {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
        int dimen = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.home_time_content_plr);
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(dimen, false, false, true));
        AdPersonItem adapter = new AdPersonItem();
        adapter.setNewData(item.getPersonBeans());
        adapter.bindToRecyclerView(recyclerView);
    }

    /**
     * 处理商品数据显示
     *
     * @param helper
     * @param item
     */
    private void handlerCommodity(BaseViewHolder helper, TimeEntity item)
    {
        ImageView img = helper.getView(R.id.img);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_place = helper.getView(R.id.tv_place);
        if (item.getCommodityBean() != null)
        {
            GlideApp.with(img).asBitmap().load(item.commodityBean.getImg()).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_title, item.commodityBean.getTitle());
            VerificationUtil.setViewValue(tv_price, item.commodityBean.getPrice());
            VerificationUtil.setViewValue(tv_time, item.commodityBean.getTime());
            VerificationUtil.setViewValue(tv_place, item.commodityBean.getPlace());
        } else
        {
            GlideApp.with(img).asBitmap().load(0).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_title, "");
            VerificationUtil.setViewValue(tv_price, "");
            VerificationUtil.setViewValue(tv_time, "");
            VerificationUtil.setViewValue(tv_place, "");
        }

    }

    /**
     * 处理单条图片的资讯新闻数据显示
     *
     * @param helper
     * @param item
     */
    private void handlerSingleImg(BaseViewHolder helper, TimeEntity item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_from = helper.getView(R.id.tv_from);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_count = helper.getView(R.id.tv_count);
        if (item.getInfomationBean() != null)
        {
            List<Integer> list = item.getInfomationBean().getList();
            if (VerificationUtil.noEmpty(list))
            {
                GlideApp.with(img).asBitmap().load(list.get(0)).centerCrop().into(img);
            } else
            {
                GlideApp.with(img).asBitmap().load(0).centerCrop().into(img);
            }
            VerificationUtil.setViewValue(tv_from, item.getInfomationBean().getFrom());
            VerificationUtil.setViewValue(tv_title, item.getInfomationBean().getContent());
            VerificationUtil.setViewValue(tv_count, item.getInfomationBean().getCount());
        } else
        {
            GlideApp.with(img).asBitmap().load(0).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_from, "");
            VerificationUtil.setViewValue(tv_title, "");
            VerificationUtil.setViewValue(tv_count, "");
        }
    }

    /**
     * 处理多条图片的资讯新闻数据显示
     *
     * @param helper
     * @param item
     */
    private void handlerMultiImg(BaseViewHolder helper, TimeEntity item)
    {
        SelectableRoundedImageView img1 = helper.getView(R.id.img1);
        SelectableRoundedImageView img2 = helper.getView(R.id.img2);
        SelectableRoundedImageView img3 = helper.getView(R.id.img3);
        TextView tv_from = helper.getView(R.id.tv_from);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_count = helper.getView(R.id.tv_count);
        if (item.getInfomationBean() != null)
        {
            List<Integer> list = item.getInfomationBean().getList();
            if (list != null && list.size() >= 3)
            {
                GlideApp.with(img1).asBitmap().load(list.get(0)).centerCrop().into(img1);
                GlideApp.with(img2).asBitmap().load(list.get(1)).centerCrop().into(img2);
                GlideApp.with(img3).asBitmap().load(list.get(2)).centerCrop().into(img3);
            } else
            {
                GlideApp.with(img1).asBitmap().load(0).centerCrop().into(img1);
                GlideApp.with(img2).asBitmap().load(0).centerCrop().into(img2);
                GlideApp.with(img3).asBitmap().load(0).centerCrop().into(img3);
            }
            VerificationUtil.setViewValue(tv_from, item.getInfomationBean().getFrom());
            VerificationUtil.setViewValue(tv_title, item.getInfomationBean().getContent());
            VerificationUtil.setViewValue(tv_count, item.getInfomationBean().getCount());
        } else
        {
            GlideApp.with(img1).asBitmap().load(0).centerCrop().into(img1);
            GlideApp.with(img2).asBitmap().load(0).centerCrop().into(img2);
            GlideApp.with(img3).asBitmap().load(0).centerCrop().into(img3);
            VerificationUtil.setViewValue(tv_from, "");
            VerificationUtil.setViewValue(tv_title, "");
            VerificationUtil.setViewValue(tv_count, "");
        }
    }

    /**
     * 时间场的实体
     */
    public static class TimeEntity implements MultiItemEntity
    {

        /**
         * 时间场-动态
         */
        public static final int TYPE_DYNAMIC = 1;

        /**
         * 时间场-申购
         */
        public static final int TYPE_PURCHASE = 2;

        /**
         * 时间场-标题
         */
        public static final int TYPE_TITLE = 3;

        /**
         * 名人
         */
        public static final int TYPE_PERSON = 4;

        /**
         * 商品
         */
        public static final int TYPE_COMMODITY = 5;

        /**
         * 名人资讯-单张图
         */
        public static final int TYPE_SINGLE_IMG = 6;

        /**
         * 名人资讯-多张图
         */
        public static final int TYPE_MULTI_IMG = 7;

        /**
         * 项类型
         */
        private int itemType;
        //标题
        private String title;
        /**
         * 交易动态实体
         */
        private DynamicBean dynamicBean;
        /**
         * 资讯信息实体
         */
        private InfomationBean infomationBean;
        /**
         * 推荐名人列表
         */
        private List<PersonBean> personBeans;
        /**
         * 商品实体信息
         */
        private CommodityBean commodityBean;

        public TimeEntity(int type)
        {
            itemType = type;
        }

        public TimeEntity(int type, String title)
        {
            this(type);
            this.title = title;
        }

        public TimeEntity(int type, DynamicBean bean)
        {
            this(type);
            dynamicBean = bean;
        }

        public TimeEntity(int type, InfomationBean bean)
        {
            this(type);
            infomationBean = bean;
        }

        public TimeEntity(int type, List<PersonBean> bean)
        {
            this(type);
            personBeans = bean;
        }

        public TimeEntity(int type, CommodityBean bean)
        {
            this(type);
            commodityBean = bean;
        }

        @Override
        public int getItemType()
        {
            return itemType;
        }

        public InfomationBean getInfomationBean()
        {
            return infomationBean;
        }

        public void setInfomationBean(InfomationBean infomationBean)
        {
            this.infomationBean = infomationBean;
        }

        public List<PersonBean> getPersonBeans()
        {
            return personBeans;
        }

        public void setPersonBeans(List<PersonBean> personBeans)
        {
            this.personBeans = personBeans;
        }

        public CommodityBean getCommodityBean()
        {
            return commodityBean;
        }

        public void setCommodityBean(CommodityBean commodityBean)
        {
            this.commodityBean = commodityBean;
        }

        public DynamicBean getDynamicBean()
        {
            return dynamicBean;
        }

        public void setDynamicBean(DynamicBean dynamicBean)
        {
            this.dynamicBean = dynamicBean;
        }
    }
}
