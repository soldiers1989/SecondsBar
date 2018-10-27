package com.fx.secondbar.ui.home.item.adapter;

import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.btten.bttenlibrary.util.Arithmetic;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.DateBean;
import com.fx.secondbar.util.GlideLoad;
import com.fx.secondbar.util.PhoneShowUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.next.tagview.TagCloudView;

/**
 * function:约Ta列表适配器
 * author: frj
 * modify date: 2018/10/22
 */
public class AdDateTa extends BaseQuickAdapter<DateBean, BaseViewHolder>
{
    private int height;

    public AdDateTa(int height)
    {
        super(R.layout.ad_date_ta);
        this.height = height;
    }

    @Override
    protected void convert(BaseViewHolder helper, DateBean item)
    {
        ConstraintLayout content = helper.getView(R.id.content);
        ImageView img_avatar = helper.getView(R.id.img_avatar);
        ImageView img_yue = helper.getView(R.id.img_yue);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_account = helper.getView(R.id.tv_account);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_des = helper.getView(R.id.tv_des);
        ImageView img = helper.getView(R.id.img);
        TextView tv_location = helper.getView(R.id.tv_location);
        TagCloudView tag_cloud_view = helper.getView(R.id.tag_cloud_view);

        ViewGroup.LayoutParams params = content.getLayoutParams();
        params.height = height;
        content.setLayoutParams(params);

        VerificationUtil.setViewValue(tv_name, item.getNickname());
        VerificationUtil.setViewValue(tv_account, "秒吧号：" + VerificationUtil.verifyDefault(PhoneShowUtil.handlerPhoneStr(item.getAccount()), ""));
        VerificationUtil.setViewValue(tv_des, item.getName());
        VerificationUtil.setViewValue(tv_price, "￥" + VerificationUtil.verifyDefault(item.getPrice(), "0"));
        GlideLoad.loadCicle(img_avatar, item.getImg(), R.mipmap.default_avatar, R.mipmap.default_avatar);
        GlideLoad.loadRound(img, item.getImage(), R.drawable.ic_default_adimage, R.drawable.ic_default_adimage, DensityUtil.dip2px(img.getContext(), 5));
        if (tv_location != null)
        {
            String distance = "";
            try
            {
                distance = getDistance(new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLon())), new LatLng(FxApplication.getInstance().getLocationBean().getLatitude(), FxApplication.getInstance().getLocationBean().getLongitude()));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            tv_location.setText(VerificationUtil.verifyDefault(item.getAddress(), "") + " " + VerificationUtil.verifyDefault(distance, "0km"));
        }
        tag_cloud_view.setTags(getTags(item.getTag()));
    }

    /**
     * 获取距离
     *
     * @return
     */
    private String getDistance(LatLng l1, LatLng l2)
    {
        //单位：米
        double distance = DistanceUtil.getDistance(l1, l2);
        if (distance < 1000)
        {
            return Arithmetic.doubleToStr(distance, 2) + "m";
        } else
        {
            return Arithmetic.doubleToStr(distance / 1000) + "km";
        }
    }

    /**
     * 获取标签集
     *
     * @param tags
     * @return
     */
    private List<String> getTags(String tags)
    {
        if (!TextUtils.isEmpty(tags))
        {
            String tagArray[] = tags.split("，");
            if (tagArray != null && tagArray.length > 0)
            {
                return Arrays.asList(tagArray);
            }
        }
        return Collections.emptyList();
    }
}
