package com.fx.secondbar.ui.purchase;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.DialogShare;
import com.fx.secondbar.ui.quote.AcQuoteBuyConfirm;
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

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_quote_detail;
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
        GlideApp.with(img_avatar).asBitmap().load(R.mipmap.test_person_item1).centerCrop().into(img_avatar);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_share:
                new DialogShare(this).show();
                break;
            case R.id.btn_buy:
                jump(AcQuoteBuyConfirm.class);
                break;
        }
    }

    /**
     * 获取数据
     *
     * @param id
     */
    private void getData(String id)
    {
        HttpManager.getQuoteDetail(id, new Subscriber<PersonBean>()
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
            public void onNext(PersonBean personBean)
            {
                if (isDestroy())
                {
                    return;
                }
                bindData(personBean);
            }
        });
    }

    /**
     * 绑定数据
     *
     * @param bean
     */
    private void bindData(PersonBean bean)
    {
        if (bean != null)
        {
            GlideLoad.load(img_top, bean.getPicture());
            GlideLoad.load(img_avatar, bean.getImg(), true);
            if (TextUtils.isEmpty(bean.getZjm()))
            {
                VerificationUtil.setViewValue(tv_person_name, bean.getName());
            } else
            {
                VerificationUtil.setViewValue(tv_person_name, bean.getName() + "(" + bean.getZjm() + ")");
            }
            VerificationUtil.setViewValue(tv_person_price, String.format(getString(R.string.mall_detail_info_price), VerificationUtil.verifyDefault(bean.getPrice(), "0")));
            VerificationUtil.setViewValue(tv_person_position, String.format(getString(R.string.mall_detail_info_position), VerificationUtil.verifyDefault(bean.getJob(), "")));
            VerificationUtil.setViewValue(tv_person_school, String.format(getString(R.string.mall_detail_info_school), VerificationUtil.verifyDefault(bean.getSchool(), "")));
            VerificationUtil.setViewValue(tv_experience, bean.getExperience());
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
