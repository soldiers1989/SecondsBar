package com.fx.secondbar.ui;

import android.Manifest;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.Arithmetic;
import com.btten.bttenlibrary.util.SharePreferenceUtils;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.bean.ActiveBean;
import com.fx.secondbar.bean.ResConfigInfo;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.AcIncomeDialog;
import com.fx.secondbar.util.DeviceUuidFactory;

import cn.jpush.android.api.JPushInterface;
import rx.Subscriber;

/**
 * function:过渡页
 * author: frj
 * modify date: 2018/9/20
 */
public class AcTransilate extends ActivitySupport
{

    private TextView tv_jump;
    private ImageView img;
    //计数器，表示场景任务是否处理完，当前共两个任务
    private int count = 0;

    //倒计时5秒
    private int duration = 5;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_translate;
    }

    @Override
    protected void initView()
    {
        img = findView(R.id.img);
        tv_jump = findView(R.id.tv_jump);
    }

    @Override
    protected void initListener()
    {
        tv_jump.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        if (SharePreferenceUtils.getValueByBoolean("isFirst", true))
        {
            tv_jump.setVisibility(View.GONE);
        } else
        {
            tv_jump.setVisibility(View.VISIBLE);
        }
        tv_jump.setText(duration + "s | 跳过");
        new DeviceUuidFactory(FxApplication.getInstance());
        openAppActive();
        getConfigInfo();
        //用于生成设备id
        String deviceId = new DeviceUuidFactory(FxApplication.getInstance()).getDeviceUuid().toString();
        if (deviceId.contains("-"))
        {
            deviceId = deviceId.replace("-", "");
        }
        setAlias(deviceId);
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //倒计时
                case 1:
                    if (duration == 1)
                    {
                        //计数+1，表示一个任务完成
                        count++;
                        if (count >= 2)
                        {
                            SharePreferenceUtils.savePreferences("isFirst", false);
                            jump(MainActivity.class, true);
                        }
                        return;
                    } else
                    {
                        duration -= 1;
                        tv_jump.setText(duration + "s | 跳过");
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
            }
        }
    };

    /**
     * 完成打开App的任务
     */
    private void openAppActive()
    {
        HttpManager.finishActive(String.valueOf(ActiveBean.TYPE_OPEN_APP), new Subscriber<Double>()
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
            public void onNext(Double value)
            {
                if (value != null)
                {
                    jump(AcIncomeDialog.class, Arithmetic.doubleToStr(value));
                }
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
                count++;
                if (count >= 2)
                {
                    jump(MainActivity.class, true);
                }
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
                    SharePreferenceUtils.savePreferences("isFirst", false);
                    jump(MainActivity.class, true);
                }
            }
        });
    }

    /**
     * 设置别名
     */
    private void setAlias(String deviceId)
    {
        JPushInterface.setAlias(this, 1, deviceId);
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION};
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[]{R.string.permission_write_external_storage_tips,
                R.string.permission_read_phone_state_tips,
                R.string.permission_fine_location_tips};
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

    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.tv_jump:
                handler.removeMessages(1);
                count = 0;
                jump(MainActivity.class, true);
                break;
        }
    }
}
