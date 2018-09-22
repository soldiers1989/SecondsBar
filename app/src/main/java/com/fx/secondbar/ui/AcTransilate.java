package com.fx.secondbar.ui;

import android.Manifest;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.glide.GlideApp;
import com.fx.secondbar.R;

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
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[]{R.string.permission_write_external_storage_tips};
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
