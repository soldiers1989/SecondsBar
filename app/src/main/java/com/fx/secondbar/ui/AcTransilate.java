package com.fx.secondbar.ui;

import android.Manifest;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.util.DeviceUuidFactory;

/**
 * function:过渡页
 * author: frj
 * modify date: 2018/9/20
 */
public class AcTransilate extends ActivitySupport
{

    private ImageView img;

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
                jump(MainActivity.class, true);
            }
        }, 2 * 1000);
        //用于生成设备id
        new DeviceUuidFactory(FxApplication.getInstance());
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
