package com.fx.secondbar.ui.purchase;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.bean.PurchaseDetailBean;
import com.fx.secondbar.bean.PurchaseInfoBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.AcShareDialog;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

import me.next.tagview.TagCloudView;
import rx.Subscriber;

/**
 * function:申购详情
 * author: frj
 * modify date: 2018/9/23
 */
public class AcPurchaseDetail extends ActivitySupport
{
    private ImageView img_top;
    private SelectableRoundedImageView img_avatar;
    private TextView tv_person_name;
    private TextView tv_person_price;
    private TextView tv_person_position;
    private TextView tv_person_school;
    private TextView tv_use;
    private TagCloudView tagCloudView;
    private TextView tv_experience;

    //名人图片
    private String personPicture;

    private PurchaseInfoBean purchaseInfoBean;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_purchase_detail;
    }

    @Override
    protected void initView()
    {
        img_top = findView(R.id.img_top);
        img_avatar = findView(R.id.img_avatar);
        tv_person_name = findView(R.id.tv_person_name);
        tv_person_price = findView(R.id.tv_person_price);
        tv_person_position = findView(R.id.tv_person_position);
        tv_person_school = findView(R.id.tv_person_school);
        tv_use = findView(R.id.tv_use);
        tagCloudView = findView(R.id.tagCloudView);
        tv_experience = findView(R.id.tv_experience);
        findView(R.id.ib_share).setOnClickListener(this);
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.btn_buy).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
//        GlideApp.with(img_avatar).asBitmap().load(R.mipmap.test_person_item1).centerCrop().into(img_avatar);
        getData(getIntent().getStringExtra(KEY_STR));
    }

    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_share:
                StringBuilder sb = new StringBuilder();
                sb.append(getTextView(tv_person_name));
                sb.append("==");
                sb.append(getTextView(tv_person_price));
                Bundle bundleShare = new Bundle();
                bundleShare.putInt(AcShareDialog.KEY_TYPE, AcShareDialog.TYPE_POST_PURCHASE);
                bundleShare.putString(AcShareDialog.KEY_CONTENT, sb.toString());
                bundleShare.putString(AcShareDialog.KEY_IMG, personPicture);
                jump(AcShareDialog.class, bundleShare, false);
                break;
            case R.id.btn_buy:
                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY, purchaseInfoBean);
                jump(AcQuoteBuyConfirm.class, bundle, false);
                break;
        }
    }

    /**
     * 获取数据
     *
     * @param id 申购id
     */
    private void getData(String id)
    {
        HttpManager.getPurchaseDetail(id, new Subscriber<PurchaseDetailBean>()
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
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(PurchaseDetailBean bean)
            {
                if (isDestroy())
                {
                    return;
                }
                bindData(bean);
            }
        });
    }

    /**
     * 绑定数据
     *
     * @param bean 申购详情实体信息
     */
    private void bindData(PurchaseDetailBean bean)
    {
        if (bean != null)
        {
            PurchaseInfoBean purchaseInfoBean = bean.getPurchaseVO();
            if (purchaseInfoBean != null)
            {
                VerificationUtil.setViewValue(tv_person_price, String.format(getString(R.string.purchase_money_text), VerificationUtil.verifyDefault(purchaseInfoBean.getPrice(), "0")));
                GlideLoad.load(img_avatar, purchaseInfoBean.getPeopleimg(), true);
                personPicture = purchaseInfoBean.getPeopleimg();
                if (TextUtils.isEmpty(purchaseInfoBean.getZjm()))
                {
                    VerificationUtil.setViewValue(tv_person_name, purchaseInfoBean.getPeoplename());
                } else
                {
                    VerificationUtil.setViewValue(tv_person_name, purchaseInfoBean.getPeoplename() + "(" + purchaseInfoBean.getZjm() + ")");
                }
                this.purchaseInfoBean = purchaseInfoBean;
            }

            PersonBean personBean = bean.getPeopleVO();
            if (personBean != null)
            {
                GlideLoad.load(img_top, personBean.getPicture());
                VerificationUtil.setViewValue(tv_person_position, String.format(getString(R.string.mall_detail_info_position), VerificationUtil.verifyDefault(personBean.getJob(), "")));
                VerificationUtil.setViewValue(tv_person_school, String.format(getString(R.string.mall_detail_info_school), VerificationUtil.verifyDefault(personBean.getSchool(), "")));
                VerificationUtil.setViewValue(tv_experience, personBean.getExperience());
                String use = String.format(getString(R.string.purchase_detail_time_use), personBean.getName(), personBean.getAddress());
                VerificationUtil.setViewValue(tv_use, use);
            }
            tagCloudView.setTags(bean.getListTimers());
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
}
