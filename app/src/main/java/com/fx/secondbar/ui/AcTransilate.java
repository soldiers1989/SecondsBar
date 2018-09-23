package com.fx.secondbar.ui;

import android.Manifest;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.ActiveBean;
import com.fx.secondbar.bean.ResConfigInfo;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.DeviceUuidFactory;

import rx.Subscriber;

/**
 * function:过渡页
 * author: frj
 * modify date: 2018/9/20
 */
public class AcTransilate extends ActivitySupport
{

    private ImageView img;
    //计数器，表示场景任务是否处理完，当前共两个任务
    private int count = 0;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_translate;
    }

    @Override
    protected void initView()
    {
        img = findView(R.id.img);

    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
//        GlideApp.with(img).load(R.mipmap.startup).centerCrop().load(img);
        img.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //计数+1，表示一个任务完成
                count++;
                if (count >= 2)
                {
                    jump(MainActivity.class, true);
                }
            }
        }, 2 * 1000);
        //用于生成设备id
        new DeviceUuidFactory(FxApplication.getInstance());
        openAppActive();
        getConfigInfo();
    }

    /**
     * 完成打开App的任务
     */
    private void openAppActive()
    {
        HttpManager.finishActive(String.valueOf(ActiveBean.TYPE_OPEN_APP), new Subscriber<ResponseBean>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onNext(ResponseBean responseBean)
            {

            }
        });
    }

    /**
     * 获取配置信息
     */
    private void getConfigInfo()
    {
        HttpManager.getConfigInfo(new Subscriber<ResConfigInfo>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                e.printStackTrace();
            }

            @Override
            public void onNext(ResConfigInfo resConfigInfo)
            {
                //计数+1，表示一个任务完成
                count++;
                if (resConfigInfo != null)
                {
                    FxApplication.getInstance().setConfigInfo(resConfigInfo);
                }
                if (count >= 2)
                {
                    jump(MainActivity.class, true);
                }
            }
        });
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[]{R.string.permission_write_external_storage_tips, R.string.permission_read_phone_state_tips};
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (KeyEvent.KEYCODE_BACK == keyCode)
        {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
