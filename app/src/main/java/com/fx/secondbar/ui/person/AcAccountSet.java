package com.fx.secondbar.ui.person;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.ui.img.ConstantValue;
import com.btten.bttenlibrary.ui.img.MultiImageSelectorActivity;
import com.btten.bttenlibrary.util.BitmapUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.bumptech.glide.Glide;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.UserInfoBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.person.aboutus.AcAboutUs;
import com.fx.secondbar.ui.person.assets.AcVerified;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.GlideCacheUtil;
import com.fx.secondbar.util.GlideLoad;
import com.fx.secondbar.util.ProgressDialogUtil;
import com.joooonho.SelectableRoundedImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * function:账号设置
 * author: frj
 * modify date: 2018/9/21
 */
public class AcAccountSet extends ActivitySupport
{

    /**
     * 获取头像请求码
     */
    private static final int REQUEST_CODE_HEAD_IMG = 10;
    /**
     * 设置昵称请求码
     */
    private static final int REQUEST_CODE_SET_NICKNAME = 11;
    /**
     * 绑定手机号
     */
    private static final int REQUEST_CODE_BIND_PHONE = 12;
    /**
     * 设置支付密码
     */
    private static final int REQUEST_CODE_SET_PAYPWD = 13;
    /**
     * 实名认证请求码
     */
    private static final int REQUEST_CODE_AUTH = 14;

    private SelectableRoundedImageView img_avatar;
    private TextView tv_nickname;
    private TextView tv_level;
    private TextView tv_phone;
    private TextView tv_pay_pwd;
    private TextView tv_cache_size;
    private TextView tv_aboutus_tips;
    private Button btn_logout;
    private TextView tv_verify;

    private Subscription subscriptionCacheSize;
    private Subscription subscriptionClear;
    private ProgressDialog dialog;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_account_set;
    }

    @Override
    protected void initView()
    {
        img_avatar = findView(R.id.img_avatar);
        tv_nickname = findView(R.id.tv_nickname);
        tv_level = findView(R.id.tv_level);
        tv_phone = findView(R.id.tv_phone);
        tv_pay_pwd = findView(R.id.tv_pay_pwd);
        tv_cache_size = findView(R.id.tv_cache_size);
        tv_aboutus_tips = findView(R.id.tv_aboutus_tips);
        btn_logout = findView(R.id.btn_logout);
        tv_verify = findView(R.id.tv_verify);
        findView(R.id.tv_help_tips).setOnClickListener(this);
        findView(R.id.tv_transaction_rule_tips).setOnClickListener(this);
        findView(R.id.tv_turial_tips).setOnClickListener(this);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        img_avatar.setOnClickListener(this);
        tv_nickname.setOnClickListener(this);
        tv_level.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
        tv_pay_pwd.setOnClickListener(this);
        tv_cache_size.setOnClickListener(this);
        tv_aboutus_tips.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        tv_verify.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        dialog = ProgressDialogUtil.getProgressDialog(this, getString(R.string.progress_tips), true);
        setData();
    }

    /**
     * 设置用户数据
     */
    private void setData()
    {
        GlideLoad.load(img_avatar, FxApplication.getInstance().getUserInfoBean().getImg(), true, R.mipmap.default_avatar, R.mipmap.default_avatar);
        VerificationUtil.setViewValue(tv_nickname, FxApplication.getInstance().getUserInfoBean().getNickname());
        VerificationUtil.setViewValue(tv_level, "LV" + FxApplication.getInstance().getUserInfoBean().getLevel());
        VerificationUtil.setViewValue(tv_phone, FxApplication.getInstance().getUserInfoBean().getAccount(), "去绑定");
        //判断是否设置支付密码，1表示已设置。
        VerificationUtil.setViewValue(tv_pay_pwd, "1".equals(FxApplication.getInstance().getUserInfoBean().getPaymentpassword()) ? "去修改" : "去设置");
        VerificationUtil.setViewValue(tv_verify, TextUtils.isEmpty(FxApplication.getInstance().getUserInfoBean().getActualname()) ? "去认证" : "已认证");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        calCacheSize();
    }

    /**
     * 计算缓存大小
     */
    private void calCacheSize()
    {
        subscriptionCacheSize = Observable.create(new Observable.OnSubscribe<Long>()
        {
            @Override
            public void call(Subscriber<? super Long> subscriber)
            {
                File file = new File(getCacheDir() + File.separator + Constants.ROOT_DIR);
                long a = 0;
                if (!file.exists())
                {
                    try
                    {
                        a = GlideCacheUtil.getFolderSize(Glide.getPhotoCacheDir(BtApplication.getApplication()));
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                try
                {

                    long b = GlideCacheUtil.getFolderSize(file);
                    long all = a + b;
                    subscriber.onNext(all);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>()
        {
            @Override
            public void call(Long aLong)
            {
                if (aLong != null && tv_cache_size != null)
                {
                    tv_cache_size.setText(GlideCacheUtil.getFormatSize(aLong));
                }
            }
        });
    }

    /**
     * 清除缓存
     */
    private void clearCache()
    {
        subscriptionClear = Observable.create(new Observable.OnSubscribe<Void>()
        {
            @Override
            public void call(Subscriber<? super Void> subscriber)
            {
                GlideCacheUtil.getInstance().clearCacheDiskSelf();
                GlideCacheUtil.getInstance().cleanCatchDisk();
                GlideCacheUtil.deleteFolderFile(getCacheDir() + File.separator + Constants.ROOT_DIR, false);
                subscriber.onNext(null);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>()
        {
            @Override
            public void call(Void aVoid)
            {
                calCacheSize();
                ShowToast.showToast("清除成功");
            }
        });
    }

    /**
     * 退出登录
     */
    private void logout()
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.logout(new Subscriber<ResponseBean>()
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
                //重新生成账号信息
                login();
            }
        });
    }

    /**
     * 登录-退出登录后，重新生成账号信息
     */
    private void login()
    {
        HttpManager.login(new Subscriber<UserInfoBean>()
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
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(UserInfoBean userInfoBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                FxApplication.getInstance().setUserInfoBean(userInfoBean);
                FxApplication.refreshPersonShowBroadCast();
                setData();

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
            case R.id.img_avatar:
                Bundle bundle = new Bundle();
                bundle.putInt(ConstantValue.EXTRA_SELECT_MODE, ConstantValue.MODE_SINGLE);
                jump(MultiImageSelectorActivity.class, bundle, REQUEST_CODE_HEAD_IMG);
                break;
            case R.id.tv_nickname:
                Bundle bundleNickName = new Bundle();
                bundleNickName.putString(KEY_STR, getTextView(tv_nickname));
                jump(AcSetNickName.class, bundleNickName, REQUEST_CODE_SET_NICKNAME);
                break;
            case R.id.tv_level:
                jump(AcLevelList.class);
                break;
            case R.id.tv_phone:
                if (TextUtils.isEmpty(FxApplication.getInstance().getUserInfoBean().getAccount()))
                {
                    jump(AcBindPhone.class, REQUEST_CODE_BIND_PHONE);
                }
                break;
            case R.id.tv_pay_pwd:
                if (TextUtils.isEmpty(FxApplication.getInstance().getUserInfoBean().getAccount()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setMessage("您还未绑定手机号，请先绑定~");
                    builder.setPositiveButton("去绑定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            jump(AcBindPhone.class, REQUEST_CODE_BIND_PHONE);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();
                } else
                {
                    jump(AcBindPayPwd.class, REQUEST_CODE_SET_PAYPWD);
                }
                break;
            case R.id.tv_cache_size:
                clearCache();
                break;
            case R.id.tv_aboutus_tips:
                jump(AcAboutUs.class);
                break;
            case R.id.btn_logout:
                logout();
                break;
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_verify:
                if (TextUtils.isEmpty(FxApplication.getInstance().getUserInfoBean().getActualname()))
                {
                    jump(AcVerified.class, REQUEST_CODE_AUTH);
                }
                break;
            case R.id.tv_help_tips: //帮助
                jump(AcHelpList.class);
                break;
            case R.id.tv_transaction_rule_tips: //交易规则
                jump(AcTransactionRule.class);
                break;
            case R.id.tv_turial_tips:   //教程中心
                jump(AcTurialList.class);
                break;
        }
    }

    /**
     * 处理头像文件
     */
    private void handleAvatar(final String path)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        Observable.create(new Observable.OnSubscribe<File>()
        {
            @Override
            public void call(Subscriber<? super File> subscriber)
            {
                try
                {
                    byte[] bytes = BitmapUtil.decodeThumbBitmapByteForFile(path, BitmapUtil.DEFAULT_WIDTH, BitmapUtil.DEFAULT_HEIGHT, true);
                    File cacheDir = FxApplication.getInstance().getCacheDir();
                    File saveDir = new File(cacheDir, Constants.ROOT_DIR);
                    if (!saveDir.exists())
                    {
                        saveDir.mkdirs();
                    }
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    File file = new File(saveDir, fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.flush();
                    fos.close();
                    subscriber.onNext(file);
                } catch (Exception e)
                {
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally
                {

                }

            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<File>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                e.printStackTrace();
                ShowToast.showToast("图片上传失败");
            }

            @Override
            public void onNext(File file)
            {
                uploadAvatar(file);
            }
        });
    }

    /**
     * 上传头像
     *
     * @param file
     */
    private void uploadAvatar(File file)
    {
        HttpManager.getInstance().uploadAvatar(file, new Subscriber<ResponseBean>()
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
                ShowToast.showToast("头像修改失败");
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
                ShowToast.showToast("头像修改成功");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_HEAD_IMG == requestCode)
        {
            if (RESULT_OK == resultCode)
            {
                if (data == null)
                {
                    return;
                }
                ArrayList<String> list = data.getStringArrayListExtra(ConstantValue.EXTRA_RESULT);
                if (VerificationUtil.noEmpty(list))
                {
                    GlideApp.with(this).asBitmap().load(new File(list.get(0))).centerCrop().into(img_avatar);
                    handleAvatar(list.get(0));
                }
            }
        } else if (REQUEST_CODE_SET_NICKNAME == requestCode)
        {
            if (RESULT_OK == resultCode)
            {
                if (data == null)
                {
                    return;
                }
                VerificationUtil.setViewValue(tv_nickname, data.getStringExtra(KEY_STR));
            }
        } else if (REQUEST_CODE_BIND_PHONE == requestCode)
        {
            if (RESULT_OK == resultCode)
            {
                if (data == null)
                {
                    return;
                }
                VerificationUtil.setViewValue(tv_phone, data.getStringExtra(KEY_STR));
            }
        } else if (REQUEST_CODE_SET_PAYPWD == requestCode)
        {
            if (RESULT_OK == resultCode)
            {

                VerificationUtil.setViewValue(tv_pay_pwd, "已设置");
            }
        } else if (REQUEST_CODE_AUTH == requestCode)
        {
            if (RESULT_OK == resultCode)
            {
                VerificationUtil.setViewValue(tv_verify, TextUtils.isEmpty(FxApplication.getInstance().getUserInfoBean().getActualname()) ? "去认证" : "已认证");
            }
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

    @Override
    protected void onDestroy()
    {
        if (subscriptionClear != null)
        {
            subscriptionClear.unsubscribe();
        }
        if (subscriptionCacheSize != null)
        {
            subscriptionCacheSize.unsubscribe();
        }
        super.onDestroy();
    }
}
