package com.fx.secondbar.ui.person;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.ProgressDialogUtil;

import rx.Subscriber;

/**
 * function:设置昵称界面
 * author: frj
 * modify date: 2018/9/25
 */
public class AcSetNickName extends ActivitySupport
{

    private EditText ed_nickname;
    private ProgressDialog dialog;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_set_nick_name;
    }

    @Override
    protected void initView()
    {
        ed_nickname = findView(R.id.ed_nickname);
        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.btn_submit).setOnClickListener(this);
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
        VerificationUtil.setViewValue(ed_nickname, getIntent().getStringExtra(KEY_STR));
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
    }

    /**
     * 设置昵称
     *
     * @param nickName
     */
    private void setNickName(final String nickName)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.setNickName(nickName, new Subscriber<ResponseBean>()
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
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(ResponseBean responseBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                FxApplication.refreshUserInfoBroadCast();
                ShowToast.showToast("修改成功");
                Intent intent = new Intent();
                intent.putExtra(KEY_STR, nickName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
            case R.id.btn_submit:
                if (VerificationUtil.validator(this, ed_nickname, "请输入昵称"))
                {
                    setNickName(getTextView(ed_nickname));
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
