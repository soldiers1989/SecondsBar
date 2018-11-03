package com.fx.secondbar.ui.person.assets;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
 * function:实名认证界面
 * author: frj
 * modify date: 2018/9/22
 */
public class AcVerified extends ActivitySupport
{
    private EditText ed_name;
    private EditText ed_card_num;
    private Button btn_next;

    private ProgressDialog dialog;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_verified;
    }

    @Override
    protected void initView()
    {
        ed_name = findView(R.id.ed_name);
        ed_card_num = findView(R.id.ed_card_num);
        btn_next = findView(R.id.btn_next);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_next.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
    }

    /**
     * 实名认证
     *
     * @param idCard 身份证号
     * @param name   姓名
     */
    private void auth(String idCard, final String name)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.verifyName(idCard, name, new Subscriber<ResponseBean>()
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
                FxApplication.getInstance().getUserInfoBean().setActualname(name);
                FxApplication.getInstance().refreshUserInfoBroadCast();
                ShowToast.showToast("认证成功");
                setResult(RESULT_OK);
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
            case R.id.btn_next:
                if (VerificationUtil.requiredFieldValidator(this, new View[]{ed_card_num, ed_name}, new String[]{"请输入身份证号", "请输入您的姓名"}))
                {
                    auth(getTextView(ed_card_num), getTextView(ed_name));
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
