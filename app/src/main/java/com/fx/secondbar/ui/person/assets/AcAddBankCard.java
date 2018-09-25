package com.fx.secondbar.ui.person.assets;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.NumSpaceTextWatcher;
import com.fx.secondbar.util.ProgressDialogUtil;

import rx.Subscriber;

/**
 * function:新增银行卡界面
 * author: frj
 * modify date: 2018/9/22
 */
public class AcAddBankCard extends ActivitySupport
{
    //    private TextView tv_name;
//    private TextView tv_card_num;
    private EditText ed_name;
    private EditText ed_bank;
    private EditText ed_bank_card;
    private EditText ed_bank_name;
    private Button btn_submit;

    private ProgressDialog dialog;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_add_bank_card;
    }

    @Override
    protected void initView()
    {
//        tv_name = findView(R.id.tv_name);
//        tv_card_num = findView(R.id.tv_card_num);
        ed_name = findView(R.id.ed_name);
        ed_bank = findView(R.id.ed_bank);
        ed_bank_card = findView(R.id.ed_bank_card);
        ed_bank_name = findView(R.id.ed_bank_name);
        btn_submit = findView(R.id.btn_submit);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        btn_submit.setOnClickListener(this);
        ed_bank_name.setOnClickListener(this);

        ed_name.addTextChangedListener(watcher);
        ed_bank.addTextChangedListener(watcher);
        ed_bank_card.addTextChangedListener(watcher);
        ed_bank_name.addTextChangedListener(watcher);
    }


    @Override
    protected void initData()
    {
        NumSpaceTextWatcher.addTextWatcher(ed_bank_card);
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
    }

    private TextWatcher watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (getTextView(ed_bank).length() > 0 && getTextView(ed_bank_card).length() > 0 && getTextView(ed_bank_name).length() > 0 && getTextView(ed_name).length() > 0)
            {
                btn_submit.setEnabled(true);
            } else
            {
                btn_submit.setEnabled(false);
            }
        }
    };

    /**
     * 提交
     *
     * @param name        户主姓名
     * @param bankCard    银行卡号
     * @param bankName    银行名称
     * @param bankAddress 开户行地址
     */
    private void submit(String name, String bankCard, String bankName, String bankAddress)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        bankCard = bankCard.replace(" ", "");
        HttpManager.addBank(bankCard, bankName, bankAddress, new Subscriber<ResponseBean>()
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
                ShowToast.showToast("添加成功");
                setResult(RESULT_OK);
                finish();
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
            case R.id.btn_submit:
                if (VerificationUtil.requiredFieldValidator(this, new View[]{ed_name, ed_bank_card, ed_bank, ed_bank_name}, new String[]{"请输入您的姓名", "请输入银行名称", "请输入您的银行卡号", "请输入您的开户行"}))
                {
                    submit(getTextView(ed_name), getTextView(ed_bank_card), getTextView(ed_bank), getTextView(ed_bank_name));
                }
                break;
            case R.id.ed_bank_name:
                jump(AcSelectBank.class);
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
