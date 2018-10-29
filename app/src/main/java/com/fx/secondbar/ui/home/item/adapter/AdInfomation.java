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
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.bean.PurchaseInfoBean;
import com.fx.secondbar.ui.purchase.AcPurchaseDetail;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

import java.util.List;

/**
 * function:资讯列表适配器
 * author: frj
 * modify date: 2018/9/7
 */
public class AdInfomation extends BaseMultiItemQuickAdapter<AdInfomation.InfomationEntity, BaseViewHolder>
{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public AdInfomation()
    {
        super(null);
        addItemType(InfomationEntity.TYPE_SINGLE_IMG, R.layout.ad_infomation_single_img);
        addItemType(InfomationEntity.TYPE_MULTI_IMG, R.layout.ad_infomation_multi_img);
        addItemType(InfomationEntity.TYPE_COMMODITY, R.layout.ad_infomation_commodity);
        addItemType(InfomationEntity.TYPE_PERSON, R.layout.ad_infomation_person_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdInfomation.InfomationEntity item)
    {
        if (InfomationEntity.TYPE_COMMODITY == item.getItemType())
        {
            handlerCommodity(helper, item);
        } else if (InfomationEntity.TYPE_MULTI_IMG == item.getItemType())
        {
            handlerMultiImg(helper, item);
        } else if (InfomationEntity.TYPE_PERSON == item.getItemType())
        {
            handlerPerson(helper, item);
        } else if (InfomationEntity.TYPE_SINGLE_IMG == item.getItemType())
        {
            handlerSingleImg(helper, item);
        }
    }

    /**
     * 处理商品数据显示
     *
     * @param helper
     * @param item
     */
    private void handlerCommodity(BaseViewHolder helper, AdInfomation.InfomationEntity item)
    {
        TextView tv_tips = helper.getView(R.id.tv_tips);
        ImageView img = helper.getView(R.id.img);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_place = helper.getView(R.id.tv_place);
        if (item.commodityBean != null)
        {
            GlideLoad.load(img, item.commodityBean.getImage(), true);
            VerificationUtil.setViewValue(tv_title, item.commodityBean.getName());
            if (0 == item.commodityBean.getPaytype())
            {
                setCommodityMoney(tv_price, item.commodityBean.getPrice(), false);
            } else
            {
                setCommodityMoney(tv_price, item.commodityBean.getQcoin(), true);
            }
            setCommodityPlace(tv_place, item.commodityBean.getAddress());
            setCommodityTime(tv_time, item.commodityBean.getTimelength());
        } else
        {
            GlideApp.with(img).asBitmap().load(0).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_title, "");
            setCommodityMoney(tv_price, "", false);
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
    private void setCommodityMoney(TextView tv, String price, boolean isQ)
    {
        if (tv != null)
        {
            String money = null;
            if (isQ)
            {
                money = tv.getContext().getString(R.string.mall_detail_info_q_price);
            } else
            {
                money = tv.getContext().getString(R.string.mall_detail_info_price);
            }
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
    private void handlerSingleImg(BaseViewHolder helper, AdInfomation.InfomationEntity item)
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
            GlideLoad.load(img, "", true);
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
    private void handlerMultiImg(BaseViewHolder helper, AdInfomation.InfomationEntity item)
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
                            GlideLoad.load(img2, picture[1], true);
                        }
                        if (picture.length > 2)
                        {
                            GlideLoad.load(img3, picture[2], true);
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
            GlideLoad.load(img1, "", true);
            GlideLoad.load(img2, "", true);
            GlideLoad.load(img3, "", true);
            VerificationUtil.setViewValue(tv_from, "");
            VerificationUtil.setViewValue(tv_title, "");
            VerificationUtil.setViewValue(tv_count, "");
        }
    }

    /**
     * 处理名人显示
     *
     * @param helper
     * @param item
     */
    private void handlerPerson(BaseViewHolder helper, AdInfomation.InfomationEntity item)
    {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
        if (recyclerView.getItemDecorationCount() == 0)
        {
            recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.home_infomation_item_sing_plr), false, false, true));
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
     * 资讯的实体
     */
    public static class InfomationEntity implements MultiItemEntity
    {

        /**
         * 单个图片展示的资讯类型
         */
        public static final int TYPE_SINGLE_IMG = 1;

        /**
         * 多个图片展示的资讯类型
         */
        public static final int TYPE_MULTI_IMG = 2;

        /**
         * 标题
         */
        public static final int TYPE_TITLE = 3;

        /**
         * 推荐名人
         */
        public static final int TYPE_PERSON = 4;

        /**
         * 推荐商品
         */
        public static final int TYPE_COMMODITY = 5;

        /**
         * 项类型
         */
        private int itemType;
        /**
         * 推荐名人列表
         */
        private List<PurchaseInfoBean> personBeans;
        /**
         * 资讯信息
         */
        private InfomationBean infomationBean;

        private CommodityBean commodityBean;

        public InfomationEntity(int type)
        {
            itemType = type;
        }

        public InfomationEntity(int type, InfomationBean bean)
        {
            this(type);
            infomationBean = bean;
        }

        public InfomationEntity(int type, CommodityBean bean)
        {
            this(type);
            commodityBean = bean;
        }

        public InfomationEntity(int type, List<PurchaseInfoBean> beans)
        {
            this(type);
            personBeans = beans;
        }

        @Override
        public int getItemType()
        {
            return itemType;
        }

        public List<PurchaseInfoBean> getPersonBeans()
        {
            return personBeans;
        }

        public void setPersonBeans(List<PurchaseInfoBean> personBeans)
        {
            this.personBeans = personBeans;
        }

        public InfomationBean getInfomationBean()
        {
            return infomationBean;
        }

        public void setInfomationBean(InfomationBean infomationBean)
        {
            this.infomationBean = infomationBean;
        }

        public CommodityBean getCommodityBean()
        {
            return commodityBean;
        }

        public void setCommodityBean(CommodityBean commodityBean)
        {
            this.commodityBean = commodityBean;
        }
    }
}
