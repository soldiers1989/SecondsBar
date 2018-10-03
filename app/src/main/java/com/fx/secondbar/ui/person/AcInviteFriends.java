package com.fx.secondbar.ui.person;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.InviteInfoBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.AcShareDialog;
import com.fx.secondbar.util.Constants;

import rx.Subscriber;

/**
 * function:邀请好友
 * author: frj
 * modify date: 2018/10/2
 */
public class AcInviteFriends extends ActivitySupport
{

    private TextView tv_generate_url_tips;
    private TextView tv_total_get;
    private TextView tv_total_person;
    private TextView tv_rule;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_invite_friends;
    }

    @Override
    protected void initView()
    {
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);

        findView(R.id.tv_generate).setOnClickListener(this);
        findView(R.id.tv_copy).setOnClickListener(this);
        tv_generate_url_tips = findView(R.id.tv_generate_url_tips);
        tv_total_get = findView(R.id.tv_total_get);
        tv_total_person = findView(R.id.tv_total_person);
        tv_rule = findView(R.id.tv_rule);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        String url = String.format(Constants.URL_SHARE, FxApplication.getInstance().getUserInfoBean().getMemberid());
        VerificationUtil.setViewValue(tv_generate_url_tips, String.format(getString(R.string.invite_generate_url), url));
        tv_generate_url_tips.setTag(url);

        getData();
    }

    /**
     * 获取邀请信息
     */
    private void getData()
    {
        HttpManager.getInviteInfo(new Subscriber<InviteInfoBean>()
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
            }

            @Override
            public void onNext(InviteInfoBean inviteInfoBean)
            {
                if (isDestroy())
                {
                    return;
                }
                VerificationUtil.setViewValue(tv_total_person, "邀请总人数：" + VerificationUtil.verifyDefault(inviteInfoBean.getTotals(), "0"));
                String getQTips = String.format(getString(R.string.invite_total_get_q), VerificationUtil.verifyDefault(inviteInfoBean.getQcointotal(), "0"));
                tv_total_get.setText(Html.fromHtml(getQTips));
                VerificationUtil.setViewValue(tv_rule, inviteInfoBean.getDescription());
            }
        });
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
            case R.id.tv_generate:
                Bundle bundle = new Bundle();
                bundle.putInt(AcShareDialog.KEY_TYPE, AcShareDialog.TYPE_POSTER_INVITE);
                jump(AcShareDialog.class, bundle, false);
                break;
            case R.id.tv_copy:
                String address = (String) tv_generate_url_tips.getTag();
                if (!TextUtils.isEmpty(address))
                {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", address);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ShowToast.showToast("复制成功");
                } else
                {
                    ShowToast.showToast("无地址");
                }
                break;

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
