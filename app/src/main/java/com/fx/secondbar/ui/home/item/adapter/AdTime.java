package com.fx.secondbar.ui.home.item.adapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.DynamicBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.bean.PurchaseInfoBean;
import com.fx.secondbar.ui.purchase.AcPurchaseDetail;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
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
     */
    public AdTime()
    {
        super(null);
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
//            GlideApp.with(img).asBitmap().load(item.getDynamicBean().getAvatar()).centerCrop().into(img);
            GlideLoad.load(img, item.getDynamicBean().getImg(), true);
            VerificationUtil.setViewValue(tv_time, getTimeDes(item.getDynamicBean().getMinutes()));
            VerificationUtil.setViewValue(tv_dynamic, item.getDynamicBean().getGoodsname());
        } else
        {
            GlideApp.with(img).asBitmap().load(0).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_time, "");
            VerificationUtil.setViewValue(tv_dynamic, "");
        }

    }

    /**
     * 根据分钟数，获取时间描述
     *
     * @param minues
     * @return
     */
    private String getTimeDes(int minues)
    {
        if (minues <= 5)
        {
            return "刚刚";
        } else if (minues <= 60)
        {
            return minues + "分钟前";
        } else
        {
            int hours = minues / 60;
            if (hours <= 31)
            {
                return hours + "天前";
            } else
            {
                int month = hours / 31;
                return month + "月前";
            }
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
        if (recyclerView.getItemDecorationCount() == 0)
        {
            int dimen = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.home_time_content_plr);
            recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(dimen, false, false, true));
        }
        AdPersonItem adapter = new AdPersonItem();
        adapter.setNewData(item.getPersonBeans());
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                AdPersonItem ad = (AdPersonItem) adapter;
                Intent intent = new Intent(view.getContext(), AcPurchaseDetail.class);
                intent.putExtra("activity_str", ad.getItem(position).getPurchase_ID());
                intent.putExtra("activity_num", ad.getItem(position));
                view.getContext().startActivity(intent);
            }
        });
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
//            GlideApp.with(img).asBitmap().load(item.commodityBean.getImg()).centerCrop().into(img);
            GlideLoad.load(img, item.commodityBean.getImage(), true);
            VerificationUtil.setViewValue(tv_title, item.commodityBean.getName());
            setCommodityMoney(tv_price, item.commodityBean.getPrice());
            setCommodityPlace(tv_place, item.commodityBean.getAddress());
            setCommodityTime(tv_time, item.commodityBean.getTimelength());
        } else
        {
            GlideApp.with(img).asBitmap().load(0).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_title, "");
            setCommodityMoney(tv_price, "");
            setCommodityPlace(tv_place, "");
            setCommodityTime(tv_time, "");
        }
    }

    /**
     * 设置商品价格
     *
     * @param tv
     * @param price
     */
    private void setCommodityMoney(TextView tv, String price)
    {
        if (tv != null)
        {
            String money = tv.getContext().getString(R.string.mall_detail_info_price);
            tv.setText(String.format(money, VerificationUtil.verifyDefault(price, "0")));
        }
    }

    /**
     * 设置商品地点
     *
     * @param tv
     * @param address
     */
    private void setCommodityPlace(TextView tv, String address)
    {
        if (tv != null)
        {
            String place = tv.getContext().getString(R.string.mall_detail_info_place);
            tv.setText(String.format(place, VerificationUtil.verifyDefault(address, "等待客服通知")));
        }
    }

    /**
     * 设置商品时长
     *
     * @param tv
     * @param timelength
     */
    private void setCommodityTime(TextView tv, String timelength)
    {
        if (tv != null)
        {
            String time = tv.getContext().getString(R.string.mall_detail_info_time);
            tv.setText(String.format(time, VerificationUtil.verifyDefault(timelength, "0")));
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
            GlideLoad.load(img, item.getInfomationBean().getPicture(), true);
            VerificationUtil.setViewValue(tv_from, item.getInfomationBean().getShare_COPY());
            VerificationUtil.setViewValue(tv_title, item.getInfomationBean().getTitle());
            VerificationUtil.setViewValue(tv_count, String.format(FxApplication.getStr(R.string.read_person_count), item.getInfomationBean().getScan_NUM()));
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
            String pictures = item.getInfomationBean().getPictures();
            if (!TextUtils.isEmpty(pictures))
            {
                String[] picture = pictures.split(",");
                if (picture != null)
                {
                    if (picture.length > 0)
                    {
                        GlideLoad.load(img1, picture[0], true);
                        if (picture.length > 1)
                        {
                            GlideLoad.load(img1, picture[1], true);
                        }
                        if (picture.length > 2)
                        {
                            GlideLoad.load(img1, picture[2], true);
                        }
                    } else
                    {
                        GlideLoad.load(img1, "", true);
                        GlideLoad.load(img2, "", true);
                        GlideLoad.load(img3, "", true);
                    }
                } else
                {
                    GlideLoad.load(img1, "", true);
                    GlideLoad.load(img2, "", true);
                    GlideLoad.load(img3, "", true);
                }
            } else
            {
                GlideLoad.load(img1, "", true);
                GlideLoad.load(img2, "", true);
                GlideLoad.load(img3, "", true);
            }

            VerificationUtil.setViewValue(tv_from, item.getInfomationBean().getShare_COPY());
            VerificationUtil.setViewValue(tv_title, item.getInfomationBean().getTitle());
            VerificationUtil.setViewValue(tv_count, String.format(FxApplication.getStr(R.string.read_person_count), item.getInfomationBean().getScan_NUM()));
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
        private List<PurchaseInfoBean> personBeans;
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

        public TimeEntity(int type, List<PurchaseInfoBean> bean)
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

        public List<PurchaseInfoBean> getPersonBeans()
        {
            return personBeans;
        }

        public void setPersonBeans(List<PurchaseInfoBean> personBeans)
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
